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
import android.widget.EditText;
import android.widget.Toast;

import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;


public class MainActivity extends AppCompatActivity {

    private EditText editTextLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("testLog", "MainActivity: onCreate()");
        editTextLogIn = findViewById(R.id.editTextLogIn);
    }

    public void buttonLogInOnClick(View v) {
        Toast.makeText(getBaseContext(), "Connecting..", Toast.LENGTH_SHORT).show();
        SendBird.connect(editTextLogIn.getText().toString(), "368795ec2183f4da5882d8b542c31ba090177093", new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(getBaseContext(), getString(R.string.login_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getBaseContext(), ActivityChatList.class);
                startActivity(intent);
            }
        });
    }
}
