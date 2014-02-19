package qvdev.examples.travisforandroid.tests;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.network.TwitFlicksCachedBuzzingLoader;

import java.util.List;

/**
 * Created by QVDev on 7/3/13.
 */
public class CacheTest extends ActivityInstrumentationTestCase2<DetailView> implements LoaderManager.LoaderCallbacks<List<Buzzing>> {

    private static final int LOADER_CAHCED_BUZZING_ID = 1;

    public CacheTest() {
        super(DetailView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        intent.setClassName("com.qvdev.apps.twitflick.View", "DetailView");
        setActivityIntent(intent);
    }

    public void testCacheExists() {
        getActivity().getLoaderManager().initLoader(LOADER_CAHCED_BUZZING_ID, null, this);
    }

    @Override
    public Loader<List<Buzzing>> onCreateLoader(int i, Bundle bundle) {
        return new TwitFlicksCachedBuzzingLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Buzzing>> listLoader, List<Buzzing> buzzings) {
        assertTrue("Cache exists", buzzings.size() > 0);
    }

    @Override
    public void onLoaderReset(Loader<List<Buzzing>> listLoader) {

    }
}
