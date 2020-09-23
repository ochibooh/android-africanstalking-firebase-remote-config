package com.ochibooh.mobile.tutorial.credential.remote.config.adapter.sms;

import android.content.Context;
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
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class SmsMessageAdapter extends RecyclerView.Adapter<SmsMessageHolder> {
    private Context context;

    private List<SmsMessage> smsMessages;

    public SmsMessageAdapter(@NonNull Context context, @NonNull List<SmsMessage> smsMessages) {
        this.context = context;
        this.smsMessages = smsMessages;
    }

    @NonNull
    @Override
    public SmsMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_sms_message, parent, false);
        return new SmsMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsMessageHolder holder, int position) {
        SmsMessage message = this.smsMessages.get(position);
        holder.recipient.setText(message.getRecipient());
        holder.message.setText(message.getMessage());
        holder.date.setText(SharedUtils.getInstance().date(message.getCreationDate().getTime()));
        holder.holder.setOnClickListener(view -> Navigation.findNavController(view).navigate(SmsMessagesPageDirections.messagesToView().setRecipientNumber(message.getRecipient())));
        log.log(Level.INFO, String.format("Messages [ %s ]", message));
    }

    @Override
    public int getItemCount() {
        return this.smsMessages.size();
    }
}
