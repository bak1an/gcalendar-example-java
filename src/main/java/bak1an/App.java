package bak1an;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


public class App {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId("861849038444-2dm3nonjb60sp57lv2l3ipc513vs395h@developer.gserviceaccount.com")
                .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR_READONLY))
                .setServiceAccountPrivateKeyFromP12File(new File("client_key.p12"))
                .build();

        Calendar client = new Calendar.Builder(httpTransport, jsonFactory, credential).setApplicationName("sandbox").build();

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
