package qvdev.examples.travisforandroid.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.View.MainView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.network.NetworkHelper;

import java.util.List;

/**
 * Created by dirkwilmer on 7/3/13.
 */
public class CacheTest extends ActivityInstrumentationTestCase2<MainView> {

    public CacheTest() {
        super(MainView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testCacheExists() {
        NetworkHelper networkHelper = new NetworkHelper();
        List<Buzzing> buzzingList = networkHelper.getCachedBuzzing();

        assertNull("Cache does not exists", buzzingList);
    }
}
