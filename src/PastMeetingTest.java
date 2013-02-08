/**
 * User: Nick
 * Date: 04/02/13
 * Time: 14:28
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class PastMeetingTest {

    @Test
    public void testGetNotes() throws Exception {
        Set<Contact> contacts = new HashSet<>();
        Calendar date = Calendar.getInstance();
        String notes = "Some notes";
        PastMeeting pm = new PastMeetingImpl(1, date, contacts, notes);

        //test the ID so that we know the super constructor is working
        assertEquals(pm.getId(), 1);
        //now test that get notes returns the correct string
        assertEquals(pm.getNotes(), notes);
    }

    @Test
    public void testAddNotes() throws Exception {
        Set<Contact> contacts = new HashSet<>();
        Calendar date = Calendar.getInstance();
        String notes = "Some notes";
        PastMeetingImpl pm = new PastMeetingImpl(1, date, contacts, notes);

        pm.addNotes("more notes");
        assertEquals(pm.getNotes(), notes + "\n" + "more notes");
    }
}
