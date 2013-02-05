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

    } */

    @Test
    public void testGetFutureMeeting() throws Exception {
        Calendar fc = Calendar.getInstance();

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(1, "John Smith", "some notes" ));
        contacts.add(new ContactImpl(2, "Peter Smith", "some other notes" ));
        contacts.add(new ContactImpl(3, "Alan Smith", "notes" ));

        //add two new future meetings
        fc.add(Calendar.DATE, 1);  //ensure the date is in the future
        int m1 = contactManager.addFutureMeeting(contacts, fc);
        int m2 = contactManager.addFutureMeeting(contacts, fc);

        //execute the getMeeting procedure and compare the IDs
        assertEquals(m1, contactManager.getFutureMeeting(m1).getId());
        assertEquals(m2, contactManager.getFutureMeeting(m2).getId());

        //test that a null object is sent back for a meeting that doesn't exist
        assertEquals(null, contactManager.getFutureMeeting(99));
    }

    @Test
    public void testGetMeeting() throws Exception {
        Calendar fc = Calendar.getInstance();

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(1, "John Smith", "some notes" ));
        contacts.add(new ContactImpl(2, "Peter Smith", "some other notes" ));
        contacts.add(new ContactImpl(3, "Alan Smith", "notes" ));

        //add two new future meetings
        fc.add(Calendar.DATE, 1);  //ensure the date is in the future
        int m1 = contactManager.addFutureMeeting(contacts, fc);
        int m2 = contactManager.addFutureMeeting(contacts, fc);

        //add two new past meetings
        fc.add(Calendar.DATE, -1);  //ensure the date is in the future
        contactManager.addNewPastMeeting(contacts, fc, "meeting 3 notes");
        contactManager.addNewPastMeeting(contacts, fc, "meeting 4 notes");

        //execute the getMeeting procedure and compare the IDs
        assertEquals(m1, contactManager.getMeeting(m1).getId());
        assertEquals(m2, contactManager.getMeeting(m2).getId());
    }
     /*
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

        Calendar date;
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

    } */

    @Test
    public void testAddNewContact() throws Exception {
        contactManager.addNewContact("John Smith", "some notes");
    }

    @Test
    public void testAddNewContactFail() throws Exception {
        exception.expect(NullPointerException.class);
        contactManager.addNewContact(null, "some notes");

        exception.expect(NullPointerException.class);
        contactManager.addNewContact("John Smith", null);
    }


    @Test
    public void testGetContactsByName() throws Exception {
        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");
        contactManager.addNewContact("Alan Smith", "notes");

        //execute getContacts for name Peter and check that one contact is returned
        Set<Contact> contacts = contactManager.getContacts("Peter");
        assertEquals(contacts.size(), 1);

        //execute getContacts for name Smith and check that three contacts are returned
        contacts = contactManager.getContacts("Smith");
        assertEquals(contacts.size(), 3);

        //execute getContacts for name Paul and check that no contacts are returned
        contacts = contactManager.getContacts("Paul");
        assertEquals(contacts.size(), 0);
    }

    @Test
    public void testGetContactsByNameFail() throws Exception {
        //test the Exception when a null string is passed in
        String name = null;

        exception.expect(NullPointerException.class);
        contactManager.getContacts(name);
    }

    @Test
    public void testGetContactsByIDs() throws Exception {

        //add 3 contacts, the id's will be 0,1,2
        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");
        contactManager.addNewContact("Alan Smith", "notes");

        //execute getContacts for IDs 0,2 and check that two contacts are returned
        Set<Contact> contacts = contactManager.getContacts(0,2);
        assertEquals(contacts.size(), 2);
    }

    /*
    @Test
    public void testFlush() throws Exception {

    } */
}
