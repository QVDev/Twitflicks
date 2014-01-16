package com.qvdev.apps.twitflick.Model;

import com.qvdev.apps.twitflick.api.models.Buzzing;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 * Created by QVDev on 7/29/13.
 */
public class BuzzingModel extends Observable{

    private List<Buzzing> mBuzzing = new LinkedList<Buzzing>();

    public List<Buzzing> getBuzzing() {
        return mBuzzing;
    }

    public void setBuzzing(List<Buzzing> mBuzzing) {
        this.mBuzzing = mBuzzing;
    }

    public int popupPosition;
}
