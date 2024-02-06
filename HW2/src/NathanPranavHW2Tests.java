import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

/**
 * @author Nathan Duggal / Pranav Tadepalli
 * @version 1.0
 */
public class NathanPranavHW2Tests {

    private static final int TIMEOUT = 200;
    private DoublyLinkedList<String> list;

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test(timeout = TIMEOUT)
    public void testSingleNode() {
        list.addToFront("0");
        assertEquals(new String("0"), list.getHead().getData());
        assertEquals(new String("0"), list.getTail().getData());
        assertSame(list.getHead(), list.getTail());

        list.addToFront("1");
        list.addToFront("2");
        assertEquals(new String("0"), list.getTail().getData());
        assertEquals(3, list.size());
        assertNotNull(list.getHead().getNext());

        list.removeFromBack();
        list.removeFromBack();

        assertEquals(new String("2"), list.getHead().getData());
        assertEquals(new String("2"), list.getTail().getData());
        assertSame(list.getHead(), list.getTail());
        assertNull(list.getHead().getNext());

        assertEquals(new String("2"), list.removeFromBack());
        assertNull(list.getHead());
        assertNull(list.getTail());
    }

    @Test(timeout = TIMEOUT)
    public void testTwoNodes() {
        list.addToFront("1");
        list.addToFront("0");
        assertEquals(new String("0"), list.getHead().getData());
        assertEquals(new String("1"), list.getTail().getData());
        assertEquals(new String("0"), list.getTail().getPrevious().getData());
        assertEquals(new String("1"), list.getHead().getNext().getData());
        assertNull(list.getHead().getPrevious());
        assertNull(list.getTail().getNext());

        list.addAtIndex(1, "0.5");
        assertEquals(3, list.size());
        assertEquals(new String("0.5"), list.getTail().getPrevious().getData());
        assertEquals(new String("0.5"), list.getHead().getNext().getData());

        list.removeAtIndex(1);

        assertEquals(new String("0"), list.getTail().getPrevious().getData());
        assertEquals(new String("1"), list.getHead().getNext().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testAddFromFront() {
        for (int i = 0; i < 6; i++) {
            list.addToBack("" + i);
        }

        list.addAtIndex(2, "1.5");
        
        DoublyLinkedListNode<String> cur = list.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("0", cur.getData());

        DoublyLinkedListNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("1", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("1.5", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("2", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("3", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("4", cur.getData());
        
        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("5", cur.getData());
        assertSame(list.getTail(), cur);

        cur = cur.getNext();
        assertNull(cur);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromTail() {
        for (int i = 0; i < 6; i++) {
            list.addToBack("" + i);
        }

        list.removeAtIndex(4);
        
        DoublyLinkedListNode<String> cur = list.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("0", cur.getData());

        DoublyLinkedListNode<String> prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("1", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("2", cur.getData());

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("3", cur.getData());
        
        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertSame(prev, cur.getPrevious());
        assertEquals("5", cur.getData());
        assertSame(list.getTail(), cur);

        cur = cur.getNext();
        assertNull(cur);
    }
        

    @Test(timeout = TIMEOUT)
    public void testIllArgs() {
        try {
            list.addToFront(null);
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            list.addToBack(null);
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            list.addAtIndex(0, null);
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            list.removeLastOccurrence(null);
        } catch (Throwable e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testIndOOB() {

        list.addToFront("1");

        try {
            list.addAtIndex(-1, "1");
        } catch (Throwable e) {
            assertTrue(e instanceof IndexOutOfBoundsException);
        }

        try {
            list.addAtIndex(2, "1");
        } catch (Throwable e) {
            assertTrue(e instanceof IndexOutOfBoundsException);
        }

        try {
            list.removeAtIndex(-1);
        } catch (Throwable e) {
            assertTrue(e instanceof IndexOutOfBoundsException);
        }

        try {
            list.removeAtIndex(1);
        } catch (Throwable e) {
            assertTrue(e instanceof IndexOutOfBoundsException);
        }

        try {
            list.get(-1);
        } catch (Throwable e) {
            assertTrue(e instanceof IndexOutOfBoundsException);
        }

        try {
            list.get(1);
        } catch (Throwable e) {
            assertTrue(e instanceof IndexOutOfBoundsException);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testNoSuchEl() {
        try {
            list.removeFromFront();
        } catch (Throwable e) {
            assertTrue(e instanceof NoSuchElementException);
        }

        try {
            list.removeFromBack();
        } catch (Throwable e) {
            assertTrue(e instanceof NoSuchElementException);
        }

        list.addToBack("0");
        list.addToBack("1");
        list.addToBack("2");

        try {
            list.removeLastOccurrence("3");
        } catch (Throwable e) {
            assertTrue(e instanceof NoSuchElementException);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLastOccurrence() {
        try {
            list.removeLastOccurrence("0a");
        } catch (Throwable e) {
            assertTrue(e instanceof NoSuchElementException);
        }
        String newString = new String("a");
        list.addToBack(newString);
        list.addToFront("b");
        list.addAtIndex(1, "a");

        assertTrue(list.removeLastOccurrence("a") == newString);
        assertEquals(list.size(), 2);
        assertTrue(list.get(list.size() - 1) == "a");

        list.removeLastOccurrence(new String("a"));
        assertEquals(list.size(), 1);

        list.addToBack("c");
        list.removeLastOccurrence("b");
        assertEquals(list.get(0), "c");

        try {
            list.removeLastOccurrence("z");
        } catch (Throwable e) {
            assertTrue(e instanceof NoSuchElementException);
        }

    }
}