package com.ochibooh.mobile.tutorial.credential.remote.config.lifecycle.page;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public class SmsMessagesLifecycleObserver implements LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create(){
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start(){
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume(){
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause(){
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop(){
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy(){
    }
}
