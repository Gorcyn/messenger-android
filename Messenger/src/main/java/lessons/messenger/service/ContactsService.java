package lessons.messenger.service;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lessons.messenger.constant.WebServiceConstants;
import lessons.messenger.model.Contact;
import lessons.messenger.model.Message;
import lessons.messenger.model.User;

public class ContactsService extends AsyncTask<String, Void, ArrayList<Contact>>
{
    @Override
    protected ArrayList<Contact> doInBackground(String... params)
    {
        String token = params[0];

        // Base uri
        String uri = WebServiceConstants.CONTACTS.URI;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.CONTACTS.TOKEN, token));

        uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");

        HttpGet httpGet = new HttpGet(uri);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        try
        {
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
            String response = EntityUtils.toString(httpResponse.getEntity());

            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.has(WebServiceConstants.CONTACTS.CONTACTS))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.CONTACTS.CONTACTS);
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Contact contact = new Contact();
                    contact.id = jsonArray.getJSONObject(index).getInt(WebServiceConstants.CONTACTS.ID);

                    User user = new User();
                    JSONObject userObject = jsonArray.getJSONObject(index).getJSONObject(WebServiceConstants.CONTACTS.CONTACT);
                    user.first_name = userObject.getString(WebServiceConstants.CONTACTS.FIRST_NAME);
                    user.last_name = userObject.getString(WebServiceConstants.CONTACTS.LAST_NAME);
                    user.email = userObject.getString(WebServiceConstants.CONTACTS.EMAIL);

                    contact.contact = user;

                    if(jsonArray.getJSONObject(index).has(WebServiceConstants.CONTACTS.MESSAGE))
                    {
                        Message message = new Message();
                        JSONObject messageObject = jsonArray.getJSONObject(index).getJSONObject(WebServiceConstants.CONTACTS.MESSAGE);
                        message.message = messageObject.getString(WebServiceConstants.CONTACTS.MESSAGE);
                        message.date = messageObject.getString(WebServiceConstants.CONTACTS.DATE);

                        contact.message = message;
                    }

                    contacts.add(contact);
                }
            }
        }
        catch(JSONException jsonException)
        {

        }
        catch(ClientProtocolException clientProtocolException)
        {

        }
        catch(IOException ioException)
        {

        }
        return contacts;
    }
}