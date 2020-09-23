package com.ochibooh.mobile.tutorial.credential.remote.config.adapter.sms;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.ochibooh.mobile.tutorial.credential.remote.config.R;

public class SmsMessageHolder extends RecyclerView.ViewHolder {
    public MaterialTextView recipient, message, date;

    public ConstraintLayout holder;

    public SmsMessageHolder(@NonNull View view) {
        super(view);
        this.recipient = view.findViewById(R.id.recipient);
        this.message = view.findViewById(R.id.message);
        this.date = view.findViewById(R.id.date);
        this.holder = view.findViewById(R.id.holder);
    }
}
