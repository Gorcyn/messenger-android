package lessons.messenger.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import lessons.messenger.BuildConfig;
import lessons.messenger.R;
import lessons.messenger.activity.ContactsActivity;
import lessons.messenger.constant.IntentConstants;
import lessons.messenger.service.LoginService;

public class LoginFragment extends Fragment implements OnClickListener
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_login, null);
        viewGroup.findViewById(R.id.connect).setOnClickListener(this);

        if(BuildConfig.DEBUG)
        {
            ((EditText) viewGroup.findViewById(R.id.email)).setText("test1@test.fr");
            ((EditText) viewGroup.findViewById(R.id.password)).setText("test");
        }
        return viewGroup;
    }

    @Override
    public void onClick(View v)
    {
        String email = ((EditText) this.getView().findViewById(R.id.email)).getText().toString();
        String password = ((EditText) this.getView().findViewById(R.id.password)).getText().toString();

        // Local validation
        boolean error = false;
        if(password.length() == 0)
        {
            ((EditText) this.getView().findViewById(R.id.password)).setError(this.getString(R.string.password_required));
            error = true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            ((EditText) this.getView().findViewById(R.id.email)).setError(this.getString(R.string.email_error));
            error = true;
        }
        if(error)
        {
            return;
        }

        LoginService loginService = new LoginService();
        try
        {
            String token = loginService.execute(email, password).get();
            if(token != null)
            {
                Intent intent = new Intent(this.getActivity(), ContactsActivity.class);
                intent.putExtra(IntentConstants.TOKEN, token);
                this.startActivity(intent);
            }
            else
            {
                Toast.makeText(this.getActivity(), R.string.login_error, Toast.LENGTH_SHORT).show();
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