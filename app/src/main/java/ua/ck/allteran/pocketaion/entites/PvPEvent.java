package ua.ck.allteran.pocketaion.entites;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Allteran on 5/27/2015.
 */

public class PvPEvent extends RealmObject{
    @PrimaryKey
    private int id;
    private int mBeginTime;
    private int mEndTime;
    private String mEventName;
    private String[] mDays;

    public int getBeginTime() {
        return mBeginTime;
    }

    public void setBeginTime(int mBeginTime) {
        this.mBeginTime = mBeginTime;
    }

    public int getEndTime() {
        return mEndTime;
    }

    public void setEndTime(int mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getEventName() {
        return mEventName;
    }

    public void setEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getDays() {
        return mDays;
    }

    public void setDays(String... days) {
        mDays = days;
    }
}
