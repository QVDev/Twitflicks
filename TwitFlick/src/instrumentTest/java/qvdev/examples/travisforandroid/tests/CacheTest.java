package qvdev.examples.travisforandroid.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.network.NetworkHelper;

import java.util.List;

/**
 * Created by QVDev on 7/3/13.
 */
public class CacheTest extends ActivityInstrumentationTestCase2<DetailView> {

    public CacheTest() {
        super(DetailView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testCacheExists() {
        NetworkHelper networkHelper = new NetworkHelper(getInstrumentation().getTargetContext());
        List<Buzzing> buzzingList = networkHelper.getCachedBuzzing();

        if (buzzingList != null) {
            assertTrue("Cache does not exists", buzzingList.size() > 0);
        }
    }
}
