/**
 * User: Nick
 * Date: 27/01/13
 * Time: 12:16
 */

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

    public FutureMeetingImpl(int id, Calendar meetingDate, Set<Contact> contacts) {
        super(id, meetingDate, contacts);
    }

}
