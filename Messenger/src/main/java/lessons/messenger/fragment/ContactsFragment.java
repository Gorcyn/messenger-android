package lessons.messenger.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v4.app.ListFragment;

import lessons.messenger.R;
import lessons.messenger.activity.MessagesActivity;
import lessons.messenger.constant.IntentConstants;
import lessons.messenger.model.Contact;
import lessons.messenger.service.ContactsService;

public class ContactsFragment extends ListFragment
{
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        try
        {
            String token = this.getActivity().getIntent().getStringExtra(IntentConstants.TOKEN);

            ContactsService contactsService = new ContactsService();
            ArrayList<Contact> contacts = contactsService.execute(token).get();

            this.setListAdapter(new ContactsAdapter(this.getActivity(), contacts));
        }
        catch(InterruptedException interruptedException)
        {

        }
        catch(ExecutionException executionException)
        {

        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        String token = this.getActivity().getIntent().getStringExtra(IntentConstants.TOKEN);
        Contact contact = (Contact) this.getListAdapter().getItem(position);

        Intent intent = new Intent(this.getActivity(), MessagesActivity.class);
        intent.putExtra(IntentConstants.TOKEN, token);
        intent.putExtra(IntentConstants.CONTACT, contact.id);
        intent.putExtra(IntentConstants.TITLE, contact.contact.getIdentity());

        this.startActivity(intent);
    }

    private class ContactsAdapter extends ArrayAdapter<Contact>
    {
        public ContactsAdapter(Context context, ArrayList<Contact> contacts)
        {
            super(context, R.layout.contacts_list_entry, contacts);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this.getContext()).inflate(R.layout.contacts_list_entry, null);

            ((TextView) viewGroup.findViewById(R.id.contact)).setText(this.getItem(position).contact.getIdentity());

            String message = this.getContext().getString(R.string.messages_none);
            if(this.getItem(position).message != null)
            {
                message = this.getItem(position).message.message;
            }
            else
            {
                ((TextView) viewGroup.findViewById(R.id.message)).setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
            }
            ((TextView) viewGroup.findViewById(R.id.message)).setText(message);
            return viewGroup;
        }
    }
}