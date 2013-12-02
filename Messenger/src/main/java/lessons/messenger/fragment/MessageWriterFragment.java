package lessons.messenger.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import lessons.messenger.R;
import lessons.messenger.constant.IntentConstants;
import lessons.messenger.model.Message;
import lessons.messenger.service.MessageService;

public class MessageWriterFragment extends Fragment implements OnClickListener
{
    public interface MessageWriterFragmentListener
    {
        public void onMessageSent(Message message);
    }

    private MessageWriterFragmentListener messageWriterFragmentListener;

    public void setMessageWriterFragmentListener(MessageWriterFragmentListener messageWriterFragmentListener)
    {
        this.messageWriterFragmentListener = messageWriterFragmentListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_message_writer, null);
        viewGroup.findViewById(R.id.send).setOnClickListener(this);

        return viewGroup;
    }

    @Override
    public void onClick(View v)
    {
        try
        {
            String token = this.getActivity().getIntent().getStringExtra(IntentConstants.TOKEN);
            int contact = this.getActivity().getIntent().getIntExtra(IntentConstants.CONTACT, -1);
            String message = ((EditText) this.getView().findViewById(R.id.message)).getText().toString();

            MessageService messageService = new MessageService();
            Message messageObject = messageService.execute(token, String.valueOf(contact), message).get();

            ((EditText) this.getView().findViewById(R.id.message)).setText("");
            if(messageObject != null)
            {
                this.messageWriterFragmentListener.onMessageSent(messageObject);
            }
            else
            {
                Toast.makeText(this.getActivity(), R.string.message_not_sent, Toast.LENGTH_SHORT).show();
            }
        }
        catch(InterruptedException interruptedException)
        {

        }
        catch(ExecutionException executionException)
        {

        }
        catch(NullPointerException nullPointerException)
        {

        }
    }
}