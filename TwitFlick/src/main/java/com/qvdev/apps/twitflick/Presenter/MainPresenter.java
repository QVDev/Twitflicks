package com.qvdev.apps.twitflick.Presenter;

import com.qvdev.apps.twitflick.Model.MainModel;
import com.qvdev.apps.twitflick.View.MainView;

/**
 * Created by dirkwilmer on 7/26/13.
 */
public class MainPresenter {

    private final MainView mMainView;
    private MainModel mMainModel;

    public MainPresenter(MainView view) {
        mMainView = view;
        mMainModel = new MainModel();
    }
}
