import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author HUY LE
 * @version 1.0
 * @userid hduc6
 * @GTID 903845849
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index can't be smaller than 0 or larger than size");
        }
        if (data == null) {
            throw new IllegalArgumentException("cannot search for a null data");
        }
        //Creates new node with only the given data
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        //Adding to the front
        if (index == 0) {
            //create pointer to head
            newNode.setNext(head);
            if (head != null) {
                //create pointer to node
                head.setPrevious(newNode);
            }
            //new node is now head
            head = newNode;
            if (size == 0) {
                //new node is both head & tail
                tail = newNode;
            }
        //Adding to the end
        //cant be index == size - 1 cuz node needs to be in front of tail (size = index gives 1 space to make new Node)
        } else if (size == index) {
            newNode.setPrevious(tail);
            //new node is now tail
            if (tail != null) {
                tail.setNext(newNode);
            }
            //new node is both head & tail
            tail = newNode;
        //Adding to the middle
        } else {
            DoublyLinkedListNode<T> current;
            //Traverse from the shorter side or from tail
            if ((size - index) <= index) {
                current = tail;
                for (int i = 0, j = size - index - 1; i < j; i++) {
                    current = current.getPrevious();
                }
            } else {
                //Traverse from the head
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
            }
            //Creates new data w/ the given data and node references
            newNode = new DoublyLinkedListNode<T>(data, current.getPrevious(), current);
            //prev node -- pointer -> new node
            current.getPrevious().setNext(newNode);
            //current node -- pointer -> new node
            current.setPrevious(newNode);
            //newNode.setPrevious(current);
            //newNode.setNext(current.getNext());
            //current.getNext().setPrevious(newNode);
            //current.setNext(newNode);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cant search for null to data");
        }
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cant search for null data");
        }
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index has to be larger than 0 or less than or equal to size");
        }
        //set up generic
        T dataRemoved = null;
        //Removing at the front
        if (index == 0) {
            dataRemoved = head.getData();
            //next node is new head
            head = head.getNext();
            //if current head not empty
            if (head != null) {
                //remove pointer of prev node --> current head
                head.getPrevious().setNext(null);
                //remove pointer of current head --> prev node
                head.setPrevious(null);
            } else {
                //make both tail & head null -> only node w/o pointer
                tail = null;
            }
            size--;
            return dataRemoved;
        //Removing at the back
        } else if (index == size - 1) {
            dataRemoved = tail.getData();
            tail = tail.getPrevious();
            //make both tail & head null -> only node w/o pointer
            if (tail == null) {
                head = null;
            } else {
                //remove pointer from prev node -> tail ----> nothing points to tail so it is garbage
                tail.setNext(null);
            }
            size--;
            return dataRemoved;
        }
        //Removing at the middle
        DoublyLinkedListNode<T> current;
        //Traverse from the shorter side or from tail
        if ((size - index) <= index) {
            current = tail;
            for (int i = 0, j = size - index - 1; i < j; i++) {
                current = current.getPrevious();
            }
        } else {
            //Traverse from the head
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        }
        //
        dataRemoved = current.getData();
        //prev node  ---> connect pointer ---> next node (current node = garbage)
        current.getPrevious().setNext(current.getNext());
        //next node  ---> connect pointer ---> prev node (current node = garbage)
        current.getNext().setPrevious(current.getPrevious());
        size--;
        return dataRemoved;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List can't be empty");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("List can't be empty");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index has to be greater than 0 or within the size");
        }
        //if data is at front
        if (index == 0) {
            return head.getData();
        }
        //if data is at the back (size - 1 cuz index)
        if (index == size - 1) {
            return tail.getData();
        } else {
            //Creates new node current with only the given data
            DoublyLinkedListNode<T> current;
            //Traverse from tail -> middle
            if ((size - index) <= index) {
                current = tail;
                for (int i = 0, j = size - index - 1; i < j; i++) {
                    current = current.getPrevious();
                }
            } else {
                //Traverse from head -> middle
                current = head;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        //clear data in head + tail
        head = null;
        tail = null;
        //reset size
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("cannot search for null data");
        }
        //Set current node as tail
        DoublyLinkedListNode<T> current = tail;
        //Tail -> Head
        for (int i = size - 1; i >= 0; i--) {
            //if data inside matches what we r finding
            if (current.getData().equals(data)) {
                //stored data to return
                T returnData = current.getData();
                //if theres only 1 node
                if (size == 1) {
                    head = null;
                    tail = null;
                //if node is at the back
                } else if (i == size - 1) {
                    //make prev node = current -> set current node (the next node now) = null -> set tail = prev node
                    current.getPrevious().setNext(null);
                    tail = current.getPrevious();
                //if node is at front
                } else if (i == 0) {
                    //make next node = current -> set current node (the prev node now) = null -> set head = next node
                    current.getNext().setPrevious(null);
                    head = current.getNext();
                } else {
                    //prev node  ---> connect pointer ---> next node (current node = garbage)
                    current.getPrevious().setNext(current.getNext());
                    //next node  ---> connect pointer ---> prev node (current node = garbage)
                    current.getNext().setPrevious(current.getPrevious());
                }
                size--;
                return returnData;
            }
            //keep traversing from tail -> head
            current = current.getPrevious();
        }
        throw new NoSuchElementException("No data can't be found");
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        DoublyLinkedListNode<T> current = head;
        Object[] array = new Object[size];
        int i = 0;
        while (i != size) {
            array[i] = current.getData();
            current = current.getNext();
            i++;
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
