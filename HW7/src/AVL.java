import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Huy Le
 * @version 1.0
 * @userid hduc6
 * @GTID 903845849
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * Goat Recitation and TAs
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
        root = null;
        size = 0;
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        root = null;
        size = 0;
        if (data == null) {
            throw new IllegalArgumentException("Cant add null data");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Cant add null data");
            }
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cant add null data");
        }
        root = addHelper(data, root); 
        newHeBF(root);
    }

    /**
     * recursive helper method to add new data
     * @param data the data to add
     * @param current node the recursive method is currently on
     * @return node -> parent node (pointer reinforcement)
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> current) {
        if (current == null) {
            size++;
            //make new node for data to add
            return new AVLNode<T>(data); //makes new node for data to be added
        }
        //data > curr.data -> traverse right
        if (data.compareTo(current.getData()) > 0) {
            current.setRight(addHelper(data, current.getRight()));
            //data < curr.data -> traverse left
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(addHelper(data, current.getLeft()));
        } else {
            return current;
        }
        //update height and balance factor
        newHeBF(current);
        return balance(current);
    }

    /**
     * method to see if we need rotation or not and do it to maintain balance of tree
     *
     * @param node the node whose BF is checked if rotation is needed
     * @return new root node to parent for pointer reinforcement
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        //BF < -1 -> Right subtree is heavier
        if (node.getBalanceFactor() < -1) {
            //Right Child's BF > 0 -> Left heavy
            if (node.getRight().getBalanceFactor() > 0) {
                //Right rotation on right child
                node.setRight(rotateRight(node.getRight()));
            }
            //left rotation on node to balance
            return rotateLeft(node);
            //BF > 1 -> Left subtree is heavier
        } else if (node.getBalanceFactor() > 1) {
            //Left Child's BF < 0 -> Right heavy
            if (node.getLeft().getBalanceFactor() < 0) {
                //Left rotation on left child
                node.setLeft(rotateLeft(node.getLeft()));
            }
            //right rotation on node to balance
            return rotateRight(node);
        }
        //if BF is [-1,1] -> balanced -> no rotation needed
        return node;
    }

    /**
     * method of right rotation on a node
     * @param node node for right rotation
     * @return new root node -> parent (pointer reinforcement)
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        //node holds left child
        AVLNode<T> left = node.getLeft();
        //node.left child -> left.right child
        node.setLeft(left.getRight());
        //left is now new root -> set right child to node
        left.setRight(node);
        //update BF & height of node + left (new root)
        newHeBF(node);
        newHeBF(left);
        //return left as new root
        return left;
    }

    /**
     * method of left rotation on a node
     * @param node node for left rotation
     * @return new root node -> parent (pointer reinforcement)
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        //node holds right child
        AVLNode<T> right = node.getRight();
        //node.right child -> right.left child
        node.setRight(right.getLeft());
        //right is now new root -> set left child to node
        right.setLeft(node);
        //update BF & height of node + right (new root)
        newHeBF(node);
        newHeBF(right);
        //return right as new root
        return right;
    }

    /**
     * this method updates height + BF of every node
     * @param node node to update new height + BF
     */
    private void newHeBF(AVLNode<T> node) {
        //no child -> leaf node
        if (node.getLeft() == null && node.getRight() == null) {
            //set Height & BF = 0
            node.setHeight(0);
            node.setBalanceFactor(0);
            //1 child: Left child
        } else if (node.getRight() == null) {
            //Height = 1 + left child's height
            node.setHeight(1 + node.getLeft().getHeight());
            //BF = 1 + left child's height
            node.setBalanceFactor(node.getLeft().getHeight() + 1);
            //1 child: Right child
        } else if (node.getLeft() == null) {
            //Height = 1 + right child's height
            node.setHeight(1 + node.getRight().getHeight());
            //BF = -1 - right child's height
            node.setBalanceFactor(-1 - node.getRight().getHeight());
        } else { //2 children
            //Height = 1 + max height(left child, right child)
            node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
            //BF = left child.height - right child.height
            node.setBalanceFactor(node.getLeft().getHeight() - node.getRight().getHeight());
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */

    //kinda same with BST
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cant remove null data");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = removeHelper(data, root, dummy);
        if (dummy.getData() == null) {
            throw new java.util.NoSuchElementException("Data not found in AVL");
        }
        size--;
        return dummy.getData();
    }

    /**
     * Helper method for remove() method
     *
     * @param data data to remove
     * @param curr current node to traverse
     * @param dummy store value we removed
     * @return AVLNode returns node -> parent (pointer reinforcement)
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        //data to remove not in tree -> return null
        if (curr == null) {
            return null;
            //data < curr.data -> search left subtree
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(data, curr.getLeft(), dummy));
            //data > curr.data -> search right subtree
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(data, curr.getRight(), dummy));
        } else { //data = curr.data -> node found for removal
            dummy.setData(curr.getData());
            //leaf node -> return null (curr removed from parent)
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
                //1 child: right child -> return right child
            } else if (curr.getLeft() == null) {
                return curr.getRight();
                //1 child: left child -> return left child
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else { //2 children -> find predecessor from left subtree
                //// -> curr.data is replaced w/ predecessor node.data
                curr.setData(getPredecessor(curr.getLeft(), curr));
                if (curr.getLeft() != null) {
                    //recalculate height + BF of curr.left child
                    curr.setLeft(balance(curr.getLeft()));
                }
            }
        }
        //return balanced version of curr after removal
        newHeBF(curr);
        return balance(curr);
    }

    /**
     * method that gets -> removes predecessor node of removed node
     *
     * @param curr node of current recursive method
     * @param parent curr's parent node
     * @return T predecessor node.data
     */
    private T getPredecessor(AVLNode<T> curr, AVLNode<T> parent) {
        //curr has no right child -> curr is predecessor
        if (curr.getRight() == null) {
            //retrieve curr.data
            T returnData = curr.getData();
            //update parent's left child -> curr.left child
            if (parent.getLeft() == curr) {
                //remove curr.left child
                parent.setLeft(curr.getLeft());
            } else {
                //remove curr.right child
                parent.setRight(curr.getLeft());
            }
            //update height + BF of parent + curr
            newHeBF(parent);
            newHeBF(curr);
            //return data of removed predecessor node
            return returnData;
        } else { //curr has right child -> recursion on right child till rightmost node (predecessor)
            return getPredecessor(curr.getRight(), curr);
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cant be null");

        }
        return getHelper(root, data).getData();
    }

    /**
     * Helper method for get() method (same w/ BST)
     * @param curr current node to traverse
     * @param data the data to search for
     * @return node with the data
     */
    private AVLNode<T> getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data needs to be in tree");
        }
        if (data.compareTo(curr.getData()) > 0) {
            return (getHelper(curr.getRight(), data));
        } else if (curr.getData().compareTo(data) > 0) {
            return (getHelper(curr.getLeft(), data));
        } else {
            return curr;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        return containHelper(root, data);

    }
    /**
     * Helper method for contain() method (same w/BST)
     * @param curr current node to traverse
     * @param data the data to search for
     * @return if it contains data or not
     */
    private boolean containHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (data.compareTo(curr.getData()) > 0) {
            return (containHelper(curr.getRight(), data));
        } else if (curr.getData().compareTo(data) > 0) {
            return (containHelper(curr.getLeft(), data));
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     *
     * Should be recursive.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *     76
     *   /    \
     * 34      90
     *  \    /
     *  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cant be null");
        }
        return predecessorHelper(root, data);
    }

    /**
     *
     * @param curr current node to traverse
     * @param data the data to search for
     * @return predecessor of the data
     */
    private T predecessorHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("The data is not in the tree.");
            //data > curr.data (left subtree is non-empty) -> explore right subtree
        } else if (data.compareTo(curr.getData()) > 0) {
            T result = predecessorHelper(curr.getRight(), data);
            //null result -> curr is predecessor -> return curr.data
            if (result == null) {
                return curr.getData();
                //predecessor is found in right subtree -> returns that value
            } else {
                return result;
            }
            //data < curr.data (left subtree is empty) -> explore left subtree
        } else if (data.compareTo(curr.getData()) < 0) {
            T result = predecessorHelper(curr.getLeft(), data);
            return result;
        } else {
            //result of left subtree (not null) is predecessor -> return
            if (curr.getLeft() != null) {
                return getPredecessor(curr.getLeft());
            } else { //left subtree does not have predecessor -> return null
                return null;
            }
        }
    }

    /**
     * Method to help get Predecessor (rightmost node)
     * @param curr current node to traverse
     * @return the predecessor
     */
    private T getPredecessor(AVLNode<T> curr) {
        //base case: curr doesnt have right child -> curr is rightmost node in subtree -> return curr.data
        if (curr.getRight() == null) {
            return curr.getData();
        } else { //curr has right child -> recursion on right child till rightmost leaf node is found -> base case
            T result = getPredecessor(curr.getRight()); //return data of each node visited
            return result; //return predecessor.data
        }
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     *
     * Should be recursive.
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */

    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return maxDNHelper(root);
    }

    /**
     * method finds + returns deepest node.data
     * if there are multiple nodes w same deepest depth -> return rightmost (largest) node
     * @param curr current node to traverse
     * @return data of the deepest node in the tree
     */
    private T maxDNHelper(AVLNode<T> curr) {
        //no child -> curr is deepest node -> return curr.data
        if (curr.getLeft() == null && curr.getRight() == null) {
            return curr.getData();
            //1 child: left child -> traverse left subtree
        } else if (curr.getRight() == null) {
            return maxDNHelper(curr.getLeft());
            //1 child: right child -> traverse right subtree
        } else if (curr.getLeft() == null) {
            return maxDNHelper(curr.getRight());
        } else { //both left + right subtrees exist
            //left subtree is deeper -> recursion on left subtree
            if (curr.getLeft().getHeight() > curr.getRight().getHeight()) {
                return maxDNHelper(curr.getLeft());
            } else { //right subtree is deeper or equal heights -> recursion on right subtree
                return maxDNHelper(curr.getRight());
            }
            //recursion till base case reach -> deepest node is found
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
    public AVLNode<T> getRoot() {
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

