package com.ochibooh.mobile.tutorial.credential.remote.config.viewmodel.page;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SmsUtils;

import java.util.List;

public class SmsMessagesPageViewModel extends AndroidViewModel implements Observable {
    public LiveData<List<SmsMessage>> messages;

    public SmsMessagesPageViewModel(@NonNull Application application) {
        super(application);
        this.messages = SmsUtils.getInstance().smsAll(application.getApplicationContext());
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
