package com.ochibooh.mobile.tutorial.credential.remote.config.adapter.sms;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.circularreveal.CircularRevealRelativeLayout;
import com.google.android.material.textview.MaterialTextView;
import com.ochibooh.mobile.tutorial.credential.remote.config.R;

public class SmsMessageViewHolder extends RecyclerView.ViewHolder {
    public MaterialTextView recipient, message, date;

    public CircularRevealRelativeLayout holder;

    public SmsMessageViewHolder(@NonNull View view) {
        super(view);
        this.recipient = view.findViewById(R.id.recipient);
        this.message = view.findViewById(R.id.message);
        this.date = view.findViewById(R.id.date);
        this.holder = view.findViewById(R.id.holder);
    }
}
