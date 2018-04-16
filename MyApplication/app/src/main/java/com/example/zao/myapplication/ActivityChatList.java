package com.example.zao.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class ActivityChatList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        Log.d("Start", "ActivityChatList: onStart()");
    }*/
}
