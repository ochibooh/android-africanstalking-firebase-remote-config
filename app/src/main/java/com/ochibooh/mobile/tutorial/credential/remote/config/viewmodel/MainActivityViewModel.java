package com.ochibooh.mobile.tutorial.credential.remote.config.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.AndroidViewModel;

import com.ochibooh.mobile.tutorial.credential.remote.config.BR;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;

public class MainActivityViewModel extends AndroidViewModel implements Observable {
    private String title = "Messages";

    private int page = 0;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Bindable
    public String getTitle() {
        return SharedUtils.getInstance().isNullOrEmpty(this.title) ? "Messages" : this.title;
    }

    public void setTitle(String tittle) {
        if (!SharedUtils.getInstance().isNullOrEmpty(tittle) && !this.title.equals(tittle)) {
            this.title = tittle.trim();
            propertyChanged(BR.title);
        }
    }

    @Bindable
    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        if (this.page != page) {
            this.page = page;
            propertyChanged(BR.page);
        }
    }

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        this.callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        this.callbacks.remove(callback);
    }

    private void propertyChanged(int field) {
        callbacks.notifyCallbacks(this, field, null);
    }
}
