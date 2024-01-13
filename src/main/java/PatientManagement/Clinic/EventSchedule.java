package PatientManagement.Clinic;

import java.util.ArrayList;


/**
 *
 * @author kal bugrara
 */

public class EventSchedule {

    ArrayList<Event> scheduledevents;

    public EventSchedule() {
        scheduledevents = new ArrayList<Event>();
    }

    public Event newEvent(Site s, String budgetnumer,String name) {

        Event newevent = new Event(s, budgetnumer, name);
        scheduledevents.add(newevent);
        return newevent;
    }

    // finds all positive confirmations for all events that match the budgetnumber
    public int getEventConfirmedCasesByBudgetNumber(String budgetnumber) {
        int sum = 0;
        for (Event e : scheduledevents) {

            if (e.isMatch(budgetnumber))

                sum = sum + e.getConfirmedTotals();

        }
        return sum;

    }

   
}