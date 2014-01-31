package qvdev.examples.travisforandroid.tests;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.network.TwitFlicksBuzzingLoader;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by QVDev on 7/3/13.
 */
public class BuzzingNetworkingTest extends ActivityInstrumentationTestCase2<DetailView> implements LoaderManager.LoaderCallbacks<List<Buzzing>> {

    private static final int LOADER_BUZZING_ID = 0;

    public BuzzingNetworkingTest() {
        super(DetailView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testActivityCreation() {
        assertNotNull("Activity is null, check creating of activity", getActivity());
    }

    public void testGetBuzzing() {
        getActivity().getLoaderManager().initLoader(LOADER_BUZZING_ID, null, this);
    }


    @Override
    public Loader<List<Buzzing>> onCreateLoader(int i, Bundle bundle) {
        String url = "http://www.twitflicks.com/api/buzzing.json?count=1";
        return new TwitFlicksBuzzingLoader(getActivity(), url);
    }

    @Override
    public void onLoadFinished(Loader<List<Buzzing>> listLoader, List<Buzzing> buzzings) {
        Assert.assertNotNull(buzzings);
    }

    @Override
    public void onLoaderReset(Loader<List<Buzzing>> listLoader) {

    }
}
