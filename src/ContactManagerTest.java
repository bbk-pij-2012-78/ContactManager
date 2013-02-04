/**
 * User: Nick
 * Date: 02/02/13
 * Time: 20:15
 */

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ContactManagerTest {

    private ContactManager contactManager;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        contactManager = new ContactManagerImpl();
    }

    @After
    public void tearDown() throws Exception {
        contactManager = null;
    }

    @Test
    public void testAddFutureMeeting() throws Exception {
        System.out.println("Running Test: testAddFutureMeeting");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(1, "John Smith", "some notes"));

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 14);  //set the date to always be 14 days in the future

        assertEquals(0, contactManager.addFutureMeeting(contacts, date));
        assertEquals(1, contactManager.addFutureMeeting(contacts, date));
    }

    @Test
    public void testAddFutureMeetingFail() {
        System.out.println("Running Test: testAddFutureMeetingFail");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(1, "John Smith", "some notes"));

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);  //set the date to always be a day in the past

        exception.expect(IllegalArgumentException.class);
        contactManager.addFutureMeeting(contacts, date);
    }

    /*
    @Test
    public void testGetPastMeeting() throws Exception {

    }

    @Test
    public void testGetFutureMeeting() throws Exception {

    }

    @Test
    public void testGetMeeting() throws Exception {

    }

    @Test
    public void testGetFutureMeetingList() throws Exception {

    }

    @Test
    public void testGetFutureMeetingList() throws Exception {

    }

    @Test
    public void testGetPastMeetingList() throws Exception {

    }
    */

    @Test
    public void testAddNewPastMeeting() throws Exception {
        System.out.println("Running Test: testAddNewPastMeeting");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(1, "John Smith", "some notes"));

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);  //set the date to always be a day in the past

        String notes = "Some notes";

        contactManager.addNewPastMeeting(contacts, date, notes);
    }

    @Test
    public void testAddNewPastMeetingFail() throws Exception {
        System.out.println("Running Test: testAddNewPastMeetingFail");

        Set<Contact> contacts;;
        contacts = null;

        Calendar date = Calendar.getInstance();
        date = null;  //set the date to always be a day in the past

        String notes = null;

        // execute the test when all arguments are null
        exception.expect(NullPointerException.class);
        contactManager.addNewPastMeeting(contacts, date, notes);

        //set the notes argument to some text and check the call still fails
        notes = "Some Notes";
        exception.expect(NullPointerException.class);
        contactManager.addNewPastMeeting(contacts, date, notes);

        //set the date argument to a date in the past and check the call still fails
        date.add(Calendar.DATE, -1);  //set the date to always be a day in the past
        exception.expect(NullPointerException.class);
        contactManager.addNewPastMeeting(contacts, date, notes);

        //now add a contacts object that is not null but has no contacts
        //should now get an IllegalArgumentException
        contacts = new HashSet<>();
        exception.expect(IllegalArgumentException.class);
        contactManager.addNewPastMeeting(contacts, date, notes);
    }


    /*
    @Test
    public void testAddMeetingNotes() throws Exception {

    }

    @Test
    public void testAddNewContact() throws Exception {

    }

    @Test
    public void testGetContacts() throws Exception {

    }

    @Test
    public void testGetContacts() throws Exception {

    }

    @Test
    public void testFlush() throws Exception {

    } */
}
