package com.qvdev.apps.twitflick.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.qvdev.apps.twitflick.listeners.onBuzzingDetailsResultListener;
import com.qvdev.apps.twitflick.listeners.onBuzzingResultListener;

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

/**
 * Created by QVDev on 7/26/13.
 */
public class NetworkHelper {

    private static final String FILE_NAME = "json.json";

    private Gson mGSon = new Gson();
    private onBuzzingResultListener mBuzzingResultListener = null;
    private onBuzzingDetailsResultListener mBuzzingDetailsResultListener;
    private Context mContext;

    public NetworkHelper(Context context) {
        mContext = context;
    }

    public void getBuzzingDetails(onBuzzingDetailsResultListener listener, URL[] urls) {
        mBuzzingDetailsResultListener = listener;
        buzzingDetails.execute(urls);
    }


    private AsyncTask<URL, Integer, BuzzingDetail> buzzingDetails = new AsyncTask<URL, Integer, BuzzingDetail>() {

        public BuzzingDetail getJson(URL url) {

            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url.toString());
            BuzzingDetail buzzingDetailResult = null;

            try {
                HttpResponse response = client.execute(httpGet);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();

                    Reader reader = new InputStreamReader(content);
                    Type listType = new TypeToken<BuzzingDetail>() {
                    }.getType();

                    try {
                        buzzingDetailResult = mGSon.fromJson(reader, listType);
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

            return buzzingDetailResult;
        }

        @Override
        protected BuzzingDetail doInBackground(URL... urls) {
            return getJson(urls[0]);
        }

        @Override
        protected void onPostExecute(BuzzingDetail result) {
            if (result != null) {
                mBuzzingDetailsResultListener.onBuzzingRetrievalSuccess(result);
            } else {
                mBuzzingDetailsResultListener.onBuzzingRetrievalFailed();
            }
        }
    };



}
