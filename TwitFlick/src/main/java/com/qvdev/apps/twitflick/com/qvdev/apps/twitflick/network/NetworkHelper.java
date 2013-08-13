package com.qvdev.apps.twitflick.com.qvdev.apps.twitflick.network;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qvdev.apps.twitflick.Presenter.BuzzingPresenter;
import com.qvdev.apps.twitflick.api.models.Buzzing;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

/**
 * Created by dirkwilmer on 7/26/13.
 */
public class NetworkHelper extends AsyncTask<URL, Integer, List<Buzzing>> {

    //TODO:Maybe implement VOLLEY?
    //http://blog.bignerdranch.com/3177-solving-the-android-image-loading-problem-volley-vs-picasso/
    private Gson mGSon = new Gson();
    private BuzzingPresenter mBuzzingPresenter = null;

    public NetworkHelper(BuzzingPresenter context) {
        mBuzzingPresenter = context;
    }

    public List<Buzzing> getJson(URL url) {

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url.toString());
        List<Buzzing> buzzingResult = null;

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();

                Reader reader = new InputStreamReader(content);
                Type listType = new TypeToken<List<Buzzing>>() {
                }.getType();

                try {
                    buzzingResult = mGSon.fromJson(reader, listType);
                } catch (Exception e) {
                    Log.d("APP", "Exception" + e);
                }

            } else {
                Log.e(Gson.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buzzingResult;
    }

    @Override
    protected List<Buzzing> doInBackground(URL... urls) {

        List<Buzzing> buzzingResult = getJson(urls[0]);

        return buzzingResult;
    }

    @Override
    protected void onPostExecute(List<Buzzing> result) {
        if (result != null) {
            mBuzzingPresenter.refresh(result);
        }
    }
}
