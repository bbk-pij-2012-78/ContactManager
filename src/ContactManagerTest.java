/**
 * User: Nick
 * Date: 02/02/13
 * Time: 20:15
 */

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ContactManagerTest {

    private static final String FILENAME = "contacts.txt";

    private ContactManager contactManager;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        deleteContactsFile();
        contactManager = new ContactManagerImpl();
    }

    @After
    public void tearDown() throws Exception {
        contactManager = null;
        deleteContactsFile();
    }

    private void deleteContactsFile() {
        if (new File(FILENAME).exists()) {
            new File(FILENAME).delete();
        }
    }


    @Test
    public void testAddFutureMeeting() throws Exception {
        System.out.println("Running Test: testAddFutureMeeting");

        contactManager.addNewContact("John Smith", "some notes");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 14);  //set the date to always be 14 days in the future

        assertEquals(0, contactManager.addFutureMeeting(contacts, date));
        assertEquals(1, contactManager.addFutureMeeting(contacts, date));
    }

    @Test
    public void testAddFutureMeetingFailDate() {
        System.out.println("Running Test: testAddFutureMeetingFailDate");

        contactManager.addNewContact("John Smith", "some notes");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);  //set the date to always be a day in the past

        exception.expect(IllegalArgumentException.class);
        contactManager.addFutureMeeting(contacts, date);
    }

    @Test
    public void testAddFutureMeetingFailContact() {
        System.out.println("Running Test: testAddFutureMeetingFailContact");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);  //set the date to always be a day in the future

        exception.expect(IllegalArgumentException.class);
        contactManager.addFutureMeeting(contacts, date);
    }

    @Test
    public void testGetPastMeeting() throws Exception {
        //add a past meeting and then check the notes match
        System.out.println("Running Test: testGetPastMeeting");

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);  //set the date to be in the past

        //add the contacts to contact manager or the test will fail
        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes" ));
        contacts.add(new ContactImpl(1, "Peter Smith", "some other notes" ));

        contactManager.addNewPastMeeting(contacts, date, "notes");
        assertEquals(contactManager.getPastMeeting(0).getNotes(), "notes");
    }

    @Test
    public void testGetPastMeetingFail() throws Exception {
        //add a future meeting and then pass that ID to getPastMeeting and expect an error
        System.out.println("Running Test: testGetPastMeetingFail");

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 2);  //set the date to be in the future

        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes" ));
        contacts.add(new ContactImpl(1, "Peter Smith", "some other notes" ));

        int id = contactManager.addFutureMeeting(contacts, date);
        exception.expect(IllegalArgumentException.class);
        contactManager.getPastMeeting(id);
    }

    @Test
    public void testGetFutureMeeting() throws Exception {
        System.out.println("Running Test: testGetFutureMeeting");

        Calendar fc = Calendar.getInstance();

        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");
        contactManager.addNewContact("Alan Smith", "notes");

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes" ));
        contacts.add(new ContactImpl(1, "Peter Smith", "some other notes" ));
        contacts.add(new ContactImpl(2, "Alan Smith", "notes" ));

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
        System.out.println("Running Test: testGetMeeting");

        Calendar fc = Calendar.getInstance();

        //add contacts to contact manager or the test will fail
        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");
        contactManager.addNewContact("Alan Smith", "notes");

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes" ));
        contacts.add(new ContactImpl(1, "Peter Smith", "some other notes" ));
        contacts.add(new ContactImpl(2, "Alan Smith", "notes" ));

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


    @Test
    public void testGetFutureMeetingListContact() throws Exception {
        System.out.println("Running Test: testGetFutureMeetingListContact");

        //add contacts to contact manager or the test will fail
        contactManager.addNewContact("John Smith", "some notes");
        Contact c = new ContactImpl(0, "John Smith", "some notes");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));

        //add a future meeting and a past meeting and check that only 1 is returned in the list
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);
        contactManager.addNewPastMeeting(contacts, date, "past meeting");

        date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);
        contactManager.addFutureMeeting(contacts, date);

        //check we only get one meeting back for the contact
        List<Meeting> list = contactManager.getFutureMeetingList(c);
        assertEquals(list.size(), 1);
    }

    @Test
    public void testGetFutureMeetingListContactFail() throws Exception {
        System.out.println("Running Test: testGetFutureMeetingListContactFail");

        Contact c = new ContactImpl(99, "Peter Jones", "notes");

        exception.expect(IllegalArgumentException.class);
        List<Meeting> list = contactManager.getFutureMeetingList(c);
    }

    @Test
    public void testGetFutureMeetingListDate() throws Exception {
        System.out.println("Running Test: testGetFutureMeetingListDate");

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        Calendar date3 = Calendar.getInstance();
        Calendar date4 = Calendar.getInstance();

        date1.add(Calendar.DATE, 2);
        date2.add(Calendar.DATE, 2);
        date3.add(Calendar.DATE, 3);

        //add contacts to contact manager or the test will fail
        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes" ));
        contacts.add(new ContactImpl(1, "Peter Smith", "some other notes" ));

        date1.add(Calendar.HOUR, 8);
        int id1 = contactManager.addFutureMeeting(contacts, date1);
        date2.add(Calendar.HOUR, 5);
        int id2 = contactManager.addFutureMeeting(contacts, date2);
        int id3 = contactManager.addFutureMeeting(contacts, date3);

        //check that we get two meetings returned for date1
        List<Meeting> list = contactManager.getFutureMeetingList(date1);
        assertEquals(list.size(), 2);

        //now check we get one meeting back for date2
        list = contactManager.getFutureMeetingList(date3);
        assertEquals(1, list.size());
        assertEquals(id3, list.get(0).getId());

        //use a date that is the same date as date1 but a different time
        // to check the comparison works with different times
        date4.add(Calendar.DATE, 2);
        list = contactManager.getFutureMeetingList(date4);
        assertEquals(2, list.size());

        //check that the list is sorted chronologically
        assertEquals(id2, list.get(0).getId());
        assertEquals(id1, list.get(1).getId());
    }

    @Test
    public void testGetPastMeetingList() throws Exception {
        System.out.println("Running Test: testGetPastMeetingList");

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        Calendar date3 = Calendar.getInstance();

        date1.add(Calendar.DATE, -2);
        date2.add(Calendar.DATE, -3);
        date3.add(Calendar.DATE, 4);
        //add contacts to contact manager or the test will fail
        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");

        //create a list of contacts to add
        Set<Contact> contacts = new HashSet<>();
        Contact c1 = new ContactImpl(0, "John Smith", "some notes" );
        contacts.add(c1);

        contactManager.addNewPastMeeting(contacts, date1, "Past Meeting 1");
        contactManager.addNewPastMeeting(contacts, date1, "Past Meeting 2");
        //add a future meeting to check we only get past meetings
        contactManager.addFutureMeeting(contacts, date3);
        //check that we get two meetings returned for contact 1
        Contact c2 = new ContactImpl(1, "Peter Smith", "some other notes" );
        List<PastMeeting> list = contactManager.getPastMeetingList(c1);
        assertEquals(2, list.size());
        //check no meetings returned for contact 2

        list = contactManager.getPastMeetingList(c2);
        assertEquals(0, list.size());

        //add another contact and check we only get one meeting back
        Set<Contact> contacts2 = new HashSet<>();
        contacts2.add(c1);
        contacts2.add(c2);
        contactManager.addNewPastMeeting(contacts2, date2, "Past Meeting 3");
        list = contactManager.getPastMeetingList(c2);
        assertEquals(1, list.size());

        //TODO add a test to check the list is sorted chronologically
    }

    @Test
    public void testGetPastMeetingListFail() throws Exception {
        System.out.println("Running Test: testGetPastMeetingListFail");

        Contact c = new ContactImpl(99, "Peter Jones", "notes");

        exception.expect(IllegalArgumentException.class);
        List<PastMeeting> list = contactManager.getPastMeetingList(c);
    }

    @Test
    public void testAddNewPastMeeting() throws Exception {
        System.out.println("Running Test: testAddNewPastMeeting");

        //add the contact to contact manager first or the test will fail
        contactManager.addNewContact("John Smith", "some notes");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));

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

    @Test
    public void testContactsExist() throws Exception {
        System.out.println("Running Test: testContactsExist");
        String notes = "";
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);  //set the date to always be a day in the past

        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");
        contactManager.addNewContact("Alan Smith", "notes");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));
        contacts.add(new ContactImpl(99, "Peter Smith", "some other notes"));

        exception.expect(IllegalArgumentException.class);
        contactManager.addNewPastMeeting(contacts, date, notes);
    }


    @Test
    public void testAddMeetingNotes() throws Exception {
        System.out.println("Running Test: testAddMeetingNotes");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));
        contactManager.addNewContact("John Smith", "some notes");

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);
        contactManager.addNewPastMeeting(contacts, date, "meeting notes");

        //now add some additional notes
        contactManager.addMeetingNotes(0, "more notes");
        assertEquals(contactManager.getPastMeeting(0).getNotes(), "meeting notes" + "\n" + "more notes");
    }

    @Test
    public void testAddMeetingNotesFail() throws Exception {
        System.out.println("Running Test: testAddMeetingNotesFail");

        //pass an ID through without a meeting and expect an illegal argument
        exception.expect(IllegalArgumentException.class);
        contactManager.addMeetingNotes(1, "some notes");

        //add a future meeting and then pass that ID back and expect an illegal argument
        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(1, "John Smith", "some notes"));
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);

        int id = contactManager.addFutureMeeting(contacts, date);
        exception.expect(IllegalArgumentException.class);
        contactManager.addMeetingNotes(id, "some notes");

        //now pass in a null string for the notes and expect a null pointer exception
        contactManager = new ContactManagerImpl();  //reset the contacts manager so we know the meeting ID is 0
        date = Calendar.getInstance();
        date.add(Calendar.DATE, -1);
        contactManager.addNewPastMeeting(contacts, date, "meeting notes");

        exception.expect(NullPointerException.class);
        contactManager.addMeetingNotes(0, null);
    }

    @Test
    public void testAddNewContact() throws Exception {
        System.out.println("Running Test: testAddNewContact");
        contactManager.addNewContact("John Smith", "some notes");
    }

    @Test
    public void testAddNewContactFail() throws Exception {
        System.out.println("Running Test: testAddNewContactFail");

        exception.expect(NullPointerException.class);
        contactManager.addNewContact(null, "some notes");

        exception.expect(NullPointerException.class);
        contactManager.addNewContact("John Smith", null);
    }


    @Test
    public void testGetContactsByName() throws Exception {
        System.out.println("Running Test: testGetContactsByName");

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
        System.out.println("Running Test: testGetContactsByNameFail");

        String name = null;

        exception.expect(NullPointerException.class);
        contactManager.getContacts(name);
    }

    @Test
    public void testGetContactsByIDs() throws Exception {
        System.out.println("Running Test: testGetContactsByIDs");

        //add 3 contacts, the id's will be 0,1,2
        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");
        contactManager.addNewContact("Alan Smith", "notes");

        //execute getContacts for IDs 0,2 and check that two contacts are returned
        Set<Contact> contacts = contactManager.getContacts(0,2);
        assertEquals(contacts.size(), 2);
    }


    @Test
    public void testFlush() throws Exception {

        File f = new File(FILENAME);

        //delete the file if it exists so we know a new one was created
        if (f.exists()) {
            f.delete();
        }

        contactManager.addNewContact("John Smith", "some notes");
        contactManager.addNewContact("Peter Smith", "some other notes");

        Set<Contact> contacts = new HashSet<>();
        contacts.add(new ContactImpl(0, "John Smith", "some notes"));
        contacts.add(new ContactImpl(1, "Peter Smith", "some other notes"));

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 1);

        contactManager.addFutureMeeting(contacts, date);

        contactManager.flush();

        assertTrue(f.exists());
    }
}
