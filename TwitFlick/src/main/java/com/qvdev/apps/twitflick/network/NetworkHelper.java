package com.qvdev.apps.twitflick.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.api.models.BuzzingDetail;
import com.qvdev.apps.twitflick.listeners.onBuzzingDetailsResultListener;
import com.qvdev.apps.twitflick.listeners.onBuzzingResultListener;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class NetworkHelper {

    private static final String FILE_NAME = "json.json";
    //TODO::Maybe implement VOLLEY?
    //http://blog.bignerdranch.com/3177-solving-the-android-image-loading-problem-volley-vs-picasso/
    //TODO::Think of a general Twitlfick api??
    private Gson mGSon = new Gson();
    private onBuzzingResultListener mBuzzingResultListener = null;
    private onBuzzingDetailsResultListener mBuzzingDetailsResultListener;
    private Context mContext;

    public NetworkHelper(Context context) {
        mContext = context;
    }

    public void getBuzzing(onBuzzingResultListener listener, URL[] urls) {
        mBuzzingResultListener = listener;
        buzzing.execute(urls);
    }

    public void getBuzzingDetails(onBuzzingDetailsResultListener listener, URL[] urls) {
        mBuzzingDetailsResultListener = listener;
        buzzingDetails.execute(urls);
    }

    public AsyncTask<URL, Integer, List<Buzzing>> buzzing = new AsyncTask<URL, Integer, List<Buzzing>>() {

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
        protected List<Buzzing> doInBackground(URL... urls) {
            return getJson(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Buzzing> result) {
            if (result != null) {
                mBuzzingResultListener.onBuzzingRetrievalSuccess(result);
            } else {
                mBuzzingResultListener.onBuzzingRetrievalFailed();
            }
        }
    };


    public AsyncTask<URL, Integer, BuzzingDetail> buzzingDetails = new AsyncTask<URL, Integer, BuzzingDetail>() {

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

    public List<Buzzing> getCachedBuzzing() {

        List<Buzzing> buzzingResult = null;

        FileInputStream fInStream = loadFilesystemConfiguration();

        Type listType = new TypeToken<List<Buzzing>>() {
        }.getType();


        try {
            buzzingResult = mGSon.fromJson(new InputStreamReader(fInStream), listType);

        } catch (Exception e) {
            Log.d("APP", "Exception" + e);
        }

        return buzzingResult;
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

        File cacheDir = mContext.getCacheDir();
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

    private FileInputStream loadFilesystemConfiguration() {
        FileInputStream fInStream = null;
        try {
            File jsonFile = new File(mContext.getCacheDir(), FILE_NAME);
            Log.d("APP", "Exist::" + jsonFile.exists());
            fInStream = new FileInputStream(jsonFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fInStream;
    }
}
