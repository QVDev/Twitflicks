package qvdev.examples.travisforandroid.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.MainView;
import com.qvdev.apps.twitflick.api.models.Buzzing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dirkwilmer on 7/3/13.
 */
public class BuzzingAdapterTest extends ActivityInstrumentationTestCase2<MainView> {

    BuzzingModel mBuzzingModel;
    List<Buzzing> mBuzzingData;
    BuzzingListAdapter mBuzzingAdapter;

    public BuzzingAdapterTest() {
        super(MainView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mBuzzingModel = new BuzzingModel();
        mBuzzingData = new ArrayList<Buzzing>();
        mBuzzingAdapter = new BuzzingListAdapter(getActivity(), R.layout.buzzing_list_circle_item, mBuzzingModel);
    }

    private void fillBuzzingData() {
        for (int i = 0; i < 100; i++) {
            Buzzing buzzing = new Buzzing();
            buzzing.setRating(4.0f);
            buzzing.setName("New Buzzing");

            mBuzzingData.add(buzzing);
        }

        //Special one we want to get and test the adapter with
        Buzzing buzzing = new Buzzing();
        buzzing.setRating(1.0f);
        buzzing.setName("Special Buzzing");

        mBuzzingData.add(4, buzzing);
    }

    public void testActivityCreation() {

        fillBuzzingData();

        mBuzzingModel.setBuzzing(mBuzzingData);
        assertEquals(mBuzzingData.isEmpty(), false);

        Buzzing testSpecialBuzzing = (Buzzing) mBuzzingAdapter.getItem(4);
        assertTrue(testSpecialBuzzing.getName().contentEquals("Special Buzzing"));
    }
}
