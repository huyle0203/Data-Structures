import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Basic stress tests and other additional tests for MinHeap.
 *
 * @author Alexander Gualino
 * @version 1.0
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;

    // a stress test.
    @Test(timeout = TIMEOUT)
    public void stressTestSortNumbers() {
        // basically, I'll generate a bunch of numbers and I expect the heap to be able to spit them all out!
        var duplicateChecks = new HashSet<Integer>();
        var random = new Random();
        var heap = new MinHeap<Integer>();
        for (var i = 0; i < 500; i++) {
            var input = random.nextInt();
            while (duplicateChecks.contains(input)) {
                input = random.nextInt();
            }
            duplicateChecks.add(input);
            heap.add(input);
        }

        var last = Integer.MIN_VALUE;
        duplicateChecks.clear();
        for (var i = 0; i < 500; i++) {
            var smallest = heap.remove();
            assertFalse(duplicateChecks.contains(smallest));
            duplicateChecks.add(smallest);
            assertTrue(last <= smallest);
            last = smallest;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBuildHeapFromNullArray() {
        new MinHeap<>(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testBuildHeapFromNullElement() {
        var list = new ArrayList<Integer>();
        list.add(null);
        new MinHeap<>(list);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddNullElement() {
        new MinHeap<>().add(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromEmptyHeap() {
        new MinHeap<>().remove();
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetMinimumFromEmptyHeap() {
        new MinHeap<>().getMin();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveMinReplaceWithRight() {
        // heap is like:
        //    1
        //   3   2
        //  4 5 6 7
        var heap = new MinHeap<Integer>();
        heap.add(1);
        heap.add(3);
        heap.add(2);
        heap.add(4);
        heap.add(5);
        heap.add(6);
        heap.add(7);

        assertEquals(1, (int) heap.remove());
        assertArrayEquals(
                new Integer[]{null, 2, 3, 6, 4, 5, 7, null, null, null, null, null, null},
                heap.getBackingArray()
        );
    }

    @Test(timeout = TIMEOUT)
    public void testOperationsOnSaturatedHeap() {
        var heap = new MinHeap<Integer>();
        for (var i = 0; i < MinHeap.INITIAL_CAPACITY - 1; i++) {
            heap.add(i);
        }
        assertEquals(MinHeap.INITIAL_CAPACITY - 1, heap.size());
        assertEquals(MinHeap.INITIAL_CAPACITY, ((Comparable[]) heap.getBackingArray()).length);

        for (var i = 0; i < MinHeap.INITIAL_CAPACITY - 1; i++) {
            assertEquals(i, (int) heap.remove());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testResizing() {
        var heap = new MinHeap<Integer>();
        for (var i = 0; i < MinHeap.INITIAL_CAPACITY - 1; i++) {
            heap.add(i);
        }
        assertEquals(MinHeap.INITIAL_CAPACITY - 1, heap.size());
        assertEquals(MinHeap.INITIAL_CAPACITY, ((Comparable[]) heap.getBackingArray()).length);

        heap.add(MinHeap.INITIAL_CAPACITY - 1);
        assertEquals(MinHeap.INITIAL_CAPACITY, heap.size());
        assertEquals(MinHeap.INITIAL_CAPACITY * 2, ((Comparable[]) heap.getBackingArray()).length);

        assertEquals(0, (int) heap.getMin());
    }

    @Test(timeout = TIMEOUT)
    public void testDownheapALot() {
        var heap = new MinHeap<Integer>();
        // idea: I want to have to downheap until the end of the array
        heap.add(0);
        heap.add(1);
        heap.add(2);
        heap.add(4);
        heap.add(3);
        heap.add(5);
        heap.add(6);
        heap.add(8);
        heap.add(10);
        heap.add(9);
        heap.add(7);
        heap.add(11);

        assertEquals(0, (int) heap.remove());
        assertArrayEquals(
                new Integer[]{null, 1, 3, 2, 4, 7, 5, 6, 8, 10, 9, 11, null},
                heap.getBackingArray()
        );
    }
}