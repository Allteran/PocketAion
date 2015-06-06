package ua.ck.allteran.pocketaion.entites;

import io.realm.RealmObject;

/**
 * Created by Allteran on 6/6/2015.
 */
public class EventsTime extends RealmObject {
    private int beginTime, endTime;
    private String day;

    public EventsTime() {

    }

    public EventsTime (int beginTime, int endTime, String day) {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.day = day;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
