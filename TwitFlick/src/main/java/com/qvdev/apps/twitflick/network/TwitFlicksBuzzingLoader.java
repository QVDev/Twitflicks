package com.qvdev.apps.twitflick.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qvdev.apps.twitflick.api.models.Buzzing;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by QVDev on 1/28/14.
 */
public class TwitFlicksBuzzingLoader extends AsyncTaskLoader<List<Buzzing>> {

    private static final String FILE_NAME = "buzzing.json";

    private Gson mGSon = new Gson();
    List<Buzzing> mBuzzing;
    String mRequestUrl;

    public TwitFlicksBuzzingLoader(Context context, String requestUrl) {
        super(context);
        mRequestUrl = requestUrl;
    }

    @Override
    public List<Buzzing> loadInBackground() {

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(mRequestUrl);
        List<Buzzing> buzzingResult = null;

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bfEnt = new BufferedHttpEntity(entity);

                InputStreamReader in = new InputStreamReader(bfEnt.getContent());
                BufferedReader readerTemp = new BufferedReader(in);

                Type listType = new TypeToken<List<Buzzing>>() {
                }.getType();


                try {
                    buzzingResult = mGSon.fromJson(readerTemp, listType);
                } catch (Exception e) {
                    Log.d("APP", "Exception" + e);
                }

                InputStreamReader in2 = new InputStreamReader(bfEnt.getContent());
                BufferedReader readerTemp2 = new BufferedReader(in2);
                saveFilesystemConfiguration(readerTemp2);

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
    public void deliverResult(List<Buzzing> buzzing) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (buzzing != null) {
                onReleaseResources(buzzing);
            }
        }

        List<Buzzing> oldBuzzing = mBuzzing;
        mBuzzing = buzzing;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(buzzing);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldBuzzing != null) {
            onReleaseResources(oldBuzzing);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mBuzzing != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mBuzzing);
        }

        if (mBuzzing == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    public void onCanceled(List<Buzzing> buzzing) {
        super.onCanceled(buzzing);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(buzzing);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mBuzzing != null) {
            onReleaseResources(mBuzzing);
            mBuzzing = null;
        }
    }

    protected void onReleaseResources(List<Buzzing> buzzing) {

    }

    private void saveFilesystemConfiguration(BufferedReader input) {

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        int readByte = 0;

        while (true) {
            try {
                readByte = input.read();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (readByte == -1) {
                break;
            }
            byteOutStream.write(readByte);
        }
        try {
            byteOutStream.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            input.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            byteOutStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        byte[] response = byteOutStream.toByteArray();

        File cacheDir = getContext().getCacheDir();
        File jsonFile = new File(cacheDir, FILE_NAME);
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(jsonFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < response.length; i++) {
            try {
                outStream.write(response[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
