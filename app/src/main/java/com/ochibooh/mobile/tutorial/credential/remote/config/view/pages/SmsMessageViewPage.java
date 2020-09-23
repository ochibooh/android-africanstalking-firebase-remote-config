package com.ochibooh.mobile.tutorial.credential.remote.config.view.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.ochibooh.mobile.tutorial.credential.remote.config.R;
import com.ochibooh.mobile.tutorial.credential.remote.config.databinding.PageSmsMessageViewBinding;
import com.ochibooh.mobile.tutorial.credential.remote.config.lifecycle.page.SmsMessageViewLifecycleObserver;
import com.ochibooh.mobile.tutorial.credential.remote.config.viewmodel.page.SmsMessageViewPageViewModel;

import lombok.extern.java.Log;

@Log
public class SmsMessageViewPage extends Fragment {
    private PageSmsMessageViewBinding binding;

    private SmsMessageViewPageViewModel viewModel;

    public SmsMessageViewPage() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.page_sms_message_view, container, false);
        this.getLifecycle().addObserver(new SmsMessageViewLifecycleObserver());
        this.binding.setLifecycleOwner(getViewLifecycleOwner());
        this.viewModel = new ViewModelProvider(this).get(SmsMessageViewPageViewModel.class);
        this.binding.setViewModel(this.viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            setup();
        }
    }

    private void setup() {
        this.requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(binding.getRoot()).navigateUp();
                    }
                });
    }
}