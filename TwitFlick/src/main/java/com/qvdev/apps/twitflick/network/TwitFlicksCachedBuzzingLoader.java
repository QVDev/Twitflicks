package com.qvdev.apps.twitflick.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qvdev.apps.twitflick.api.models.Buzzing;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by QVDev on 1/28/14.
 */
public class TwitFlicksCachedBuzzingLoader extends AsyncTaskLoader<List<Buzzing>> {

    private Gson mGSon = new Gson();
    List<Buzzing> mBuzzing;

    public TwitFlicksCachedBuzzingLoader(Context context) {
        super(context);
    }

    @Override
    public List<Buzzing> loadInBackground() {
        return getCachedBuzzing();
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

    private List<Buzzing> getCachedBuzzing() {

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

    private FileInputStream loadFilesystemConfiguration() {
        FileInputStream fInStream = null;
        try {
            File jsonFile = new File(getContext().getCacheDir(), TwitFlicksBuzzingLoader.FILE_NAME);
            Log.d("APP", "Exist::" + jsonFile.exists());
            fInStream = new FileInputStream(jsonFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fInStream;
    }
}
