package com.ochibooh.mobile.tutorial.credential.remote.config.adapter.sms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ochibooh.mobile.tutorial.credential.remote.config.R;
import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;
import com.ochibooh.mobile.tutorial.credential.remote.config.view.pages.SmsMessagesPageDirections;

import java.util.List;

public class SmsMessageAdapter extends RecyclerView.Adapter<SmsMessageViewHolder> {
    private List<SmsMessage> smsMessages;

    public SmsMessageAdapter(List<SmsMessage> smsMessages) {
        this.smsMessages = smsMessages;
    }

    @NonNull
    @Override
    public SmsMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_sms_message, parent, false);
        return new SmsMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsMessageViewHolder holder, int position) {
        SmsMessage message = smsMessages.get(position);
        holder.recipient.setText(message.getRecipient());
        holder.message.setText(message.getMessage());
        holder.date.setText(SharedUtils.getInstance().date(message.getCreationDate().getTime()));
        holder.holder.setOnClickListener(view -> Navigation.findNavController(view).navigate(SmsMessagesPageDirections.messagesToView().setRecipientNumber(message.getRecipient())));
    }

    @Override
    public int getItemCount() {
        return this.smsMessages.size();
    }
}
