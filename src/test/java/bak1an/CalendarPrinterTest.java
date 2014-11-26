package bak1an;


import com.google.api.services.calendar.Calendar;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

import java.io.IOException;

import static org.mockito.Mockito.*;


public class CalendarPrinterTest {
    @Rule
    public final StandardOutputStreamLog log = new StandardOutputStreamLog();

    @Test
    public void testCalendarPrinter() throws IOException {
        Calendar calendar = mock(Calendar.class, RETURNS_DEEP_STUBS);
        when(calendar.calendarList().list().execute().getItems()).thenReturn(null);

        CalendarPrinter printer = new CalendarPrinter(calendar);

        printer.printEvents();
    }
}
