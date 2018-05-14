package com.example.zao.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.UserMessage;

import java.util.List;
import java.util.UUID;

public class ActivityChat extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private EditText editTextMessage;
    private ListView chatListView;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatListView = findViewById(R.id.listViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        PreviousMessageListQuery prevMessageListQuery = ActivityChatList.currentOpenChannel.createPreviousMessageListQuery();
        prevMessageListQuery.load(30, false, new PreviousMessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<BaseMessage> messages, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(getBaseContext(), getString(R.string.get_channel_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                    return;
                }

                for (BaseMessage msg : messages) {
                    if (msg instanceof UserMessage) {
                        UserMessage userMsg = (UserMessage) msg;
                        arrayAdapter.add(userMsg.getSender().getUserId() + ": " + userMsg.getMessage());
                    }
                }

                chatListView.setSelection(arrayAdapter.getCount() - 1);
            }
        });

        chatListView.setAdapter(arrayAdapter);
        SendBird.addChannelHandler(UUID.randomUUID().toString(), new SendBird.ChannelHandler() {
            @Override
            public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                if (ActivityChatList.currentOpenChannel.getUrl().equals(baseChannel.getUrl()) && baseMessage instanceof UserMessage) {
                    UserMessage userMessage = (UserMessage)baseMessage;
                    arrayAdapter.add(userMessage.getSender().getUserId() + ": " + userMessage.getMessage());
                    chatListView.setSelection(arrayAdapter.getCount() - 1);
                }
            }
        });
    }

    private void enableSendButton()
    {
        buttonSend.setEnabled(true);
        buttonSend.setText(R.string.send);
    }

    public void buttonSendOnClick(View v) {
        String currentMessage =  editTextMessage.getText().toString().trim();
        if (currentMessage.isEmpty())
        {
            return;
        }

        buttonSend.setEnabled(false);
        buttonSend.setText(R.string.sending);

        editTextMessage.getText().clear();
        final String messageToAdapter = MainActivity.CurrentUserName + ": " + currentMessage;
        arrayAdapter.add(messageToAdapter);
        chatListView.setSelection(arrayAdapter.getCount() - 1);

        ActivityChatList.currentOpenChannel.sendUserMessage(currentMessage, null, "776", new BaseChannel.SendUserMessageHandler() {
            @Override
            public void onSent(UserMessage userMessage, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(getBaseContext(), getString(R.string.send_message_error, e.getMessage()), Toast.LENGTH_SHORT).show();
                    arrayAdapter.remove(messageToAdapter);
                    enableSendButton();
                    return;
                }

                enableSendButton();
            }
        });
    }
}
