/**
 * User: Nick
 * Date: 04/02/13
 * Time: 15:29
 */


import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class FutureMeetingTest {

    @Test
    public void testCreateFutureMeeting() throws Exception {
        Set<Contact> contacts = new HashSet<>();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1); //ensure the date is always set in the future
        FutureMeeting fm = new FutureMeetingImpl(1, date, contacts);

        //test the ID so that we know the super constructor is working
        assertEquals(fm.getId(), 1);
    }
}
