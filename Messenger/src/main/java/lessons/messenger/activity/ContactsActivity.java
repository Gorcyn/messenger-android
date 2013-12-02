package lessons.messenger.activity;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;

import lessons.messenger.R;

public class ContactsActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_contacts);
    }
}