package com.ochibooh.mobile.tutorial.credential.remote.config.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.ochibooh.mobile.tutorial.credential.remote.config.R;
import com.ochibooh.mobile.tutorial.credential.remote.config.databinding.ActivityMainBinding;
import com.ochibooh.mobile.tutorial.credential.remote.config.lifecycle.MainActivityLifecycleObserver;
import com.ochibooh.mobile.tutorial.credential.remote.config.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(new MainActivityLifecycleObserver());
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewModel(new ViewModelProvider(this).get(MainActivityViewModel.class));

        setup();
    }

    private void setup() {

    }
}