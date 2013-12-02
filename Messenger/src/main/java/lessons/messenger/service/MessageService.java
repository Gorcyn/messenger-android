package lessons.messenger.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import lessons.messenger.constant.WebServiceConstants;
import lessons.messenger.model.Message;

public class MessageService extends AsyncTask<String, Void, Message>
{
    @Override
    protected Message doInBackground(String... params)
    {
        String token = params[0];
        String contact = params[1];
        String message = params[2];

        // Base uri
        String uri = WebServiceConstants.MESSAGE.URI;

        // Query string
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MESSAGE.TOKEN, token));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MESSAGE.CONTACT, contact));
        nameValuePairs.add(new BasicNameValuePair(WebServiceConstants.MESSAGE.MESSAGE, message));

        uri += "?" + URLEncodedUtils.format(nameValuePairs, "utf-8");

        HttpGet httpGet = new HttpGet(uri);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

        try
        {
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet, new BasicHttpContext());
            String response = EntityUtils.toString(httpResponse.getEntity());

            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.has(WebServiceConstants.MESSAGE.MESSAGE))
            {
                Message messageObject = new Message();
                messageObject.message = jsonObject.getJSONObject(WebServiceConstants.MESSAGE.MESSAGE).getString(WebServiceConstants.MESSAGE.MESSAGE);
                messageObject.date = jsonObject.getJSONObject(WebServiceConstants.MESSAGE.MESSAGE).getString(WebServiceConstants.MESSAGE.DATE);
                messageObject.sent = jsonObject.getJSONObject(WebServiceConstants.MESSAGE.MESSAGE).getBoolean(WebServiceConstants.MESSAGE.SENT);
                return messageObject;
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
        return null;
    }
}