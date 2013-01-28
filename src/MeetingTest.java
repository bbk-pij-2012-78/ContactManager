/**
 * User: Nick
 * Date: 27/01/13
 * Time: 17:20
 */

import org.junit.*;
import java.util.Calendar;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.TreeSet;

public class MeetingTest {

    private MeetingImpl meeting;

    @Before
    public void setUp() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(2012, 1, 1);

        Set<Contact> contacts = new TreeSet<>();
        contacts.add(new ContactImpl(1, "John", "notes 1"));
        contacts.add(new ContactImpl(2, "Nick", "notes 2"));
        contacts.add((new ContactImpl(3, "Keith", "notes 3")));

        meeting = new MeetingImpl(1, date, contacts);
    }

    @After
    public void tearDown() throws Exception {
        meeting = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(1, meeting.getId());
    }

    @Test
    public void testGetDate() throws Exception {
        Calendar checkDate = Calendar.getInstance();
        checkDate.set(2012, 1, 1);
        assertEquals(checkDate.toString(), meeting.getDate().toString());
    }

    @Test
    public void testGetContacts() throws Exception {
        assertEquals(3, meeting.getContacts().size());
    }
}
