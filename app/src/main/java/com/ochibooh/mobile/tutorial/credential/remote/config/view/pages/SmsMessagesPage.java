package com.ochibooh.mobile.tutorial.credential.remote.config.view.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Transaction;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.ochibooh.mobile.tutorial.credential.remote.config.R;
import com.ochibooh.mobile.tutorial.credential.remote.config.adapter.sms.SmsMessageAdapter;
import com.ochibooh.mobile.tutorial.credential.remote.config.databinding.PageSmsMessagesBinding;
import com.ochibooh.mobile.tutorial.credential.remote.config.lifecycle.page.SmsMessagesLifecycleObserver;
import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.PhoneUtils;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SmsUtils;
import com.ochibooh.mobile.tutorial.credential.remote.config.viewmodel.page.SmsMessagesPageViewModel;
import com.ochibooh.mobile.tutorial.credential.remote.config.work.SmsMessageSendWork;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import lombok.extern.java.Log;

@Log
public class SmsMessagesPage extends Fragment {
    private PageSmsMessagesBinding binding;

    private SmsMessagesPageViewModel viewModel;

    private AlertDialog sendMessageDialog;

    private Toast toast;

    private List<SmsMessage> smsMessages = new ArrayList<>();

    public SmsMessagesPage() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.page_sms_messages, container, false);
        this.getLifecycle().addObserver(new SmsMessagesLifecycleObserver());
        this.binding.setLifecycleOwner(getViewLifecycleOwner());
        this.viewModel = new ViewModelProvider(this).get(SmsMessagesPageViewModel.class);
        this.binding.setViewModel(this.viewModel);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            setup();
        }
    }

    private void setup() {
        Context context = this.requireContext();

        this.toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        this.toast.setGravity(Gravity.BOTTOM, 0, 16);

        sendSmsDialog(context);
        messagesRecyclerView(context);

        this.binding.newMessageFloatingButton.setOnClickListener(view -> {
            if (!this.sendMessageDialog.isShowing()) {
                this.sendMessageDialog.show();
            }
        });
    }

    private void sendSmsDialog(@NonNull Context context) {
        View dialogAlertView = this.getLayoutInflater().inflate(R.layout.custom_send_sms_message, null);
        MaterialAutoCompleteTextView phoneCountryAutoComplete = dialogAlertView.findViewById(R.id.phone_country_auto_complete);
        phoneCountryAutoComplete.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, new String[]{"+254", "+255", "+256"}));
        TextInputEditText phoneNumberEditText = dialogAlertView.findViewById(R.id.phone_number_edit_text);
        TextInputEditText messageEditText = dialogAlertView.findViewById(R.id.message_edit_text);
        this.sendMessageDialog = new MaterialAlertDialogBuilder(context)
                .setTitle("Send message")
                .setView(dialogAlertView)
                .setPositiveButton("Send", (dialog, which) -> {
                    try {
                        String country = phoneCountryAutoComplete.getText() != null ? phoneCountryAutoComplete.getText().toString() : null;
                        String phone = phoneNumberEditText.getText() != null ? phoneNumberEditText.getText().toString() : null;
                        String message = messageEditText.getText() != null ? messageEditText.getText().toString() : null;
                        if (!SharedUtils.getInstance().isNullOrEmpty(country)) {
                            if (!SharedUtils.getInstance().isNullOrEmpty(phone)) {
                                if (!SharedUtils.getInstance().isNullOrEmpty(message)) {
                                    Phonenumber.PhoneNumber phoneNumber = PhoneUtils.getInstance().phone(context, Integer.parseInt(Objects.requireNonNull(country).replace("+", "")), Objects.requireNonNull(phone));
                                    if (phoneNumber != null) {
                                        String p = PhoneUtils.getInstance().format(context, phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                                        if (!SharedUtils.getInstance().isNullOrEmpty(p)) {
                                            WorkRequest work = new OneTimeWorkRequest.Builder(SmsMessageSendWork.class)
                                                    .addTag(UUID.randomUUID().toString())
                                                    .setInputData(new Data.Builder().putString("recipient", p.replaceAll(" ", "").trim()).putString("message", Objects.requireNonNull(message).trim()).build())
                                                    .build();
                                            WorkManager.getInstance(context).enqueue(work);
                                        } else {
                                            toast.setText("Please enter message");
                                            toast.show();
                                        }
                                    } else {
                                        toast.setText("Sorry, phone number is invalid");
                                        toast.show();
                                    }
                                } else {
                                    toast.setText("Please enter message");
                                    toast.show();
                                }
                            } else {
                                toast.setText("Please enter recipient's phone number");
                                toast.show();
                            }
                        } else {
                            toast.setText("Select phone number country");
                            toast.show();
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, String.format("Send message error [ %s ]", e.getMessage()), e);
                        toast.setText("Sorry, an error occurred");
                        toast.show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setOnDismissListener(dialog -> {
                    phoneCountryAutoComplete.setText(null);
                    phoneNumberEditText.setText(null);
                    messageEditText.setText(null);
                })
                .setCancelable(false)
                .create();
    }

    @Transaction
    private void messagesRecyclerView(@NonNull Context context) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        this.binding.messagesRecyclerView.setLayoutManager(layoutManager);
        this.binding.messagesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SmsMessageAdapter adapter = new SmsMessageAdapter(context, smsMessages);
        this.binding.messagesRecyclerView.setAdapter(adapter);

        SmsUtils.getInstance().smsAll(context)
                .observe(getViewLifecycleOwner(), messages -> {
                    smsMessages.clear();
                    smsMessages.addAll(messages.stream()
                            .sorted(Comparator.comparing(sms -> sms.getCreationDate().getTime(), Comparator.reverseOrder()))
                            .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SmsMessage::getRecipient))), ArrayList::new))
                            .stream()
                            .sorted(Comparator.comparing(sms -> sms.getCreationDate().getTime(), Comparator.reverseOrder()))
                            .collect(Collectors.toList()));
                    adapter.notifyDataSetChanged();
                });
    }
}