package ua.ck.allteran.pocketaion.databases;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import ua.ck.allteran.pocketaion.entites.EventsTime;
import ua.ck.allteran.pocketaion.entites.PvPEvent;

/**
 * Created by Allteran on 5/31/2015.
 */
public class RealmHelper {

    public void addEventToDatabase(Realm realm, PvPEvent event) {
        realm.beginTransaction();

        RealmList<EventsTime> eventsTimes = new RealmList<>();
        for (int i = 0; i < event.getTime().size(); i++) {
            EventsTime time = realm.createObject(EventsTime.class);
            time.setBeginTime(event.getTime().get(i).getBeginTime());
            time.setEndTime(event.getTime().get(i).getEndTime());
            time.setDay(event.getTime().get(i).getDay());
            eventsTimes.add(time);
        }

        PvPEvent realmEvent = realm.createObject(PvPEvent.class);
        realmEvent.setId(event.getId());
        realmEvent.setTime(eventsTimes);
        realmEvent.setEventName(event.getEventName());

        realm.commitTransaction();
    }

    public List<PvPEvent> getAllEvents(Realm realm) {
        List<PvPEvent> eventsFromDatabase = realm.where(PvPEvent.class).findAll();
        ArrayList<PvPEvent> pvpEvent = new ArrayList<>();
        for (int i = 0; i < eventsFromDatabase.size(); i++) {
            PvPEvent event = new PvPEvent();
            event.setId(eventsFromDatabase.get(i).getId());
            event.setEventName(eventsFromDatabase.get(i).getEventName());

            RealmList<EventsTime> eventsTimes = new RealmList<>();

            for (int j = 0; j < eventsFromDatabase.get(i).getTime().size(); j++) {
                EventsTime time = new EventsTime();
                time.setBeginTime(eventsFromDatabase.get(i).getTime().get(j).getBeginTime());
                time.setEndTime(eventsFromDatabase.get(i).getTime().get(j).getEndTime());
                time.setDay(eventsFromDatabase.get(i).getTime().get(j).getDay());
                eventsTimes.add(time);
            }

            event.setTime(eventsTimes);
            pvpEvent.add(event);
        }
        return pvpEvent;
    }

    public void deleteEvent(Realm realm, PvPEvent event) {
        List<PvPEvent> results = realm.where(PvPEvent.class).equalTo("id", event.getId())
                .findAll();
        realm.beginTransaction();
        results.remove(0);
        realm.commitTransaction();
    }

    public void deleteAllEvents(Realm realm, Context activity, String databaseName) {
        realm.close();
        realm.deleteRealmFile(activity, databaseName);
    }

    public boolean isEventInDatabase(Realm realm, PvPEvent incomingEvent) {
        List<PvPEvent> result = realm.where(PvPEvent.class)
                .equalTo("id", incomingEvent.getId())
                .findAll();
        return result.size() != 0;
    }
}
