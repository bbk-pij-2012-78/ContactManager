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

    public int getId() {
        return this.id;
    }

    public Calendar getDate() {
        return this.meetingDate;
    }

    public Set<Contact> getContacts() {
        return this.contacts;
    }

}
