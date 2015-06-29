package ua.ck.allteran.pocketaion.entites;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Allteran on 5/27/2015.
 */
@RealmClass
public class PvPEvent extends RealmObject {
    private int id;
    private RealmList<EventsTime> time = new RealmList<>();
    private String eventName;

    public PvPEvent() {

    }

    public PvPEvent(int id, String eventName) {
        this.id = id;
        this.eventName = eventName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public RealmList<EventsTime> getTime() {
        return time;
    }

    public void setTime(RealmList<EventsTime> time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
