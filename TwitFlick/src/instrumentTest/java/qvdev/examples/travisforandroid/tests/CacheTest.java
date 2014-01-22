package qvdev.examples.travisforandroid.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.listeners.onBuzzingResultListener;
import com.qvdev.apps.twitflick.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QVDev on 7/3/13.
 */
public class CacheTest extends ActivityInstrumentationTestCase2<DetailView> implements onBuzzingResultListener {

    List<Buzzing> mBuzzingList = new ArrayList<Buzzing>();

    public CacheTest() {
        super(DetailView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testCacheExists() {
        NetworkHelper networkHelper = new NetworkHelper(getInstrumentation().getTargetContext());
        networkHelper.getCachedBuzzing(this);
    }

    @Override
    public void onBuzzingRetrievalSuccess(List<Buzzing> buzzingList) {
        assertTrue("Cache does not exists", buzzingList.size() > 0);
    }

    @Override
    public void onBuzzingCachedRetrievalFailed() {
        assertFalse("Cache does not exists", mBuzzingList.size() > 0);
    }

    @Override
    public void onBuzzingRetrievalFailed() {
        assertFalse("Cache does not exists", mBuzzingList.size() > 0);
    }
}
