package ua.ck.allteran.pocketaion.databases;

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

        PvPEvent realmEvent = realm.createObject(PvPEvent.class);
        realmEvent.setId(event.getId());
        realmEvent.setTime(event.getTime());
        realmEvent.setEventName(event.getEventName());

        realm.commitTransaction();
    }

    public ArrayList<PvPEvent> getAllEvents(Realm realm) {
        List<PvPEvent> events = realm.where(PvPEvent.class).findAll();
        return (ArrayList<PvPEvent>) events;
    }

    public void deleteEvent(Realm realm, PvPEvent event) {
        final List<PvPEvent> results = realm.where(PvPEvent.class).equalTo("id", event.getId())
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.remove(0);
            }
        });
    }
}
