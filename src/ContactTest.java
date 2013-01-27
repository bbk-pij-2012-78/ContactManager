/**
 * User: Nick
 * Date: 27/01/13
 * Time: 14:28
 */

import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest {

    private ContactImpl contact;

    @Before
    public void setUp() throws Exception {
        contact = new ContactImpl(1, "John Smith", "these are some notes");
    }

    @After
    public void tearDown() throws Exception {
        contact = null;
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals(1, contact.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("John Smith", contact.getName());
    }

    @Test
    public void testGetNotes() throws Exception {
        assertEquals("these are some notes", contact.getNotes());
    }

    @Test
    public void testAddNotes() throws Exception {
        contact.addNotes("some more notes");
        assertEquals("these are some notes" + "\n" + "some more notes", contact.getNotes());
    }
}
