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
import lessons.messenger.model.Message;
import lessons.messenger.model.User;

public class MessagesService extends AsyncTask<String, Void, ArrayList<Message>>
{
    @Override
    protected ArrayList<Message> doInBackground(String... params)
    {
        String token = params[0];
        String contact = params[1];

        // Base uri
        String uri = WebServiceConstants.MESSAGES.URI;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MESSAGES.TOKEN, token));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MESSAGES.CONTACT, contact));

        uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");

        HttpGet httpGet = new HttpGet(uri);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        ArrayList<Message> messages = new ArrayList<Message>();
        try
        {
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
            String response = EntityUtils.toString(httpResponse.getEntity());

            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.has(WebServiceConstants.MESSAGES.MESSAGES))
            {
                JSONArray jsonArray = jsonObject.getJSONArray(WebServiceConstants.MESSAGES.MESSAGES);
                for(int index = 0; index < jsonArray.length(); index++)
                {
                    Message message = new Message();
                    message.message = jsonArray.getJSONObject(index).getString(WebServiceConstants.MESSAGES.MESSAGE);
                    message.date = jsonArray.getJSONObject(index).getString(WebServiceConstants.MESSAGES.DATE);
                    message.sent = jsonArray.getJSONObject(index).getBoolean(WebServiceConstants.MESSAGES.SENT);

                    messages.add(message);
                }
            }
            return messages;
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
        return messages;
    }
}