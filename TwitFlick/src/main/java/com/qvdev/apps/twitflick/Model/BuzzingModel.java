package com.qvdev.apps.twitflick.Model;

import com.qvdev.apps.twitflick.api.models.Buzzing;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dirkwilmer on 7/29/13.
 */
public class BuzzingModel {

    private List<Buzzing> mBuzzing = new LinkedList<Buzzing>();

    public List<Buzzing> getBuzzing() {
        return mBuzzing;
    }

    public void setBuzzing(List<Buzzing> mBuzzing) {
        this.mBuzzing = mBuzzing;
    }
}
