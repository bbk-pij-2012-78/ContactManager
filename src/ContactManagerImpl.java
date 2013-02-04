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

    //the default constructor, ensures that the collections are set on initialisation
    public ContactManagerImpl() {

        //collection for meetings, uses ArrayList as the size will grow automatically
        meetings = new ArrayList<>();
        contacts = new HashSet<>();
        nextMeetingId = 0;
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        //check that the date is in the future and that there is at least one contact
        if (date.before(Calendar.getInstance())) {throw new IllegalArgumentException("Date Cannot Be In The Past");}

        //create a future meeting and add it to the collection
        Meeting fm = new FutureMeetingImpl(nextMeetingId, date, contacts);
        this.meetings.add(nextMeetingId, fm);

        //increment the next meeting ID
        this.nextMeetingId ++;

        //return the meeting ID
        return fm.getId();
    }

    public PastMeeting getPastMeeting(int id) {
        MeetingImpl m = null;

        //loop over all the meetings in the collection
        for (Iterator itr = meetings.iterator(); itr.hasNext(); ) {
            m = (MeetingImpl) itr.next();

            //if the ID's match check that the date is not in the future
            if (m.getId() == id) {
                if (Calendar.getInstance().compareTo(m.getDate()) > 0) {
                    throw new IllegalArgumentException("Meeting ID Specified Is A Future Meeting");
                } else {
                    //meeting is in the past so break out of the loop
                    break;
                }
            }
        }
        //return the meeting casting to a PastMeetingImpl
        return (PastMeetingImpl) m;
    }

    public FutureMeeting getFutureMeeting(int id) {
        MeetingImpl m = null;

        //loop over all the meetings in the collection
        for (Iterator itr = meetings.iterator(); itr.hasNext(); ) {
            m = (MeetingImpl) itr.next();

            //if the ID's match check that the date is not in the past
            if (m.getId() == id) {
                if (Calendar.getInstance().compareTo(m.getDate()) < 0) {
                    throw new IllegalArgumentException("Meeting ID Specified Is A Past Meeting");
                } else {
                    //meeting is in the future so break out of the loop
                    break;
                }
            }
        }
        //return the meeting casting to FutureMeetingImpl
        return (FutureMeetingImpl) m;
    }

    public Meeting getMeeting(int id) {
        //TODO - add code to get a meeting by the ID
        Meeting m = null;
        return m;
    }

    public List<Meeting> getFutureMeetingList(Contact contact) {
        //TODO - add code to get list of all future meetings by contact
        List<Meeting> l = null;
        return l;
    }

    public List<Meeting> getFutureMeetingList(Calendar date) {
        //TODO - add code to get list of all future meetings by date
        List<Meeting> l = null;
        return l;
    }

    public List<PastMeeting> getPastMeetingList(Contact contact) {
        //TODO - add code to get list of all past meetings by contact
        List<PastMeeting> l = null;
        return l;
    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        //check that there is at least one contact
        if (contacts.size() == 0) {throw new IllegalArgumentException("Contacts List Must Contain At Least One Contact");}

        //TODO - Add a check for all contacts

        if (contacts == null) {throw new NullPointerException("Contacts List Cannot Be NULL");}
        if (date == null) {throw new NullPointerException("Meeting Date Cannot Be NULL");}
        if (text == null) {throw new NullPointerException("Meeting Notes Cannot Be NULL");}

        Meeting pm = new PastMeetingImpl(nextMeetingId, date, contacts, text);
        this.meetings.add(nextMeetingId, pm);

        //increment the next meeting ID
        this.nextMeetingId ++;
    }

    public void addMeetingNotes(int id, String text) {
        //TODO - add meeting notes
    }

    public void addNewContact(String name, String notes) {
        //TODO - add new contact
    }

    public Set<Contact> getContacts(int... ids) {
        //TODO - get contacts by a list of IDs
        Set<Contact> s = null;
        return s;
    }

    public Set<Contact> getContacts(String name) {
        //TODO - get contacts that match a name
        Set<Contact> s = null;
        return s;
    }

    public void flush() {
        //TODO - write out the contents of the object to a file
    }
}
