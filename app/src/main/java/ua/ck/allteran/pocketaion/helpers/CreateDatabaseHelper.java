package ua.ck.allteran.pocketaion.helpers;

import java.util.ArrayList;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.entites.PvPEvent;

/**
 * Created by Allteran on 5/31/2015.
 */
public class CreateDatabaseHelper {

    public void createEventDatabase(Realm realm) {
        ArrayList<PvPEvent> events = new ArrayList<>();
        PvPEvent event;

        /**Monday**/
        event = new PvPEvent();
        event.setId(1);
        event.setEventName("Daily reset");
        event.setBeginTime(9);
        event.setEndTime(9);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);

        event = new PvPEvent();
        event.setId(2);
        event.setEventName("Arena");
        event.setBeginTime(10);
        event.setEndTime(13);
        event.setDays("Monday");
        events.add(event);

        event = new PvPEvent();
        event.setId(3);
        event.setEventName("Dredgion");
        event.setBeginTime(12);
        event.setEndTime(14);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);

        event = new PvPEvent();
        event.setId(4);
        event.setEventName("Engulfed Ophidan Bridge");
        event.setBeginTime(12);
        event.setEndTime(14);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);

        event = new PvPEvent();
        event.setId(5);
        event.setEventName("Sillus Siege");
        event.setBeginTime(14);
        event.setEndTime(15);
        event.setDays("Monday", "Saturday");
        events.add(event);

        event = new PvPEvent();
        event.setId(6);
        event.setEventName("Sulfur Siege");
        event.setBeginTime(16);
        event.setEndTime(17);
        event.setDays("Monday", "Friday", "Saturday");
        events.add(event);

        event = new PvPEvent();
        event.setId(7);
        event.setEventName("Asteria Siege");
        event.setBeginTime(16);
        event.setEndTime(17);
        event.setDays("Monday", "Friday", "Saturday");
        events.add(event);

        event = new PvPEvent();
        event.setId(8);
        event.setEventName("Roah Siege");
        event.setBeginTime(16);
        event.setEndTime(17);
        event.setDays("Monday", "Friday", "Saturday");
        events.add(event);

        event = new PvPEvent();
        event.setId(9);
        event.setEventName("Arena");
        event.setBeginTime(18);
        event.setEndTime(23);
        event.setDays("Monday");
        events.add(event);

        event = new PvPEvent();
        event.setId(10);
        event.setEventName("Tia Forts Siege");
        event.setBeginTime(18);
        event.setEndTime(19);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);

        event = new PvPEvent();
        event.setId(11);
        event.setEventName("Engulfed Ophidan Bridge");
        event.setBeginTime(19);
        event.setEndTime(21);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);

        event = new PvPEvent();
        event.setId(12);
        event.setEventName("Dredgion");
        event.setBeginTime(20);
        event.setEndTime(22);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);
        //edit name
        event = new PvPEvent();
        event.setId(13);
        event.setEventName("ToS Siege");
        event.setBeginTime(20);
        event.setEndTime(21);
        event.setDays("Monday", "Wednesday");
        events.add(event);
        //edit name
        event = new PvPEvent();
        event.setId(14);
        event.setEventName("Vorgatem Siege");
        event.setBeginTime(20);
        event.setEndTime(21);
        event.setDays("Monday", "Wednesday");
        events.add(event);

        event = new PvPEvent();
        event.setId(15);
        event.setEventName("Kamar Battlefield");
        event.setBeginTime(21);
        event.setEndTime(23);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);

        event = new PvPEvent();
        event.setId(16);
        event.setEventName("Krotan Siege");
        event.setBeginTime(22);
        event.setEndTime(23);
        event.setDays("Monday", "Friday", "Saturday");
        events.add(event);

        event = new PvPEvent();
        event.setId(17);
        event.setEventName("Miren Siege");
        event.setBeginTime(22);
        event.setEndTime(23);
        event.setDays("Monday", "Tuesday", "Friday", "Saturday");
        events.add(event);

        event = new PvPEvent();
        event.setId(18);
        event.setEventName("Engulfed Ophidan Bridge");
        event.setBeginTime(21);
        event.setEndTime(23);
        event.setDays("Monday");
        events.add(event);

        event = new PvPEvent();
        event.setId(19);
        event.setEventName("D/C Arena");
        event.setBeginTime(0);
        event.setEndTime(2);
        event.setDays("Monday");
        events.add(event);

        event = new PvPEvent();
        event.setId(20);
        event.setEventName("Dredgion");
        event.setBeginTime(0);
        event.setEndTime(2);
        event.setDays("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        events.add(event);

        /**Tuesday**/
    }
}
