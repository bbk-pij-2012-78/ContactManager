/**
 * User: Nick
 * Date: 27/01/13
 * Time: 17:20
 */

import org.junit.*;
import java.util.Calendar;
import static org.junit.Assert.*;
import java.util.Set;

public class MeetingTest {

    private MeetingImpl meeting;

    @Before
    public void setUp() throws Exception {
        Calendar date = Calendar.getInstance();
        date.set(2012, 1, 1);
        Set<Contact> contacts = new Set;
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
        fail("Not implemented yet.");
    }

    @Test
    public void testGetContacts() throws Exception {
        fail("Not implemented yet.");
    }
}
