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

    //the default contructor, ensures that the collections are set on initialisation
    public ContactManagerImpl() {

        //collection for meetings, uses ArrayList as the size will grow automatically
        meetings = new ArrayList<>();
        contacts = new HashSet<>();

    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        return 0;
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
