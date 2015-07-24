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

    public void addEventToDatabase(Context context, String databaseFileName, PvPEvent event) {
        Realm realm = Realm.getInstance(context,databaseFileName);
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
        realm.close();
    }

    public List<PvPEvent> getAllEvents(Context context, String databaseFileName) {
        Realm realm = Realm.getInstance(context, databaseFileName);

        List<PvPEvent> eventsFromDatabase = realm.where(PvPEvent.class).findAll();
        ArrayList<PvPEvent> pvpEvents = new ArrayList<>();
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
            pvpEvents.add(event);
        }

        realm.close();
        return pvpEvents;
    }

    public void deleteEvent(Context context, String databaseFileName, PvPEvent event) {
        Realm realm = Realm.getInstance(context, databaseFileName);

        List<PvPEvent> results = realm.where(PvPEvent.class).equalTo("id", event.getId())
                .findAll();
        realm.beginTransaction();
        results.remove(0);
        realm.commitTransaction();

        realm.close();
    }

    public void deleteAllEvents(Context activity, String databaseName) {
        Realm.deleteRealmFile(activity, databaseName);
    }

    public boolean isEventInDatabase(Context context, String databaseFileName, PvPEvent incomingEvent) {
        Realm realm = Realm.getInstance(context, databaseFileName);

        List<PvPEvent> searchResult = realm.where(PvPEvent.class)
                .equalTo("id", incomingEvent.getId())
                .findAll();
        boolean isEventInDatabase = searchResult.size() != 0;
        realm.close();
        return isEventInDatabase;
    }
}
