package ua.ck.allteran.pocketaion.databases;

import java.util.ArrayList;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.entites.PvPEvent;

/**
 * Created by Allteran on 5/31/2015.
 * <p/>
 * To describe time I use array, where i - day of week, j - time.
 * Time is measuring in numbers from 0 to 23. If you see number 25 in time variable
 * it means, that there is no such event in that day
 */
public class CreateDatabaseHelper {

    public void createEventDatabase(Realm realm) {
        ArrayList<PvPEvent> events = new ArrayList<>();
        PvPEvent event;
        int[][] beginTime, endTime;
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        /**Daily reset**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{9}, {9}, {9}, {9}, {9}, {9}, {9}};
        endTime = new int[][]
                {{9}, {9}, {9}, {9}, {9}, {9}, {9}};
        event.setId(1);
        event.setEventName("Daily reset");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Arena**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{10, 18}, {10, 18}, {10, 18}, {10, 18}, {10, 18}, {10, 22}, {10, 22}};
        endTime = new int[][]
                {{14, 0}, {14, 0}, {14, 0}, {14, 0}, {14, 0}, {20, 2}, {20, 2}};
        event.setId(2);
        event.setEventName("Arena");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Dredgion**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{12, 20, 0}, {12, 20, 0}, {12, 20, 0}, {12, 20, 0}, {12, 20, 0}, {12, 20, 0}, {12, 20, 0}};
        endTime = new int[][]
                {{14, 22, 2}, {14, 22, 2}, {14, 22, 2}, {14, 22, 2}, {14, 22, 2}, {14, 22, 2}, {14, 22, 2}};
        event.setId(3);
        event.setEventName("Dredgion");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Ophidan Bridge**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{12, 19, 23}, {12, 19, 23}, {12, 19, 23}, {12, 19, 23}, {12, 19, 23}, {12, 19, 23}, {12, 19, 23}};
        endTime = new int[][]
                {{14, 21, 1}, {14, 21, 1}, {14, 21, 1}, {14, 21, 1}, {14, 21, 1}, {14, 21, 1}, {14, 21, 1}};
        event.setId(4);
        event.setEventName("Engulfed Ophidan Bridge");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Kamar Battlefield**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{21}, {21}, {21}, {21}, {21}, {21}, {21}};
        endTime = new int[][]
                {{23}, {23}, {23}, {23}, {23}, {23}, {23}};
        event.setId(5);
        event.setEventName("Kamar Battlefield");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Tia Forts Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{18}, {18}, {18}, {18}, {18}, {18}, {18}};
        endTime = new int[][]
                {{19}, {19}, {19}, {19}, {19}, {19}, {19}};
        event.setId(6);
        event.setEventName("Tia Forts Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Discipline&Chaos**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{0}, {0}, {0}, {0}, {0}, {25}, {25}};
        endTime = new int[][]
                {{2}, {2}, {2}, {2}, {2}, {25}, {25}};
        event.setId(7);
        event.setEventName("Discipline&Chaos");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Sillus Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{14}, {25}, {25}, {20}, {25}, {14}, {14}};
        endTime = new int[][]
                {{15}, {25}, {25}, {21}, {25}, {15}, {15}};
        event.setId(8);
        event.setEventName("Sillus Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Sulfur Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{16}, {25}, {22}, {25}, {16}, {16}, {22}};
        endTime = new int[][]
                {{17}, {25}, {23}, {25}, {17}, {17}, {23}};
        event.setId(9);
        event.setEventName("Sulfur Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Asteria Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{16}, {25}, {22}, {25}, {16}, {16}, {22}};
        endTime = new int[][]
                {{17}, {25}, {23}, {25}, {17}, {17}, {23}};
        event.setId(10);
        event.setEventName("Asteria Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Roah Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{16}, {25}, {22}, {25}, {16}, {16}, {22}};
        endTime = new int[][]
                {{17}, {25}, {23}, {25}, {17}, {17}, {23}};
        event.setId(11);
        event.setEventName("Roah Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Siel's Forts Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {16}, {25}, {22}, {16}, {16}, {22}};
        endTime = new int[][]
                {{25}, {17}, {25}, {23}, {17}, {17}, {23}};
        event.setId(12);
        event.setEventName("Siel's Forts Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Krotan Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{22}, {25}, {16}, {16}, {22}, {22}, {16}};
        endTime = new int[][]
                {{23}, {25}, {17}, {17}, {23}, {23}, {17}};
        event.setId(13);
        event.setEventName("Krotan Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Kysis Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {22}, {16}, {25}, {22}, {22}, {16}};
        endTime = new int[][]
                {{25}, {23}, {17}, {25}, {23}, {23}, {17}};
        event.setId(14);
        event.setEventName("Kysis Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Miren Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{22}, {22}, {25}, {16}, {22}, {22}, {16}};
        endTime = new int[][]
                {{23}, {23}, {25}, {17}, {23}, {23}, {17}};
        event.setId(15);
        event.setEventName("Miren Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Divine Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {25}, {25}, {25}, {0}, {25}, {20}};
        endTime = new int[][]
                {{25}, {25}, {25}, {25}, {1}, {25}, {21}};
        event.setId(16);
        event.setEventName("Divine Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Silona Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {14}, {25}, {25}, {14}, {25}, {14}};
        endTime = new int[][]
                {{25}, {15}, {25}, {25}, {15}, {25}, {15}};
        event.setId(17);
        event.setEventName("Silona Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Pradeth Forth Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {25}, {14}, {25}, {20}, {25}, {14}};
        endTime = new int[][]
                {{25}, {25}, {15}, {25}, {21}, {25}, {15}};
        event.setId(18);
        event.setEventName("Pradeth Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Temple Of Scales Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{20}, {25}, {20}, {25}, {25}, {11}, {25}};
        endTime = new int[][]
                {{21}, {25}, {21}, {25}, {25}, {12}, {25}};
        event.setId(19);
        event.setEventName("Temple Of Scales Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Altar Of Avarice Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {20}, {25}, {25}, {25}, {25}, {11}};
        endTime = new int[][]
                {{25}, {21}, {25}, {25}, {25}, {25}, {12}};
        event.setId(20);
        event.setEventName("Altar Of Avarice Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Vorgaltem Citadel Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{20}, {25}, {20}, {25}, {25}, {11}, {25}};
        endTime = new int[][]
                {{21}, {25}, {21}, {25}, {25}, {12}, {25}};
        event.setId(21);
        event.setEventName("Vorgaltem Citadel Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Crimson Temple Fort Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {20}, {25}, {25}, {25}, {25}, {11}};
        endTime = new int[][]
                {{25}, {21}, {25}, {25}, {25}, {25}, {12}};
        event.setId(22);
        event.setEventName("Crimson Temple Fort Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Asmo Vortex**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {25}, {16}};
        endTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {25}, {18}};
        event.setId(23);
        event.setEventName("Asmo Vortex");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Elyos Vortex**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {16}, {25}};
        endTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {18}, {25}};
        event.setId(24);
        event.setEventName("Elyos Vortex");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Iron Wall Warfront**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {13, 23}, {13, 23}};
        endTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {15, 1}, {15, 1}};
        event.setId(25);
        event.setEventName("Iron Wall Warfront");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**Panasterra Forts Siege**/
        event = new PvPEvent();
        beginTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {25}, {19}};
        endTime = new int[][]
                {{25}, {25}, {25}, {25}, {25}, {25}, {20}};
        event.setId(26);
        event.setEventName("Panasterra Forts Siege");
        event.setBeginTime(beginTime);
        event.setEndTime(endTime);
        event.setDays(days);
        events.add(event);

        /**
         * Now need to add events to database
         */
        RealmHelper realmHelper = new RealmHelper();
        for (PvPEvent e : events) {
            realmHelper.addEventToDatabase(realm, e);
        }

    }
}
