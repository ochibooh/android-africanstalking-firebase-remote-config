package com.ochibooh.mobile.tutorial.credential.remote.config.view.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ochibooh.mobile.tutorial.credential.remote.config.R;
import com.ochibooh.mobile.tutorial.credential.remote.config.databinding.PageSmsMessageViewBinding;
import com.ochibooh.mobile.tutorial.credential.remote.config.viewmodel.page.SmsMessageViewPageViewModel;

import lombok.extern.java.Log;

@Log
public class SmsMessageViewPage extends Fragment {
    private PageSmsMessageViewBinding binding;

    public SmsMessageViewPage() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.page_sms_message_view, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(new ViewModelProvider(this).get(SmsMessageViewPageViewModel.class));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();
    }

    private void setup() {

    }
}