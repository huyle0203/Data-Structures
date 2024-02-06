import org.junit.Test;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Basic stress tests and other additional tests for BST.
 *
 * @author Alexander Gualino
 * @version 1.1
 */
public class ExhaustiveTests {
    private static final int TIMEOUT = 200;

    // a stress test or two for good measure
    @Test(timeout = TIMEOUT)
    public void stressTestAddingPathfindingThenRemoving() {
        var tree = new BST<Integer>();
        tree.add(500);
        for (int i = 500; i > 0; i--) {
            tree.add(500 - i);
            tree.add(500 + i);
        }

        assertTrue(tree.contains(0));
        assertEquals(1, tree.findPathBetween(0, 0).size());
        assertEquals(3, tree.findPathBetween(0, 1000).size());
        assertEquals(1001, tree.size());

        for (int i = 500; i > 0; i--) {
            assertEquals(1003 - 2 * i, tree.findPathBetween(500 - i, 500 + i).size());
            var oneWay = tree.findPathBetween(500 - i, 500 + i);
            Collections.reverse(oneWay);
            assertArrayEquals(oneWay.toArray(), tree.findPathBetween(500 + i, 500 - i).toArray());
        }

        tree.remove(500);
        for (int i = 1; i <= 500; i++) {
            tree.remove(500 - i);
            tree.remove(500 + i);
        }

        assertEquals(0, tree.size());
    }

    // now the exhaustive tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddingNullData() {
        new BST<>().add(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemovingNullData() {
        new BST<>().remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemovingFromEmptyTree() {
        new BST<Integer>().remove(4);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemovingNonElement() {
        var tree = new BST<Integer>();
        tree.add(42);
        tree.remove(7);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGettingNullData() {
        new BST<>().get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGettingFromEmptyTree() {
        new BST<Integer>().get(4);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGettingNonElement() {
        var tree = new BST<Integer>();
        tree.add(42);
        tree.get(7);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testFindPathBetweenLeftNull() {
        new BST<Integer>().findPathBetween(null, 5);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testFindPathBetweenRightNull() {
        new BST<Integer>().findPathBetween(5, null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testFindPathEmptyTree() {
        new BST<Integer>().findPathBetween(1, 2);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testFindPathLeftElementDoesNotExist() {
        var tree = new BST<Integer>();
        tree.add(1);
        tree.findPathBetween(2, 1);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testFindPathRightElementDoesNotExist() {
        var tree = new BST<Integer>();
        tree.add(1);
        tree.findPathBetween(1, 2);
    }

    @Test(timeout = TIMEOUT)
    public void testAddAlreadyAddedData() {
        var tree = new BST<Integer>();
        // at root
        tree.add(1);
        tree.add(1);

        assertEquals(1, tree.size());

        // further down
        tree.add(0);
        tree.add(0);

        assertEquals(2, tree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testAddChecksExistenceCorrectly() {
        // somewhat nonsensical, just making sure every branch is checked...
        var tree = new BST<Integer>();
        tree.add(1);
        tree.add(2);

        assertEquals(2, tree.size());
        assertNull(tree.getRoot().getLeft());

        tree.add(0);
        assertEquals(3, tree.size());
        assertNotNull(tree.getRoot().getLeft());

        tree.remove(2);
        assertEquals(2, tree.size());
        assertNull(tree.getRoot().getRight());

        tree.add(2);
        assertEquals(3, tree.size());
        assertNotNull(tree.getRoot().getRight());

        tree.add(3);
        tree.add(2);
        assertEquals(4, tree.size());
        assertNotNull(tree.getRoot().getRight().getRight());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRoot() {
        var tree = new BST<Integer>();
        tree.add(4);
        tree.add(5);

        assertEquals(4, (long) tree.remove(4));
        assertEquals(1, tree.size());
        assertEquals(5, (long) tree.getRoot().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDeepNode() {
        var tree = new BST<Integer>();
        tree.add(4);
        tree.add(5);
        tree.add(6);

        assertEquals(6, (long) tree.remove(6));
        assertEquals(2, tree.size());
        assertEquals(4, (long) tree.getRoot().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        var tree = new BST<Integer>();
        tree.add(42);
        assertFalse(tree.contains(7));
        assertTrue(tree.contains(42));
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyOrderings() {
        var tree = new BST<Integer>();
        assertArrayEquals(new Object[]{}, tree.preorder().toArray());
        assertArrayEquals(new Object[]{}, tree.inorder().toArray());
        assertArrayEquals(new Object[]{}, tree.postorder().toArray());
        assertArrayEquals(new Object[]{}, tree.levelorder().toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testHeights() {
        var rightBiasedTree = new BST<Integer>();
        rightBiasedTree.add(1);
        rightBiasedTree.add(2);
        rightBiasedTree.add(3);

        assertEquals(2, rightBiasedTree.height());

        var leftBiasedTree = new BST<>(rightBiasedTree.postorder());
        assertEquals(2, leftBiasedTree.height());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromLeft() {
        var tree = new BST<Integer>();
        tree.add(3);
        tree.add(2);

        assertEquals(2, (long) tree.remove(2));
        assertEquals(1, tree.size());
        assertNull(tree.getRoot().getLeft());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveReplacements() {
        // various tests around what data gets kept, etc.
        var tree = new BST<Integer>();
        tree.add(5);

        // idea: have to replace a node with its right child
        tree.add(2);
        tree.add(3);
        assertEquals(2, (long) tree.remove(2));
        assertEquals(2, tree.size());
        assertEquals(3, (long) tree.getRoot().getLeft().getData());
        tree.remove(3);

        // idea: have to replace a node with its left child
        tree.add(8);
        tree.add(7);
        assertEquals(8, (long) tree.remove(8));
        assertEquals(2, tree.size());
        assertEquals(7, (long) tree.getRoot().getRight().getData());
        tree.remove(7);
    }

    @Test(timeout = TIMEOUT)
    public void testTrickyRemoveReplacement() {
        // when I try to explain my own code to myself, this is the logic that trips me up the most.
        var tree = new BST<Integer>();
        tree.add(5);
        tree.add(4);
        tree.add(10);
        tree.add(6);
        tree.add(7);

        assertEquals(5, tree.size());
        assertEquals(5, (long) tree.remove(5));
        assertEquals(4, tree.size());
        assertEquals(6, (long) tree.getRoot().getData());
        assertEquals(4, (long) tree.getRoot().getLeft().getData());
        assertEquals(10, (long) tree.getRoot().getRight().getData());
        assertEquals(7, (long) tree.getRoot().getRight().getLeft().getData());
    }

    @Test(timeout = TIMEOUT)
    public void testFindPathBetweenSameData() {
        var tree = new BST<Integer>();
        tree.add(1);
        assertArrayEquals(new Object[]{1}, tree.findPathBetween(1, 1).toArray());
    }

    @Test(timeout = TIMEOUT)
    public void testFindPathOnTheRight() {
        // turns out I never tested an ancestor on the right side of the tree!
        var tree = new BST<Integer>();
        tree.add(1);
        tree.add(2);
        tree.add(3);
        assertArrayEquals(new Object[]{2, 3}, tree.findPathBetween(2, 3).toArray());
        assertArrayEquals(new Object[]{3, 2}, tree.findPathBetween(3, 2).toArray());
    }
}