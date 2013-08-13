package qvdev.examples.travisforandroid.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.View.MainView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.com.qvdev.apps.twitflick.network.NetworkHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by dirkwilmer on 7/3/13.
 */
public class BuzzingNetworkingTest extends ActivityInstrumentationTestCase2<MainView> {

    private NetworkHelper mNetworkerHelper;

    public BuzzingNetworkingTest() {
        super(MainView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mNetworkerHelper = new NetworkHelper(null);
    }

    public void testActivityCreation() {

        assertNotNull("Activity is null, check creating of activity", getActivity());

        try {
            List<Buzzing> buzzingResult = mNetworkerHelper.getJson(new URL("http://www.twitflicks.com/api/buzzing.json?count=1"));
            assertNotNull("Buzzing result is null", buzzingResult);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
}
