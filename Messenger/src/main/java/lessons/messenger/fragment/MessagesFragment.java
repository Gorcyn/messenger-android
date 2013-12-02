package lessons.messenger.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import lessons.messenger.R;
import lessons.messenger.constant.IntentConstants;
import lessons.messenger.model.Message;
import lessons.messenger.service.MessagesService;

public class MessagesFragment extends ListFragment
{
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        try
        {
            String token = this.getActivity().getIntent().getStringExtra(IntentConstants.TOKEN);
            int contact = this.getActivity().getIntent().getIntExtra(IntentConstants.CONTACT, -1);

            MessagesService messagesService = new MessagesService();
            ArrayList<Message> messages = messagesService.execute(token, String.valueOf(contact)).get();

            this.setListAdapter(new MessagesAdapter(this.getActivity(), messages));
        }
        catch(InterruptedException interruptedException)
        {

        }
        catch(ExecutionException executionException)
        {

        }
    }

    public void addSentMessage(Message message)
    {
        ((MessagesAdapter) this.getListAdapter()).add(message);
        ((MessagesAdapter) this.getListAdapter()).notifyDataSetChanged();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            this.getListView().smoothScrollToPosition(this.getListAdapter().getCount() - 1);
        }
        else
        {
            this.getListView().setSelection(this.getListAdapter().getCount());
        }
    }

    private class MessagesAdapter extends ArrayAdapter<Message>
    {
        public MessagesAdapter(Context context, ArrayList<Message> messages)
        {
            super(context, R.layout.messages_list_entry_sent, messages);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            int layout = R.layout.messages_list_entry_sent;
            if(!this.getItem(position).sent)
            {
                layout = R.layout.messages_list_entry_received;
            }

            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(layout, null);

            ((TextView) viewGroup.findViewById(R.id.message)).setText(this.getItem(position).message);
            ((TextView) viewGroup.findViewById(R.id.date)).setText(this.getItem(position).date);

            return viewGroup;
        }
    }
}