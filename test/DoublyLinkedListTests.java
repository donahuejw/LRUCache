import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DoublyLinkedListTests {
    @Test
    public void insertNewHeadIntoEmptyListCreatesListWithSingleElementAsHeadAndTailTest() throws Exception {
        DoublyLinkedList<NodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<NodeType> newHead = new DoublyLinkedList.Node<>(new NodeType(5));

        assertEquals(0, dll.getSize());
        assertNull(dll.head);
        assertNull(dll.tail);

        dll.insertAsHead(newHead);

        assertEquals(1, dll.getSize());
        assertEquals(newHead, dll.head);
        assertEquals(newHead, dll.tail);
        assertNull(newHead.getNext());
        assertNull(newHead.getPrev());

        assertEquals(Lists.newArrayList(new NodeType(5)), dll.traverse());
    }

    @Test
    public void insertNewHeadIntoExistingListPutsNewElementAsHeadOfList() throws Exception {
        DoublyLinkedList<NodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<NodeType> startingHead = new DoublyLinkedList.Node<>(new NodeType(5));
        dll.insertAsHead(startingHead);

        assertEquals(1, dll.getSize());
        assertEquals(startingHead, dll.head);
        assertEquals(startingHead, dll.tail);

        DoublyLinkedList.Node<NodeType> newHead = new DoublyLinkedList.Node<>(new NodeType(10));
        dll.insertAsHead(newHead);

        assertEquals(2, dll.getSize());
        assertEquals(newHead, dll.head);
        assertNull(newHead.getPrev());
        assertEquals(startingHead, dll.tail);

        assertEquals(Lists.newArrayList(new NodeType(10), new NodeType(5)), dll.traverse());
    }

    @Test
    public void insertExistingHeadAsNewHeadOfListLeavesListUnchanged() throws Exception {
        DoublyLinkedList<NodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<NodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new NodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<NodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new NodeType(10));
        dll.insertAsHead(secondNodeAdded);

        assertEquals(2, dll.getSize());
        assertEquals(secondNodeAdded, dll.head);
        assertEquals(firstNodeAdded, dll.tail);
        assertEquals(Lists.newArrayList(new NodeType(10), new NodeType(5)), dll.traverse());

        dll.insertAsHead(secondNodeAdded);
        assertEquals(2, dll.getSize());
        assertEquals(secondNodeAdded, dll.head);
        assertEquals(firstNodeAdded, dll.tail);
        assertEquals(Lists.newArrayList(new NodeType(10), new NodeType(5)), dll.traverse());
    }

    @Test
    public void removeTailOnEmptyListThrowsNoSuchElementException() throws Exception {
        DoublyLinkedList<Cacheable> dll = new DoublyLinkedList<>();
        boolean threwAsExpected = false;

        try {
            dll.removeTail();
        } catch (NoSuchElementException e) {
            threwAsExpected = true;
        }

        assertTrue("Call to removeTail() method did not throw expected NoSuchElementException",threwAsExpected);
    }

    @Test
    public void removeTailOnSingleElementListResultsInEmptyList() throws Exception {
        DoublyLinkedList<NodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<NodeType> onlyAddedNode = new DoublyLinkedList.Node<>(new NodeType(5));
        dll.insertAsHead(onlyAddedNode);

        assertEquals(1, dll.getSize());
        assertEquals(onlyAddedNode, dll.head);
        assertEquals(onlyAddedNode, dll.tail);

        NodeType tailNodeData = dll.removeTail();

        assertEquals(tailNodeData, onlyAddedNode.getData());
        assertEquals(0, dll.getSize());
        assertNull(dll.head);
        assertNull(dll.tail);
    }

    @Test
    public void removeTailOnTwoElementListReturnsExpectedElementAndProperlySetsUpNewTail() throws Exception {
        DoublyLinkedList<NodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<NodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new NodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<NodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new NodeType(10));
        dll.insertAsHead(secondNodeAdded);

        assertEquals(2, dll.getSize());
        assertEquals(secondNodeAdded, dll.head);
        assertEquals(firstNodeAdded, dll.tail);
        assertEquals(Lists.newArrayList(new NodeType(10), new NodeType(5)), dll.traverse());

        NodeType removedTail = dll.removeTail();
        assertEquals(removedTail, firstNodeAdded.getData());
        assertEquals(1, dll.getSize());
        assertEquals(secondNodeAdded, dll.head);
        assertEquals(secondNodeAdded, dll.tail);
        assertNull(secondNodeAdded.getNext());
    }

    @Test
    public void removeTailOnMoreThanTwoElementListReturnsExpectedElementAndProperlySetsUpNewTail() throws Exception {
        DoublyLinkedList<NodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<NodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new NodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<NodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new NodeType(10));
        dll.insertAsHead(secondNodeAdded);
        DoublyLinkedList.Node<NodeType> thirdNodeAdded = new DoublyLinkedList.Node<>(new NodeType(15));
        dll.insertAsHead(thirdNodeAdded);

        assertEquals(3, dll.getSize());
        assertEquals(thirdNodeAdded, dll.head);
        assertEquals(firstNodeAdded, dll.tail);
        assertEquals(Lists.newArrayList(new NodeType(15), new NodeType(10), new NodeType(5)), dll.traverse());

        NodeType removedTail = dll.removeTail();
        assertEquals(removedTail, firstNodeAdded.getData());
        assertEquals(2, dll.getSize());
        assertEquals(thirdNodeAdded, dll.head);
        assertEquals(secondNodeAdded, dll.tail);
        assertNull(secondNodeAdded.getNext());
        assertEquals(Lists.newArrayList(new NodeType(15), new NodeType(10)), dll.traverse());
    }

    @Test
    public void removeHeadOnEmptyListThrowsNoSuchElementException() throws Exception {
        DoublyLinkedList<Cacheable> dll = new DoublyLinkedList<>();
        boolean threwAsExpected = false;

        try {
            dll.removeHead();
        } catch (NoSuchElementException e) {
            threwAsExpected = true;
        }

        assertTrue("Call to removeHead() method did not throw expected NoSuchElementException",threwAsExpected);
    }

    @Test
    public void removeHeadOnSingleElementListResultsInEmptyList() throws Exception {
        fail();
    }

    @Test
    public void removeHeadOnTwoOrMoreElementListReturnsExpectedElementAndProperlySetsUpNewHead() throws Exception {
        fail();
    }

    @Test
    public void removeReturnsTrueIfElementIsInList() throws Exception {
        fail();
    }

    @Test
    public void removeReturnsFalseIfElementIsNotInList() throws Exception {
        fail();
    }

    @Test
    public void removeResultsInEmptyListIfArgIsOnlyMemberOfList() throws Exception {
        fail();
    }

    @Test
    public void removeSetsUpNewHeadIfCurrentHeadIsPassedAsArg() throws Exception {
        fail();
    }

    @Test
    public void removeSetsUpNewTailIfCurrentTailIsPassedAsArg() throws Exception {
        fail();
    }

    @Test
    public void removeLeavesListInConsistentStateWhenNodeRemovedFromMiddle() throws Exception {
        // after removal, tail and head should still be valid, and list should be fully traversable
        fail();
    }

    @Test
    public void removeClearsPrevAndNextPointersOnRemovedNode() throws Exception {
        fail();
    }

    private static class NodeType implements Cacheable {
        int data;

        public NodeType(int data) {
            this.data = data;
        }

        @Override
        public String getID() {
            return Integer.toString(data);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeType nodeType = (NodeType) o;

            return data == nodeType.data;
        }

        @Override
        public int hashCode() {
            return data;
        }
    }
}
