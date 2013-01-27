/**
 * User: Nick
 * Date: 27/01/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */

import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager {

    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {

    }

    public PastMeeting getPastMeeting(int id) {

    }

    public FutureMeeting getFutureMeeting(int id) {

    }

    public Meeting getMeeting(int id) {

    }

    public List<Meeting> getFutureMeetingList(Contact contact) {

    }

    public List<Meeting> getFutureMeetingList(Calendar date) {

    }

    public List<PastMeeting> getPastMeetingList(Contact contact) {

    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

    }

    public void addMeetingNotes(int id, String text) {

    }

    public void addNewContact(String name, String notes) {

    }

    public Set<Contact> getContacts(int... ids) {

    }

    public Set<Contact> getContacts(String name) {

    }

    public void flush() {

    }
}
