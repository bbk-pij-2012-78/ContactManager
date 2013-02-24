/**
 * User: Nick
 * Date: 27/01/13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;

public class ContactManagerImpl implements ContactManager {
    private static final String FILENAME = "contacts.txt";

    private enum MeetingType {
        PAST, FUTURE
    }

    private List<Meeting> meetings;
    private Set<Contact> contacts;
    private int nextMeetingId;
    private int nextContactId;

    //the default constructor, ensures that the collections are set on initialisation
    public ContactManagerImpl() {

        //collection for meetings, uses ArrayList as the size will grow automatically
        meetings = new ArrayList<>();
        contacts = new HashSet<>();
        nextMeetingId = 0;
        nextContactId = 0;
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
                if (Calendar.getInstance().before(m.getDate())) {
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

        //get the meeting from the ID
        Meeting m = this.getMeeting(id);

        //if the ID doesn't match any meeting skip and return null
        if (m != null) {
            //otherwise check the date is not in the past
            if (m.getDate().before(Calendar.getInstance())) {
                throw new IllegalArgumentException("Meeting ID Specified Is A Past Meeting");
            }
        }

        //return the object casting to FutureMeetingImpl
        return (FutureMeetingImpl) m;
    }

    public Meeting getMeeting(int id) {
        Meeting m = null;

        //loop over all the meetings in the collection
        for (Iterator itr = meetings.iterator(); itr.hasNext(); ) {
            m = (MeetingImpl) itr.next();

            //if the ID's match check that the date is not in the past
            if (m.getId() == id) {
                break;
            } else {
                //set to null so that the wrong meeting is not returned
                m = null;
            }
        }

        //return the meeting object
        return m;
    }

    public List<Meeting> getFutureMeetingList(Contact contact) {
        return this.searchMeetings(contact, MeetingType.FUTURE);
    }

    public List<Meeting> getFutureMeetingList(Calendar date) {
        //Implement a list of MeetingImpl so we can use the Comparable to sort
        List<MeetingImpl> futMeetings = new ArrayList<>();
        MeetingImpl m;

        for (Iterator itr = meetings.iterator(); itr.hasNext(); ) {
            m = (MeetingImpl) itr.next();

            //if the meeting date matches the date parameter and the meeting
            // is in the future add to return list
            if (m.getDate().compareTo(date) == 0) {
                if (m.getDate().after(Calendar.getInstance())) {
                    futMeetings.add(m);
                }
            }
        }

        //sort the list
        Collections.sort(futMeetings);

        return (List) futMeetings;
    }

    public List<PastMeeting> getPastMeetingList(Contact contact) {
        if (!contactExists(contact.getId())) {throw new IllegalArgumentException("Contact Does Not Exists");}

        return (List) this.searchMeetings(contact, MeetingType.PAST);
    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        //check that there is at least one contact
        if (contacts.size() == 0) {throw new IllegalArgumentException("Contacts List Must Contain At Least One Contact");}
        if (contacts == null) {throw new IllegalArgumentException("Contacts List Cannot Be NULL");}
        if (!this.allContactsExist(contacts)) {throw new IllegalArgumentException("One Or More Contacts Do Not Exists");}
        if (date == null) {throw new NullPointerException("Meeting Date Cannot Be NULL");}
        if (text == null) {throw new NullPointerException("Meeting Notes Cannot Be NULL");}

        Meeting pm = new PastMeetingImpl(nextMeetingId, date, contacts, text);
        this.meetings.add(nextMeetingId, pm);


        //increment the next meeting ID
        this.nextMeetingId ++;
    }

    public void addMeetingNotes(int id, String text) {
        Meeting m = this.getMeeting(id);
        if (m == null) {throw new IllegalArgumentException("Meeting ID Does Not Exists");}
        if (m.getDate().after(Calendar.getInstance())) {throw new  IllegalArgumentException("Meeting Date Is In The Future");}
        if (text == null) {throw new NullPointerException("Notes Text Cannot Be Null");}

        //cast the meeting to a past meeting
        PastMeetingImpl pm = (PastMeetingImpl) m;
        //add the notes
        pm.addNotes(text);
    }

    public void addNewContact(String name, String notes) {
        if (name == null) {throw new NullPointerException("Contact Name Cannot Be NULL");}
        if (notes == null) {throw new NullPointerException("Notes Cannot Be NULL");}

        //checks have been passed so add a new contact to the contacts collection
        this.contacts.add(new ContactImpl(nextContactId, name, notes ));

        //increment the Contact ID counter
        nextContactId ++;
    }

    public Set<Contact> getContacts(int... ids) {
        Set<Contact> s = new HashSet<>();
        ContactImpl c;

        //loop around the IDs that have been passed through
        for (int id : ids) {

            if (!contactExists(id)) {throw new IllegalArgumentException("ID " + id + " Does Not Exist");}

            //loop over the set of contacts to find matching IDs
            for (Iterator itr = contacts.iterator(); itr.hasNext(); ) {
                c = (ContactImpl) itr.next();

                //if the ID's match add the object to the list to be returned
                if (c.getId() == id) {
                    s.add(c);
                }
            }
        }
        //return the set
        return s;
    }

    public Set<Contact> getContacts(String name) {
        Set<Contact> s = new HashSet<>();
        ContactImpl c;

        //check that a null String has not been passed through
        if (name == null) {throw new NullPointerException("Name Cannot Be NULL");}

        //loop over the set of contacts to find matching names
        for (Iterator itr = contacts.iterator(); itr.hasNext(); ) {
            c = (ContactImpl) itr.next();

            //if the contact name contains the name parameter add the object to the list to be returned
            if (c.getName().contains(name)) {
                s.add(c);
            }
        }

        //return the set
        return s;
    }

    private boolean allContactsExist(Set<Contact> checkContacts) {
        ContactImpl c;

        //loop over the set of contacts to find matching names
        for (Iterator itr = checkContacts.iterator(); itr.hasNext(); ) {
            c = (ContactImpl) itr.next();

            //if the contact name contains the name parameter add the object to the list to be returned
            if (!this.contactExists(c.getId())) {
                return false;
            }
        }
        //if we reach here all the IDs have matched so return true
        return true;
    }

    private boolean contactExists(int id) {
        //loop over all contacts and check that the id exists
        ContactImpl c;

        for (Iterator itr = contacts.iterator(); itr.hasNext(); ) {
            c = (ContactImpl) itr.next();

            //if the contact ID matches the id  parameter return true
            if (c.getId() == id) {
                return true;
            }
        }
        //if the code gets this far the ID doesn't exist so return false
        return false;
    }

    private void load() {

    }

    public void flush() {
        //write out the contents of the two collections to a file
        try (ObjectOutputStream output = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(FILENAME)));) {
            output.writeObject(this.contacts);
            output.writeObject(this.meetings);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //searches for past or future meetings that match a contact
    //enum is used so code is not duplicated
    private List<Meeting> searchMeetings(Contact contact, MeetingType mt) {

        List<MeetingImpl> retMeetings = new ArrayList<>();
        MeetingImpl m;
        ContactImpl c;

        //loop over all meetings
        for (Iterator itrM = meetings.iterator(); itrM.hasNext(); ) {
            m = (MeetingImpl) itrM.next();

            //loop over the set of meeting contacts to see if the IDs match
            for (Iterator itrC = m.getContacts().iterator(); itrC.hasNext(); ) {
                c = (ContactImpl) itrC.next();

                if (c.getId() == contact.getId()) {

                    //check the date based on if we are looking for past or future meetings
                    switch (mt) {
                        case PAST:
                            if (m.getDate().before(Calendar.getInstance())) {
                                retMeetings.add(m);
                            }
                            break;

                        case FUTURE:
                            if (!m.getDate().before(Calendar.getInstance())) {
                                retMeetings.add(m);
                            }
                            break;
                    }
                }
            }
        }

        //sort the meetings list
        Collections.sort(retMeetings);

        //return the meeting list
        return (List) retMeetings;
    }
}