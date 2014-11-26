package bak1an;


import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.util.List;

public class CalendarPrinter {
    private Calendar client;

    public CalendarPrinter(Calendar client) {
        this.client = client;
    }

    public void printEvents() throws IOException {
        List<CalendarListEntry> calendars = client.calendarList().list().execute().getItems();
        for (CalendarListEntry c : calendars) {
            System.out.printf("Calendar: %s\n", c.getId());
            List<Event> events = client.events().list(c.getId()).execute().getItems();
            System.out.printf("There are %d events\n", events.size());
            for (Event e : events) {
                System.out.printf("start: %s | end: %s | title: %s\n",
                        e.getStart().getDateTime().toString(), e.getEnd().getDateTime().toString(), e.getSummary());
            }
            System.out.println("----------------------");
        }
    }
}
