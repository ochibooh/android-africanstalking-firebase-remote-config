package com.ochibooh.mobile.tutorial.credential.remote.config.adapter.sms;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ochibooh.mobile.tutorial.credential.remote.config.R;

public class SmsMessageViewHolder extends RecyclerView.ViewHolder {
    public MaterialTextView date, message;

    public SmsMessageViewHolder(@NonNull View view) {
        super(view);
        this.date = view.findViewById(R.id.date);
        this.message = view.findViewById(R.id.message);
    }
}
