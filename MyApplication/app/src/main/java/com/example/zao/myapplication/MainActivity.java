package com.example.zao.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.buttonLogIn);
        button.setOnClickListener(this);
        Log.d("testLog", "MainActivity: onCreate()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogIn:
                Intent intent = new Intent(this, ActivityChatList.class);
                startActivity(intent);
                break;
        }

        }
    }
