package com.example.zao.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sendbird.android.OpenChannel;
import com.sendbird.android.OpenChannelListQuery;
import com.sendbird.android.SendBirdException;

import java.util.List;


public class ActivityChatList extends AppCompatActivity {
    public static List<OpenChannel> openChannels;
    public static OpenChannel currentOpenChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Toast.makeText(getBaseContext(), "Hello " + MainActivity.CurrentUserName, Toast.LENGTH_SHORT).show();

        ListView chatList = findViewById(R.id.listViewChatList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        chatList.setAdapter(adapter);

        OpenChannelListQuery channelListQuery = OpenChannel.createOpenChannelListQuery();
        channelListQuery.next(new OpenChannelListQuery.OpenChannelListQueryResultHandler() {
            @Override
            public void onResult(List<OpenChannel> channels, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(getBaseContext(), getString(R.string.get_channel_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                    return;
                }

                openChannels = channels;
                for (OpenChannel channel : channels) {
                    adapter.add(channel.getName());
                }
            }
        });

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getBaseContext(), "Entering chanel..", Toast.LENGTH_SHORT).show();
                openChannels.get(position).enter(new OpenChannel.OpenChannelEnterHandler() {
                    @Override
                    public void onResult(SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(getBaseContext(), getString(R.string.enter_channel_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ActivityChatList.currentOpenChannel = openChannels.get(position);
                        Intent intent = new Intent(getBaseContext(), ActivityChat.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
