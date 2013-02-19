/**
 * User: Nick
 * Date: 27/01/13
 * Time: 12:03
 */


public class ContactImpl implements Contact {

    private int id;
    private String name;
    private String notes;

    public ContactImpl(int id, String name, String notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getNotes() {
        return this.notes;
    }

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
