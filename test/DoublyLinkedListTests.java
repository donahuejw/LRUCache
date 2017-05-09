import com.google.common.collect.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

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
        assertEquals(startingHead, dll.tail);

        assertEquals(Lists.newArrayList(new NodeType(10), new NodeType(5)), dll.traverse());
    }

    @Test
    public void insertExistingHeadAsNewHeadOfListLeavesListUnchanged() throws Exception {
        fail();

    }

    @Test
    public void removeTailOnEmptyListThrowsNoSuchElementException() throws Exception {
        fail();
    }

    @Test
    public void removeTailOnSingleElementListResultsInEmptyList() throws Exception {
        fail();
    }

    @Test
    public void removeTailOnTwoOrMoreElementListReturnsExpectedElementAndProperlySetsUpNewTail() throws Exception {
        fail();
    }

    @Test
    public void removeHeadOnEmptyListThrowsNoSuchElementException() throws Exception {
        fail();
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
