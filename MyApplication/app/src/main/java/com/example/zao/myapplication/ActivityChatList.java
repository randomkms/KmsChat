package com.example.zao.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBirdException;


public class ActivityChatList extends AppCompatActivity {

    public static OpenChannel openChannel;

    private String[] names = { "Friends", "NightRaid", "SP115" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        ListView chatList = findViewById(R.id.listViewChatList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        chatList.setAdapter(adapter);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenChannel.getChannel(((TextView)view).getText().toString(), new OpenChannel.OpenChannelGetHandler() {
                    @Override
                    public void onResult(final OpenChannel openChannel, SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(getBaseContext(), getString(R.string.get_channel_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
                            @Override
                            public void onResult(SendBirdException e) {
                                if (e != null) {
                                    Toast.makeText(getBaseContext(), getString(R.string.enter_channel_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                ActivityChatList.openChannel = openChannel;
                                Intent intent = new Intent(getBaseContext(), ActivityChat.class);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }
}
