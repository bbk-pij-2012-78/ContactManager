import java.util.Calendar;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Date: 27/01/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    public PastMeetingImpl(int id, Calendar meetingDate, Set<Contact> contacts) {
        super(id, meetingDate, contacts);
    }

    public String getNotes() {
        return this.notes;
    }
}
