package com.ochibooh.mobile.tutorial.credential.remote.config.viewmodel.page;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

public class SmsMessageViewPageViewModel extends AndroidViewModel implements Observable {
    public SmsMessageViewPageViewModel(@NonNull Application application) {
        super(application);
    }

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    @Override
    public void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    private void propertyChanged(int field) {
        callbacks.notifyCallbacks(this, field, null);
    }
}
