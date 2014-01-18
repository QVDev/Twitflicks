package com.qvdev.apps.twitflick.Presenter;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.qvdev.apps.twitflick.Adapters.BuzzingListAdapter;
import com.qvdev.apps.twitflick.Model.BuzzingModel;
import com.qvdev.apps.twitflick.R;
import com.qvdev.apps.twitflick.View.BuzzingView;
import com.qvdev.apps.twitflick.View.DetailView;
import com.qvdev.apps.twitflick.api.models.Buzzing;
import com.qvdev.apps.twitflick.listeners.onBuzzingItemClickedListener;
import com.qvdev.apps.twitflick.listeners.onBuzzingResultListener;
import com.qvdev.apps.twitflick.network.NetworkHelper;
import com.qvdev.libs.Refreshbar.RefreshBarListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QVDev on 7/29/13.
 */
public class BuzzingPresenter implements onBuzzingResultListener, onBuzzingItemClickedListener, RefreshBarListener, PopupMenu.OnMenuItemClickListener {

    private BuzzingView mBuzzingView;
    private BuzzingModel mBuzzingModel;
    private BuzzingListAdapter mBuzzingListAdapter;

    public BuzzingPresenter(BuzzingView buzzingView) {
        mBuzzingView = buzzingView;
        mBuzzingModel = new BuzzingModel();

        init();
    }

    private void init() {
        mBuzzingListAdapter = new BuzzingListAdapter(mBuzzingView, R.layout.buzzing_list_circle_item, mBuzzingModel);
        mBuzzingListAdapter.setOnBuzzingItemClicked(this);
        mBuzzingView.setAdapter(mBuzzingListAdapter);

        getCachedBuzzing();
    }

    private void getCachedBuzzing() {
        mBuzzingView.showProgress();
        NetworkHelper networkHelper = new NetworkHelper(mBuzzingView);
        networkHelper.getCachedBuzzing(this);
    }

    public void resumed() {
        mBuzzingView.setAdapter(mBuzzingListAdapter);
    }


    private void getBuzzing() {
        NetworkHelper networkHelper = new NetworkHelper(mBuzzingView);
        URL url = null;
        try {
            url = new URL("" + mBuzzingView.getString(R.string.base_url) + mBuzzingView.getString(R.string.api_url) + mBuzzingView.getString(R.string.buzzing_url) + mBuzzingView.getString(R.string.buzzing_retrieve_count) + mBuzzingView.getString(R.string.buzzing_retrieve_limit));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        networkHelper.getBuzzing(this, new URL[]{url});
    }


    @Override
    public void onPopupClicked(View view, int position) {
        mBuzzingModel.popupPosition = position;

        PopupMenu popup = new PopupMenu(mBuzzingView, view);
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


    public void onLikeClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);
        String likeText = mBuzzingView.getString(R.string.share_like, buzzing.getName(), (int) buzzing.getID());
        share(likeText);
    }

    public void onHateClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);
        String hateText = mBuzzingView.getString(R.string.share_hate, buzzing.getName(), (int) buzzing.getID());
        share(hateText);
    }

    @Override
    public void onViewClicked(int position) {
        Buzzing buzzing = mBuzzingModel.getBuzzing().get(position);

        Intent intent = new Intent(mBuzzingView, DetailView.class);
        intent.putExtra(DetailPresenter.EXTRA_MESSAGE_ID, buzzing.getID());
        mBuzzingView.startActivity(intent);
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
    public void onBuzzingCachedRetrievalFailed() {
        setPullToRefresh();
    }

    private void setPullToRefresh() {
        if (mBuzzingListAdapter.getCount() == 0) {
            Buzzing buzzer = new Buzzing();
            buzzer.setName("Pull to fetch data");

            ArrayList<Buzzing> buzzingList = new ArrayList<Buzzing>();
            buzzingList.add(buzzer);

            refresh(buzzingList);
        }

        mBuzzingView.onRefreshFinished();
    }

    @Override
    public void onBuzzingRetrievalFailed() {
        Toast.makeText(mBuzzingView, "Failed to fetch data", Toast.LENGTH_LONG).show();
        mBuzzingView.onRefreshFinished();
    }

    public void refresh(List<Buzzing> result) {
        mBuzzingModel.setBuzzing(result);
        mBuzzingListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStartLoadingContent() {
        getBuzzing();
    }
}
