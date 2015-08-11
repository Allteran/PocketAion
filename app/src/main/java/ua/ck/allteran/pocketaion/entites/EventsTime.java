package ua.ck.allteran.pocketaion.entites;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Allteran on 6/6/2015.
 */
@RealmClass
public class EventsTime extends RealmObject {
    private int beginTime, endTime;
    private String day;

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
