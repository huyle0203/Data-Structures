import java.util.LinkedList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Huy Le
 * @version 1.0
 * @userid hduc6
 * @GTID 903845849
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("collection can't be null");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Cant insert null elements in collection");
            }
            add(element);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        root = addHelper(data, root);
    }

    /**
     * Helper method for add() method
     * @param data the data to add
     * @param current the current node to traverse
     * @return node to traverse back up the recursion
     */
    private BSTNode<T> addHelper(T data, BSTNode<T> current) {
        if (current == null) {
            size++;
            return new BSTNode<T>(data);
        }
        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(data, current.getLeft()));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(addHelper(data, current.getRight()));
        }
        return current;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        BSTNode<T> dummy2 = new BSTNode<>(null);
        root = removeHelper(data, root, dummy2);
        return dummy2.getData();
    }

    /**
     * Helper method for remove() method
     * @param data data to remove
     * @param current current node to traverse
     * @param dummy2 store value we removed
     * @return node with removed data
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> current, BSTNode<T> dummy2) {
        if (current == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        if (data.compareTo(current.getData()) == 0) {
            size--;
            dummy2.setData(current.getData());
            //Case 1: The node containing the data is a leaf (no children) --> simply remove it.
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            //Case 2: The node containing the data has one child (left child) --> replace it with its child.
            } else if (current.getLeft() != null && current.getRight( ) == null) {
                return current.getLeft();
                //Case 2: The node containing the data has one child (right child) --> replace it with its child
            } else if (current.getLeft() == null && current.getRight() != null) {
                return current.getRight();
                //Case 3: 2 children node
            } else {
                BSTNode<T> dummy = new BSTNode<T>(null);
                current.setRight(findSuccessor(current.getRight(), dummy));
                current.setData(dummy.getData());
                return current;
            }
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeHelper(data, current.getLeft(), dummy2));
            return current;
        } else {
            current.setRight(removeHelper(data, current.getRight(), dummy2));
            return current;
        }
    }

    /**
     * Helper method for case of 2 children node
     * @param current current node to traverse
     * @param dummy to store the data (as successor)
     * @return node of the store data or successor
     */
    private BSTNode<T> findSuccessor(BSTNode<T> current, BSTNode<T> dummy) {
        if (current.getLeft() == null) {
            dummy.setData(current.getData());
            return current.getRight();
        } else {
            current.setLeft(findSuccessor(current.getLeft(), dummy));
            return current;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        T found = getHelper(data, root).getData();
        return found;
    }

    /**
     * Helper method for get() method
     * @param data the data to search for
     * @param current current node to traverse
     * @return node with the data
     */
    private BSTNode<T> getHelper(T data, BSTNode<T> current) {
        if (current == null) {
            throw new NoSuchElementException("Data needs to be in tree");
            //size++;
        }
        if (data.compareTo(current.getData()) == 0) {
            return current;
        } else if (data.compareTo(current.getData()) < 0) {
            return getHelper(data, current.getLeft());
        } else {
            return getHelper(data, current.getRight());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        return containHelper(data, root);
    }

    /**
     * Helper method for contain() method
     * @param data the data to search for
     * @param current current node to traverse
     * @return if it has data or not
     */
    private boolean containHelper(T data, BSTNode<T> current) {
        if (current == null) {
            return false;
        }
        if (data.compareTo(current.getData()) == 0) {
            return true;
        } else if (data.compareTo(current.getData()) < 0) {
            return containHelper(data, current.getLeft());
        } else {
            return containHelper(data, current.getRight());
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * Helper method for preorder() method
     * @param node current node we are at
     * @param list preorder list
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * Helper method for inorder() method
     * @param node current node we are at
     * @param list inorder list
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }
    }
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<>();
        postorderHelper(root, list);
        return list;
    }

    /**
     * Helper method for postorder() method
     * @param node current node we are at
     * @param list postorder list
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> storedList = new LinkedList<>();
        if (root == null) {
            return storedList;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> current = queue.remove();
            storedList.add(current.getData());
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }
        return storedList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method for height() method
     * @param node current node we are at
     * @return height of tree or -1 if tree is empty
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        int heightLeft = heightHelper(node.getLeft());
        int heightRight = heightHelper(node.getRight());
        return Math.max(heightLeft, heightRight) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data 1 to start the path or data 2 to end the path cant be null");
        }
        LinkedList<T> list = new LinkedList<>();
        BSTNode<T> ancestor = ancestorFind(data1, data2, root);
        // find path between from data 1 till ancestor
        findPathBetween(data1, ancestor, list, true);
        // remove the last element
        list.removeLast();
        // find the path from ancestor to data 2
        findPathBetween(data2, ancestor, list, false);
        return list;
    }

    /**
     * Helper method to find ancestor
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @param root the node to start from
     * @return deepest common ancestor of 2 data
     */
    private BSTNode<T> ancestorFind(T data1, T data2, BSTNode<T> root) {
        if (root == null) {
            throw new NoSuchElementException("Data 1 to start the path or data 2 to end the path has to be in tree");
        } else {
            if (root.getData().compareTo(data1) > 0 && root.getData().compareTo(data2) > 0) {
                return ancestorFind(data1, data2, root.getLeft());
            } else if (root.getData().compareTo(data1) < 0 && root.getData().compareTo(data2) < 0) {
                return ancestorFind(data1, data2, root.getRight());
            } else {
                return root;
            }
        }
    }

    /**
     * Helper method to find the path going between data1, data2 m
     * @param data the data to search for
     * @param current current node to traverse
     * @param list list going from data1 to data2
     * @param front true false statement to tell whether go left or right
     */
    private void findPathBetween(T data, BSTNode<T> current, LinkedList<T> list, boolean front) {
        if (current == null) {
            throw new NoSuchElementException("");
        }
        // Take the ancestor + input either data 1 or data 2
        // Depends on which one we have a boolean that tells whether go left or right
        // --> add it to the list and go until either data 1 or data 2 is found
        if (front) {
            list.addFirst(current.getData());
        } else {
            list.addLast(current.getData());
        }
        if (data.compareTo(current.getData()) > 0) {
            findPathBetween(data, current.getRight(), list, front);
        } else if (data.compareTo(current.getData()) < 0) {
            findPathBetween(data, current.getLeft(), list, front);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
