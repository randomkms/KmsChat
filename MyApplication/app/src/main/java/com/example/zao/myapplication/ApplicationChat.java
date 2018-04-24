package com.example.zao.myapplication;

import android.app.Application;

import com.sendbird.android.SendBird;

public class ApplicationChat extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SendBird.init("B0D0D38C-7CF9-41E2-B3B2-8E8D335C6E64", this);
    }
}
