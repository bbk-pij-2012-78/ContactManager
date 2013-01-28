/**
 * User: Nick
 * Date: 27/01/13
 * Time: 12:08
 */

import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {

    private int id;
    private Calendar meetingDate;
    private Set<Contact> contacts;

    public MeetingImpl(int id, Calendar meetingDate, Set<Contact> contacts) {
        this.id = id;
        this.meetingDate = meetingDate;
        this.contacts = contacts;
    }

    //return the id of the meeting
    public int getId() {
        return this.id;
    }

    //return the date of the meeting
    public Calendar getDate() {
        return this.meetingDate;
    }

    //return the set of contacts
    public Set<Contact> getContacts() {
        return this.contacts;
    }

}
