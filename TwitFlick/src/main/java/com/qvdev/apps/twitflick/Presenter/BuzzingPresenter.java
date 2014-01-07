package com.qvdev.apps.twitflick.Presenter;

import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.BuzzingView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.listeners.onBuzzingItemClickedListener;
import com.qvdev.apps.twitflick.listeners.onBuzzingListItemClicked;
import com.qvdev.apps.twitflick.listeners.onBuzzingResultListener;
import com.qvdev.apps.twitflick.network.NetworkHelper;
import com.qvdev.libs.Refreshbar.RefreshBarListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dirkwilmer on 7/29/13.
 */
public class BuzzingPresenter implements onBuzzingResultListener, onBuzzingItemClickedListener, RefreshBarListener, PopupMenu.OnMenuItemClickListener {

    private static final String FORCE_FULLSCREEN = "force_fullscreen";

    private BuzzingView mBuzzingView;
    private BuzzingModel mBuzzingModel;
    private BuzzingListAdapter mBuzzingListAdapter;

    private onBuzzingListItemClicked mExternalItemListener;

    public BuzzingPresenter(BuzzingView buzzingView) {
        mBuzzingView = buzzingView;
        mBuzzingModel = new BuzzingModel();

        init();
        getCachedBuzzing();
    }

    private void getCachedBuzzing() {
        NetworkHelper networkHelper = new NetworkHelper();
        List<Buzzing> buzzingList = networkHelper.getCachedBuzzing();

        if (buzzingList != null) {
            onBuzzingRetrievalSuccess(buzzingList);
        } else {
            Buzzing buzzer = new Buzzing();
            buzzer.setName("Pull to fetch data");

            buzzingList = new ArrayList<Buzzing>();
            buzzingList.add(buzzer);

            refresh(buzzingList);
        }
    }

    private void init() {
        mBuzzingListAdapter = new BuzzingListAdapter(mBuzzingView.getActivity(), R.layout.buzzing_list_circle_item, mBuzzingModel);
        mBuzzingListAdapter.setOnBuzzingItemClicked(this);
        mBuzzingView.setAdapter(mBuzzingListAdapter);

        mExternalItemListener = (onBuzzingListItemClicked) mBuzzingView.getActivity();
    }


    private void getBuzzing() {
        NetworkHelper networkHelper = new NetworkHelper();
        URL url = null;
        try {
            url = new URL("" + mBuzzingView.getString(R.string.base_url) + mBuzzingView.getString(R.string.api_url) + mBuzzingView.getString(R.string.buzzing_url) + mBuzzingView.getString(R.string.buzzing_retrieve_count) + mBuzzingView.getString(R.string.buzzing_retrieve_limit));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        networkHelper.getBuzzing(this, new URL[]{url});
    }

    @Override
    public void onTrailerClicked(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.putExtra(FORCE_FULLSCREEN, true);
        mBuzzingView.startActivity(intent);
    }

    @Override
    public void onPopupClicked(View view, int position) {
        mBuzzingModel.popupPosition = position;

        PopupMenu popup = new PopupMenu(mBuzzingView.getActivity(), view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.buzzing_actions);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.share_like:
                onLikeClicked(mBuzzingModel.popupPosition);
                return true;
            case R.id.share_dislike:
                onHateClicked(mBuzzingModel.popupPosition);
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onLikeClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);
        String likeText = mBuzzingView.getString(R.string.share_like, buzzing.getName(), (int) buzzing.getID());
        share(likeText);
    }

    @Override
    public void onHateClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);
        String hateText = mBuzzingView.getString(R.string.share_hate, buzzing.getName(), (int) buzzing.getID());
        share(hateText);
    }

    @Override
    public void onViewClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);
        mExternalItemListener.onBuzzingItemSelected(buzzing.getID());
    }

    private void share(String shareText) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        mBuzzingView.startActivity(Intent.createChooser(sendIntent, mBuzzingView.getResources().getText(R.string.send_to)));
    }

    @Override
    public void onBuzzingRetrievalSuccess(List<Buzzing> buzzingList) {
        refresh(buzzingList);
        mBuzzingView.onRefreshFinished();
    }

    @Override
    public void onBuzzingRetrievalFailed() {
        Toast.makeText(mBuzzingView.getActivity(), "Failed to fetch data", Toast.LENGTH_LONG).show();
        mBuzzingView.onRefreshFinished();
    }

    public void refresh(List<Buzzing> result) {
        mBuzzingModel.getBuzzing().clear();
        mBuzzingModel.getBuzzing().addAll(result);
        mBuzzingListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartLoadingContent() {
        getBuzzing();
    }
}
