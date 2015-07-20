package ua.ck.allteran.pocketaion.entites;

import java.util.List;

/**
 * Created by Allteran on 7/7/2015.
 */
public class PvPEventsLoaderResult {
    private List<PvPEvent> mAllEvents;
    private String mTimeFromNetwork;

    public List<PvPEvent> getAllEvents() {
        return mAllEvents;
    }

    public void setAllEvents(List<PvPEvent> allEvents) {
        this.mAllEvents = allEvents;
    }

    public String getTimeFromNetwork() {
        return mTimeFromNetwork;
    }

    public void setTimeFromNetwork(String timeFromNetwork) {
        this.mTimeFromNetwork = timeFromNetwork;
    }
}
