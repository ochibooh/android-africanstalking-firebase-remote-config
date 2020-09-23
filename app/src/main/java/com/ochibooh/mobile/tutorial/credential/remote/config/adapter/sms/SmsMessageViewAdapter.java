package com.ochibooh.mobile.tutorial.credential.remote.config.adapter.sms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ochibooh.mobile.tutorial.credential.remote.config.R;
import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;

import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class SmsMessageViewAdapter extends RecyclerView.Adapter<SmsMessageViewHolder> {
    private Context context;

    private List<SmsMessage> smsMessages;

    public SmsMessageViewAdapter(@NonNull Context context, @NonNull List<SmsMessage> smsMessages) {
        this.context = context;
        this.smsMessages = smsMessages;
    }

    @NonNull
    @Override
    public SmsMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_sms_message_view, parent, false);
        return new SmsMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsMessageViewHolder holder, int position) {
        SmsMessage message = this.smsMessages.get(position);
        holder.message.setText(message.getMessage());
        holder.date.setText(SharedUtils.getInstance().date(message.getCreationDate().getTime()));
        log.log(Level.INFO, String.format("View message [ %s ]", message));
    }

    @Override
    public int getItemCount() {
        return this.smsMessages.size();
    }
}
