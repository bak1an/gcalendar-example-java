package bak1an;


import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;


public class CalendarPrinterTest {
    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testCalendarPrinter() throws IOException {
        CalendarListEntry calendar1 = new CalendarListEntry();
        calendar1.setId("ololo");
        List<CalendarListEntry> calendarListItems =  new LinkedList<CalendarListEntry>();
        calendarListItems.add(calendar1);
        CalendarList calendarList = new CalendarList();
        calendarList.setItems(calendarListItems);

        Events events = new Events();
        List<Event> eventsList = new LinkedList<Event>();
        Event event1 = new Event();
        event1.setSummary("i'm event1");
        event1.setStart(new EventDateTime().setDateTime(new DateTime(System.currentTimeMillis())));
        event1.setEnd(new EventDateTime().setDateTime(new DateTime(System.currentTimeMillis() + 10000)));
        eventsList.add(event1);
        events.setItems(eventsList);

        Calendar calendar = mock(Calendar.class, RETURNS_DEEP_STUBS);
        Calendar.CalendarList.List mockCalendarListRequest = mock(Calendar.CalendarList.List.class);
        Calendar.Events.List mockEventListRequest = mock(Calendar.Events.List.class);

        when(mockCalendarListRequest.execute()).thenReturn(calendarList);
        when(mockEventListRequest.execute()).thenReturn(events);
        when(calendar.calendarList().list()).thenReturn(mockCalendarListRequest);
        when(calendar.events().list(anyString())).thenReturn(mockEventListRequest);

        CalendarPrinter printer = new CalendarPrinter(calendar);

        printer.printEvents();

        assertNotEquals(log.getLog().indexOf("Calendar: ololo"), -1);
        assertNotEquals(log.getLog().indexOf("title: i'm event1"), -1);
    }
}
