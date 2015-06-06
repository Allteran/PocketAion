package ua.ck.allteran.pocketaion.databases;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import ua.ck.allteran.pocketaion.entites.EventsTime;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 5/31/2015.
 * <p/>
 * To describe time I separated object, which has int fields beginTime, endTime and String - day.
 * Time is measuring in numbers from 0 to 23.
 */
public class CreateDatabaseHelper {

    public void createEventDatabase(Realm realm) {
        ArrayList<PvPEvent> events = new ArrayList<>();
        PvPEvent event;
        RealmList<EventsTime> times;

        /**Daily reset**/
        event = new PvPEvent();
        times = event.getTime();

        addDayAndTime(times, 9, 9, Const.DAY_MONDAY);
        addDayAndTime(times, 9, 9, Const.DAY_MONDAY);
        addDayAndTime(times, 9, 9, Const.DAY_TUESDAY);
        addDayAndTime(times, 9, 9, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 9, 9, Const.DAY_THURSDAY);
        addDayAndTime(times, 9, 9, Const.DAY_FRIDAY);
        addDayAndTime(times, 9, 9, Const.DAY_SATURDAY);
        addDayAndTime(times, 9, 9, Const.DAY_SUNDAY);

        event.setId(1);
        event.setEventName("Daily reset");
        event.setTime(times);
        events.add(event);

        /**Arena**/
        event = new PvPEvent();
        times = event.getTime();

        addDayAndTime(times, 10, 14, Const.DAY_MONDAY);
        addDayAndTime(times, 18, 0, Const.DAY_MONDAY);
        addDayAndTime(times, 10, 14, Const.DAY_TUESDAY);
        addDayAndTime(times, 18, 0, Const.DAY_TUESDAY);
        addDayAndTime(times, 10, 14, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 18, 0, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 10, 14, Const.DAY_THURSDAY);
        addDayAndTime(times, 18, 0, Const.DAY_THURSDAY);
        addDayAndTime(times, 10, 14, Const.DAY_FRIDAY);
        addDayAndTime(times, 18, 0, Const.DAY_FRIDAY);
        addDayAndTime(times, 10, 20, Const.DAY_SATURDAY);
        addDayAndTime(times, 22, 2, Const.DAY_SATURDAY);
        addDayAndTime(times, 10, 20, Const.DAY_SUNDAY);
        addDayAndTime(times, 22, 2, Const.DAY_SUNDAY);
        event.setId(2);
        event.setEventName("Arena");
        event.setTime(times);
        events.add(event);

        /**Dredgion**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 12, 14, Const.DAY_MONDAY);
        addDayAndTime(times, 20, 22, Const.DAY_MONDAY);
        addDayAndTime(times, 0, 2, Const.DAY_MONDAY);
        addDayAndTime(times, 12, 14, Const.DAY_TUESDAY);
        addDayAndTime(times, 20, 22, Const.DAY_TUESDAY);
        addDayAndTime(times, 0, 2, Const.DAY_TUESDAY);
        addDayAndTime(times, 12, 14, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 20, 22, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 0, 2, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 12, 14, Const.DAY_TUESDAY);
        addDayAndTime(times, 20, 22, Const.DAY_TUESDAY);
        addDayAndTime(times, 0, 2, Const.DAY_TUESDAY);
        addDayAndTime(times, 12, 14, Const.DAY_FRIDAY);
        addDayAndTime(times, 20, 22, Const.DAY_FRIDAY);
        addDayAndTime(times, 0, 2, Const.DAY_FRIDAY);
        addDayAndTime(times, 12, 14, Const.DAY_SATURDAY);
        addDayAndTime(times, 20, 22, Const.DAY_SATURDAY);
        addDayAndTime(times, 0, 2, Const.DAY_SATURDAY);
        addDayAndTime(times, 12, 14, Const.DAY_SUNDAY);
        addDayAndTime(times, 20, 22, Const.DAY_SUNDAY);
        addDayAndTime(times, 0, 2, Const.DAY_SUNDAY);
        event.setId(3);
        event.setEventName("Dredgion");
        event.setTime(times);
        events.add(event);

        /**Ophidan Bridge**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 12, 14, Const.DAY_MONDAY);
        addDayAndTime(times, 19, 21, Const.DAY_MONDAY);
        addDayAndTime(times, 23, 1, Const.DAY_MONDAY);
        addDayAndTime(times, 12, 14, Const.DAY_TUESDAY);
        addDayAndTime(times, 19, 21, Const.DAY_TUESDAY);
        addDayAndTime(times, 23, 1, Const.DAY_TUESDAY);
        addDayAndTime(times, 12, 14, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 19, 21, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 23, 1, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 12, 14, Const.DAY_THURSDAY);
        addDayAndTime(times, 19, 21, Const.DAY_THURSDAY);
        addDayAndTime(times, 23, 1, Const.DAY_THURSDAY);
        addDayAndTime(times, 12, 14, Const.DAY_FRIDAY);
        addDayAndTime(times, 19, 21, Const.DAY_FRIDAY);
        addDayAndTime(times, 23, 1, Const.DAY_FRIDAY);
        addDayAndTime(times, 12, 14, Const.DAY_SATURDAY);
        addDayAndTime(times, 19, 21, Const.DAY_SATURDAY);
        addDayAndTime(times, 23, 1, Const.DAY_SATURDAY);
        addDayAndTime(times, 12, 14, Const.DAY_SUNDAY);
        addDayAndTime(times, 19, 21, Const.DAY_SUNDAY);
        addDayAndTime(times, 23, 1, Const.DAY_SUNDAY);
        event.setId(4);
        event.setEventName("Engulfed Ophidan Bridge");
        event.setTime(times);
        events.add(event);

        /**Kamar Battlefield**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 21, 23, Const.DAY_MONDAY);
        addDayAndTime(times, 21, 23, Const.DAY_TUESDAY);
        addDayAndTime(times, 21, 23, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 21, 23, Const.DAY_THURSDAY);
        addDayAndTime(times, 21, 23, Const.DAY_FRIDAY);
        addDayAndTime(times, 21, 23, Const.DAY_SUNDAY);
        addDayAndTime(times, 21, 23, Const.DAY_SUNDAY);
        event.setId(5);
        event.setEventName("Kamar Battlefield");
        event.setTime(times);
        events.add(event);

        /**Tia Forts Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 18, 19, Const.DAY_MONDAY);
        addDayAndTime(times, 18, 19, Const.DAY_TUESDAY);
        addDayAndTime(times, 18, 19, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 18, 19, Const.DAY_THURSDAY);
        addDayAndTime(times, 18, 19, Const.DAY_FRIDAY);
        addDayAndTime(times, 18, 19, Const.DAY_SATURDAY);
        addDayAndTime(times, 18, 19, Const.DAY_SUNDAY);
        event.setId(6);
        event.setEventName("Tia Forts Siege");
        event.setTime(times);
        events.add(event);

        /**Discipline&Chaos**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 0, 2, Const.DAY_MONDAY);
        addDayAndTime(times, 0, 2, Const.DAY_TUESDAY);
        addDayAndTime(times, 0, 2, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 0, 2, Const.DAY_THURSDAY);
        addDayAndTime(times, 0, 2, Const.DAY_FRIDAY);
        event.setId(7);
        event.setEventName("Discipline&Chaos");
        event.setTime(times);
        events.add(event);

        /**Sillus Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 14, 15, Const.DAY_MONDAY);
        addDayAndTime(times, 20, 21, Const.DAY_THURSDAY);
        addDayAndTime(times, 14, 15, Const.DAY_SATURDAY);
        addDayAndTime(times, 14, 15, Const.DAY_SUNDAY);
        event.setId(8);
        event.setEventName("Sillus Siege");
        event.setTime(times);
        events.add(event);

        /**Sulfur Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 16, 17, Const.DAY_MONDAY);
        addDayAndTime(times, 22, 23, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 16, 17, Const.DAY_FRIDAY);
        addDayAndTime(times, 16, 17, Const.DAY_SATURDAY);
        addDayAndTime(times, 22, 23, Const.DAY_SUNDAY);
        event.setId(9);
        event.setEventName("Sulfur Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Asteria Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 16, 17, Const.DAY_MONDAY);
        addDayAndTime(times, 22, 23, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 16, 17, Const.DAY_FRIDAY);
        addDayAndTime(times, 16, 17, Const.DAY_SATURDAY);
        addDayAndTime(times, 22, 23, Const.DAY_SUNDAY);
        event.setId(10);
        event.setEventName("Asteria Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Roah Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 16, 17, Const.DAY_MONDAY);
        addDayAndTime(times, 22, 23, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 16, 17, Const.DAY_FRIDAY);
        addDayAndTime(times, 16, 17, Const.DAY_SATURDAY);
        addDayAndTime(times, 22, 23, Const.DAY_SUNDAY);
        event.setId(11);
        event.setEventName("Roah Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Siel's Forts Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 16, 17, Const.DAY_TUESDAY);
        addDayAndTime(times, 22, 23, Const.DAY_THURSDAY);
        addDayAndTime(times, 16, 17, Const.DAY_FRIDAY);
        addDayAndTime(times, 16, 17, Const.DAY_SATURDAY);
        addDayAndTime(times, 22, 23, Const.DAY_SUNDAY);
        event.setId(12);
        event.setEventName("Siel's Forts Siege");
        event.setTime(times);
        events.add(event);

        /**Krotan Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 22, 23, Const.DAY_MONDAY);
        addDayAndTime(times, 16, 17, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 16, 17, Const.DAY_THURSDAY);
        addDayAndTime(times, 22, 23, Const.DAY_FRIDAY);
        addDayAndTime(times, 22, 23, Const.DAY_SATURDAY);
        addDayAndTime(times, 16, 17, Const.DAY_SUNDAY);
        event.setId(13);
        event.setEventName("Krotan Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Kysis Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 22, 23, Const.DAY_TUESDAY);
        addDayAndTime(times, 16, 17, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 22, 23, Const.DAY_FRIDAY);
        addDayAndTime(times, 22, 23, Const.DAY_SATURDAY);
        addDayAndTime(times, 16, 17, Const.DAY_SUNDAY);
        event.setId(14);
        event.setEventName("Kysis Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Miren Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 22, 23, Const.DAY_MONDAY);
        addDayAndTime(times, 22, 23, Const.DAY_TUESDAY);
        addDayAndTime(times, 16, 17, Const.DAY_THURSDAY);
        addDayAndTime(times, 22, 23, Const.DAY_FRIDAY);
        addDayAndTime(times, 22, 23, Const.DAY_SATURDAY);
        addDayAndTime(times, 16, 17, Const.DAY_SUNDAY);
        event.setId(15);
        event.setEventName("Miren Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Divine Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 0, 1, Const.DAY_FRIDAY);
        addDayAndTime(times, 20, 21, Const.DAY_SUNDAY);
        event.setId(16);
        event.setEventName("Divine Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Silona Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 14, 15, Const.DAY_TUESDAY);
        addDayAndTime(times, 14, 15, Const.DAY_FRIDAY);
        addDayAndTime(times, 14, 15, Const.DAY_SUNDAY);
        event.setId(17);
        event.setEventName("Silona Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Pradeth Forth Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 14, 15, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 20, 21, Const.DAY_FRIDAY);
        addDayAndTime(times, 14, 15, Const.DAY_SUNDAY);
        event.setId(18);
        event.setEventName("Pradeth Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Temple Of Scales Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 20, 21, Const.DAY_MONDAY);
        addDayAndTime(times, 20, 21, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 11, 12, Const.DAY_SATURDAY);
        event.setId(19);
        event.setEventName("Temple Of Scales Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Altar Of Avarice Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 20, 21, Const.DAY_TUESDAY);
        addDayAndTime(times, 11, 12, Const.DAY_SUNDAY);
        event.setId(20);
        event.setEventName("Altar Of Avarice Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Vorgaltem Citadel Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 20, 21, Const.DAY_MONDAY);
        addDayAndTime(times, 20, 21, Const.DAY_WEDNESDAY);
        addDayAndTime(times, 11, 12, Const.DAY_SATURDAY);
        event.setId(21);
        event.setEventName("Vorgaltem Citadel Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Crimson Temple Fort Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 20, 21, Const.DAY_TUESDAY);
        addDayAndTime(times, 11, 12, Const.DAY_SUNDAY);
        event.setId(22);
        event.setEventName("Crimson Temple Fort Siege");
        event.setTime(times);
        events.add(event);

        /**Asmo Vortex**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 16, 18, Const.DAY_SUNDAY);
        event.setId(23);
        event.setEventName("Asmo Vortex");
        event.setTime(times);
        events.add(event);

        /**Elyos Vortex**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 16, 18, Const.DAY_SATURDAY);
        event.setId(24);
        event.setEventName("Elyos Vortex");
        event.setTime(times);
        events.add(event);

        /**Iron Wall Warfront**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 13, 15, Const.DAY_SATURDAY);
        addDayAndTime(times, 23, 1, Const.DAY_SATURDAY);
        addDayAndTime(times, 13, 15, Const.DAY_SUNDAY);
        addDayAndTime(times, 23, 1, Const.DAY_SUNDAY);
        event.setId(25);
        event.setEventName("Iron Wall Warfront");
        event.setTime(times);
        events.add(event);

        /**Panasterra Forts Siege**/
        event = new PvPEvent();
        times = event.getTime();
        addDayAndTime(times, 19, 20, Const.DAY_SUNDAY);
        event.setId(26);
        event.setEventName("Panasterra Forts Siege");
        event.setTime(times);
        events.add(event);

        /**
         * Now need to add events to database
         */
        RealmHelper realmHelper = new RealmHelper();
        for (PvPEvent e : events) {
            realmHelper.addEventToDatabase(realm, e);
        }


    }

    public void addDayAndTime(RealmList<EventsTime> times, int beginTime, int endTime, String day) {
        EventsTime time = new EventsTime();
        time.setBeginTime(beginTime);
        time.setEndTime(endTime);
        time.setDay(day);
        times.add(time);
    }
}
