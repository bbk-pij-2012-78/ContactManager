/**
 * User: Nick
 * Date: 27/01/13
 * Time: 12:12
 */

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    public PastMeetingImpl(int id, Calendar meetingDate, Set<Contact> contacts, String notes) {
        super(id, meetingDate, contacts);
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    //add notes to the meeting
    public void addNotes(String note) {
        //if there is already some notes stored then add a line break
        //and then append the new note
        if (this.notes.length() > 0) {
            this.notes = this.notes + "\n" + note;
        } else {
            //otherwise just set the note
            this.notes = note;
        }
    }
}
