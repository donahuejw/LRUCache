import com.google.common.base.Preconditions;

class DoublyLinkedList<T extends Cacheable> {
    Node<T> head;
    Node<T> tail;

    public boolean remove(Node<T> nodeToRemove) {
        if (nodeToRemove == tail) {
            removeTail();
            return true;
        } else if (nodeToRemove == head) {
            removeHead();
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

            return true;
        }

        return false;
    }

    public Node<T> removeTail() {
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

        return currentTail;
    }

    public Node<T> removeHead() {
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

        return currentHead;
    }

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
    }

    static class Node<T> {
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
