package lessons.messenger.activity;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

import lessons.messenger.R;
import lessons.messenger.constant.IntentConstants;
import lessons.messenger.fragment.MessageWriterFragment;
import lessons.messenger.fragment.MessageWriterFragment.MessageWriterFragmentListener;
import lessons.messenger.fragment.MessagesFragment;
import lessons.messenger.model.Message;

public class MessagesActivity extends FragmentActivity implements MessageWriterFragmentListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_messages);

        String title = this.getIntent().getStringExtra(IntentConstants.TITLE);
        this.setTitle(title);

        MessageWriterFragment messageWriterFragment = (MessageWriterFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment_message_writer);
        messageWriterFragment.setMessageWriterFragmentListener(this);
    }

    @Override
    public void onMessageSent(Message message)
    {
        MessagesFragment messagesFragment = (MessagesFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment_messages);
        messagesFragment.addSentMessage(message);
    }
}