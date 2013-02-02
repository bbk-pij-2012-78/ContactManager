/**
 * User: Nick
 * Date: 27/01/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */

import java.util.*;

public class ContactManagerImpl implements ContactManager {

    private List<Meeting> meetings;
    private Set<Contact> contacts;
    private int nextMeetingId;

    //the default contructor, ensures that the collections are set on initialisation
    public ContactManagerImpl() {

        //collection for meetings, uses ArrayList as the size will grow automatically
        meetings = new ArrayList<>();
        contacts = new HashSet<>();
        nextMeetingId = 1;
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        Calendar c = Calendar.getInstance();

        //check that the date is in the future and that there is at least one contact
        if (date.compareTo(c) > 0) {throw new IllegalArgumentException("Date Cannot Be In The Past");}
        if (contacts.size() == 0) {throw new IllegalArgumentException("Meeting Must Contain At Least One Contact");}

        //create a future meeting and add it to the collection
        FutureMeetingImpl fm = new FutureMeetingImpl(nextMeetingId, date, contacts);
        this.meetings.add(nextMeetingId, fm);

        //increment the next meeting ID
        this.nextMeetingId ++;

        //return the meeting ID
        return fm.getId();
    }

    public PastMeeting getPastMeeting(int id) {
        PastMeetingImpl p = null;
        return p;
    }

    public FutureMeeting getFutureMeeting(int id) {
        FutureMeetingImpl f = null;
        return f;
    }

    public Meeting getMeeting(int id) {
        Meeting m = null;
        return m;
    }

    public List<Meeting> getFutureMeetingList(Contact contact) {
        List<Meeting> l = null;
        return l;
    }

    public List<Meeting> getFutureMeetingList(Calendar date) {
        List<Meeting> l = null;
        return l;
    }

    public List<PastMeeting> getPastMeetingList(Contact contact) {
        List<PastMeeting> l = null;
        return l;
    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

    }

    public void addMeetingNotes(int id, String text) {

    }

    public void addNewContact(String name, String notes) {

    }

    public Set<Contact> getContacts(int... ids) {
        Set<Contact> s = null;
        return s;
    }

    public Set<Contact> getContacts(String name) {
        Set<Contact> s = null;
        return s;
    }

    public void flush() {

    }
}
