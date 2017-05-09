import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

class DoublyLinkedList<T extends Cacheable> {
    Node<T> head;
    Node<T> tail;
    private int size=0;

    /**
     * Removes specified Node from the list, if it is part of the list
     *
     * @param nodeToRemove the specific Node to remove from this list
     * @return TRUE if the element was part of the list, otherwise FALSE
     */
    public boolean remove(Node<T> nodeToRemove) {
        if (nodeToRemove == tail) {
            removeTail();
            --size;
            return true;
        } else if (nodeToRemove == head) {
            removeHead();
            --size;
            return true;
        } else if (nodeToRemove.prev != null && nodeToRemove.next != null) {
            Node<T> prevNode = nodeToRemove.prev;
            Node<T> nextNode = nodeToRemove.next;

            // have next and previous nodes now point to each other
            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            // null out prev and next pointers on node being removed
            nodeToRemove.prev = null;
            nodeToRemove.next = null;

            --size;
            return true;
        }

        return false;
    }

    /**
     * Removes and returns the tail (i.e., final element) of this DoublyLinkedList, if the list is not empty, and
     * establishes the tail's predecessor as the new tail of the list.
     * @return the data element contained in the previous tail of the list
     * @throws NoSuchElementException if the list was empty when this method was called
     */
    public T removeTail() throws NoSuchElementException {
        if (tail == null) {
            throw new NoSuchElementException("Unable to remove the tail since list is currently empty");
        }

        Node<T> currentTail = tail;

        if (head == currentTail) {
            head = null;
            tail = null;
        } else {
            Node<T> newTail = currentTail.prev;

            currentTail.prev = null;
            newTail.next = null;
            tail = newTail;
        }

        --size;
        return currentTail.getData();
    }

    /**
     * Removes and returns the head (i.e., first element) of this DoublyLinkedList, if the list is not empty, and
     * establishes the head's successor as the new head of the list.
     * @return the data element contained in the previous head of the list
     * @throws NoSuchElementException if the list was empty when this method was called
     */
    public T removeHead() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Unable to remove the head since list is currently empty");
        }

        Node<T> currentHead = head;

        if (tail == currentHead) {
            head = null;
            tail = null;
        } else {
            Node<T> newHead = currentHead.next;

            currentHead.next = null;
            newHead.prev = null;
            head = newHead;
        }

        --size;
        return currentHead.getData();
    }

    /**
     * Inserts the provided Node as the new head of this DoublyLinkedList (i.e., the very first element).
     * The new head will have the previous head as its successor.
     * @param newHead the Node to be inserted as the new head of this list
     */
    public void insertAsHead(Node<T> newHead) {
        Preconditions.checkArgument(newHead != null, "Node to be newly inserted as " +
                "the head of the LinkedList cannot be NULL");

        if (newHead == head) {
            // already at the head of the list, so don't do anything
            return;
        }

        Node<T> currentHead = head;
        if (currentHead != null) {
            currentHead.prev = newHead;
        }

        newHead.next = currentHead;
        head = newHead;

        if (tail == null) {
            // no tail currently, so set this new node as the tail as well
            tail = newHead;
        }
        ++size;
    }

    public int getSize() {
        return size;
    }

    public List<T> traverse() {
        if (size == 0) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>();
        Node<T> currentNode = head;

        while (currentNode != null) {
            result.add(currentNode.data);
            currentNode = currentNode.next;
        }

        return result;
    }

    static class Node<T extends Cacheable> {
        private T data;
        private Node prev;
        private Node next;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }
}
