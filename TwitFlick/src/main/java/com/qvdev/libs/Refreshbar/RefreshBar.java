package com.qvdev.libs.Refreshbar;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.ProgressBar;


public class RefreshBar extends ProgressBar implements OnTouchListener, OnRefreshFinishedListener, GestureDetector.OnGestureListener {

    private static String LOG_TAG = RefreshBar.class.getSimpleName();
    private static int PULL_ACTIVATE_DISTANCE = 1;

    private int mOffset = 0;
    private boolean isLoading = false;
    private View mView;
    private RefreshBarListener mRefreshListener;
    private GestureDetector mGestureDetector;


    public RefreshBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        reset();
    }


    public RefreshBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        reset();
    }


    public RefreshBar(Context context) {
        super(context);

        reset();
    }


    public void setRefreshableView(View view) {
        mView = view;
        mView.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(mView.getContext(), this);
    }


    public void setRefreshListener(RefreshBarListener listener) {
        mRefreshListener = listener;
    }


    @Override
    public void setProgress(int progress) {
        super.setProgress(progress);
        if (progress == getMax()) {
            if (mRefreshListener != null) {
                startLoadingProgress();
            } else {
                Log.d(LOG_TAG, "No listener attached, please attach listener via setRefreshListener method");
            }
            reset();
        }
    }


    private void reset() {
        mOffset = 0;
        setProgress(mOffset);
    }


    private void startLoadingProgress() {
        mRefreshListener.onStartLoadingContent();
        isLoading = true;
        setIndeterminate(isLoading);
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
                mOffset = 0;
                break;
        }
        setProgress(mOffset);
        return false;
    }

    private boolean listIsAtTop() {
        if (mView.getClass().getSuperclass().isAssignableFrom(AbsListView.class)) {
            AbsListView absView = (AbsListView) mView;
            if (absView.getChildCount() == 0)
                return true;
            return absView.getChildAt(0).getTop() == 0;
        } else {
            return true;
        }
    }


    @Override
    public void onRefreshFinished() {
        stopLoadingProgress();
    }


    private void stopLoadingProgress() {
        isLoading = false;
        setIndeterminate(isLoading);
        reset();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        mOffset = 0;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float distanceX, float distanceY) {
        handleDistance((int) distanceY);
        return false;
    }

    private void handleDistance(int distance) {
        if (listIsAtTop() && !isLoading) {
            if (distance < -PULL_ACTIVATE_DISTANCE) {
                mOffset++;
            } else if (distance > PULL_ACTIVATE_DISTANCE) {
                mOffset--;
            }
        } else {
            mOffset = 0;
        }
        setProgress(mOffset);
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        Log.d("APP", "V2::" + v2);
        return false;
    }
}
