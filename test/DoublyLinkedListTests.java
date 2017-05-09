import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DoublyLinkedListTests {
    @Test
    public void insertNewHeadIntoEmptyListCreatesListWithSingleElementAsHeadAndTailTest() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> newHead = new DoublyLinkedList.Node<>(new TestNodeType(5));

        assertEquals(0, dll.getSize());
        assertNull(dll.head);
        assertNull(dll.tail);

        dll.insertAsHead(newHead);

        checkThatListIsConsistent(dll, newHead, newHead, Lists.newArrayList(new TestNodeType(5)));
    }

    @Test
    public void insertNewHeadIntoExistingListPutsNewElementAsHeadOfList() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> startingHead = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(startingHead);

        assertEquals(1, dll.getSize());
        assertEquals(startingHead, dll.head);
        assertEquals(startingHead, dll.tail);

        DoublyLinkedList.Node<TestNodeType> newHead = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(newHead);

        checkThatListIsConsistent(dll, newHead, startingHead, Lists.newArrayList(new TestNodeType(10), new TestNodeType(5)));
    }

    @Test
    public void insertExistingHeadAsNewHeadOfListLeavesListUnchanged() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);

        ArrayList<TestNodeType> expectedElements = Lists.newArrayList(new TestNodeType(10), new TestNodeType(5));

        checkThatListIsConsistent(dll, secondNodeAdded, firstNodeAdded, expectedElements);

        dll.insertAsHead(secondNodeAdded);

        checkThatListIsConsistent(dll, secondNodeAdded, firstNodeAdded, expectedElements);
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
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> onlyAddedNode = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(onlyAddedNode);

        assertEquals(1, dll.getSize());
        assertEquals(onlyAddedNode, dll.head);
        assertEquals(onlyAddedNode, dll.tail);

        TestNodeType tailNodeData = dll.removeTail();

        assertEquals(tailNodeData, onlyAddedNode.getData());
        checkThatListIsConsistent(dll);
    }

    @Test
    public void removeTailOnTwoElementListReturnsExpectedElementAndProperlySetsUpNewTail() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);

        ArrayList<TestNodeType> expectedElements = Lists.newArrayList(new TestNodeType(10), new TestNodeType(5));
        checkThatListIsConsistent(dll, secondNodeAdded, firstNodeAdded, expectedElements);

        TestNodeType removedTail = dll.removeTail();
        assertEquals(removedTail, firstNodeAdded.getData());
        checkThatListIsConsistent(dll, secondNodeAdded, secondNodeAdded, Lists.newArrayList(new TestNodeType(10)));
    }

    @Test
    public void removeTailOnMoreThanTwoElementListReturnsExpectedElementAndProperlySetsUpNewTail() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);
        DoublyLinkedList.Node<TestNodeType> thirdNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(15));
        dll.insertAsHead(thirdNodeAdded);

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(new TestNodeType(15), new TestNodeType(10), new TestNodeType(5)));

        TestNodeType removedTail = dll.removeTail();
        assertEquals(removedTail, firstNodeAdded.getData());

        checkThatListIsConsistent(dll, thirdNodeAdded, secondNodeAdded, Lists.newArrayList(new TestNodeType(15), new TestNodeType(10)));
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
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> onlyAddedNode = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(onlyAddedNode);

        assertEquals(1, dll.getSize());
        assertEquals(onlyAddedNode, dll.head);
        assertEquals(onlyAddedNode, dll.tail);

        assertEquals(onlyAddedNode.getData(), dll.removeHead());
        checkThatListIsConsistent(dll, null, null, Collections.emptyList());
    }

    @Test
    public void removeHeadOnTwoOrMoreElementListReturnsExpectedElementAndProperlySetsUpNewHead() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);
        DoublyLinkedList.Node<TestNodeType> thirdNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(15));
        dll.insertAsHead(thirdNodeAdded);

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(new TestNodeType(15), new TestNodeType(10), new TestNodeType(5)));

        assertEquals(dll.removeHead(), thirdNodeAdded.getData());

        checkThatListIsConsistent(dll, secondNodeAdded, firstNodeAdded, Lists.newArrayList(new TestNodeType(10), new TestNodeType(5)));
    }

    @Test
    public void removeReturnsTrueIfElementIsInList() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);

        assertEquals(firstNodeAdded, dll.tail);
        assertTrue(dll.remove(firstNodeAdded));

        checkThatListIsConsistent(dll);
    }

    @Test
    public void removeReturnsFalseIfElementIsNotInList() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);

        DoublyLinkedList.Node<TestNodeType> missingNode = new DoublyLinkedList.Node<>(new TestNodeType(15));
        DoublyLinkedList.Node<TestNodeType> current = dll.head;

        // make sure node is not in list
        while (current != null) {
            assertNotEquals(missingNode, current);
            current = current.getNext();
        }

        assertFalse(dll.remove(missingNode));
    }

    @Test
    public void removeResultsInEmptyListIfArgIsOnlyMemberOfList() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> onlyAddedNode = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(onlyAddedNode);

        assertEquals(1, dll.getSize());
        assertEquals(onlyAddedNode, dll.head);
        assertEquals(onlyAddedNode, dll.tail);

        assertTrue(dll.remove(onlyAddedNode));

        checkThatListIsConsistent(dll, null, null, Collections.emptyList());
    }

    @Test
    public void removeThrowsIllegalArgExceptionOnNullArg() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        boolean threwAsExpected = false;

        try {
            dll.remove(null);
        } catch (IllegalArgumentException e) {
            threwAsExpected = true;
        }

        assertTrue(threwAsExpected);
    }

    @Test
    public void removeSetsUpNewHeadIfCurrentHeadIsPassedAsArg() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);

        assertEquals(2, dll.getSize());
        assertEquals(secondNodeAdded, dll.head);
        assertEquals(firstNodeAdded, dll.tail);

        assertTrue(dll.remove(secondNodeAdded));

        checkThatListIsConsistent(dll, firstNodeAdded, firstNodeAdded, null);
    }

    @Test
    public void removeSetsUpNewTailIfCurrentTailIsPassedAsArg() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);

        assertEquals(2, dll.getSize());
        assertEquals(secondNodeAdded, dll.head);
        assertEquals(firstNodeAdded, dll.tail);

        assertTrue(dll.remove(firstNodeAdded));

        checkThatListIsConsistent(dll, secondNodeAdded, secondNodeAdded, null);
    }

    @Test
    public void removeLeavesListInConsistentStateWhenNodeRemovedFromMiddle() throws Exception {
        // after removal, tail and head should still be valid, and list should be fully traversable
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        TestNodeType firstElem = new TestNodeType(5);
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(firstElem);
        dll.insertAsHead(firstNodeAdded);
        TestNodeType secondElem = new TestNodeType(10);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(secondElem);
        dll.insertAsHead(secondNodeAdded);
        TestNodeType thirdElem = new TestNodeType(15);
        DoublyLinkedList.Node<TestNodeType> thirdNodeAdded = new DoublyLinkedList.Node<>(thirdElem);
        dll.insertAsHead(thirdNodeAdded);

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(thirdElem, secondElem, firstElem));

        assertTrue(dll.remove(secondNodeAdded));

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(thirdElem, firstElem));
    }

    @Test
    public void removeClearsPrevAndNextPointersOnRemovedNode() throws Exception {
        DoublyLinkedList<TestNodeType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestNodeType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestNodeType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(10));
        dll.insertAsHead(secondNodeAdded);
        DoublyLinkedList.Node<TestNodeType> thirdNodeAdded = new DoublyLinkedList.Node<>(new TestNodeType(15));
        dll.insertAsHead(thirdNodeAdded);

        assertEquals(firstNodeAdded, secondNodeAdded.getNext());
        assertEquals(thirdNodeAdded, secondNodeAdded.getPrev());

        dll.remove(secondNodeAdded);
        assertNull("expected removed node's previous pointer to be NULL", secondNodeAdded.getPrev());
        assertNull("expected removed node's next pointer to be NULL", secondNodeAdded.getNext());
    }


    private void checkThatListIsConsistent(DoublyLinkedList<TestNodeType> dll) {
        checkThatListIsConsistent(dll, null, null, null);
    }

    private void checkThatListIsConsistent(DoublyLinkedList<TestNodeType> dll, DoublyLinkedList.Node<TestNodeType> expectedHead,
                                           DoublyLinkedList.Node<TestNodeType> expectedTail, List<TestNodeType> expectedElementsInForwardOrder) {
        if (dll.getSize() == 0
                || (expectedElementsInForwardOrder != null && expectedElementsInForwardOrder.isEmpty())) {
            assertEquals(0, dll.getSize());
            assertNull(dll.head);
            assertNull(dll.tail);
        } else {
            //list should have a non-null tail and head that equal the provided values if not-null
            assertNotNull(dll.head);
            if (expectedHead != null) {
                assertEquals(expectedHead, dll.head);
            }

            assertNotNull(dll.tail);
            if (expectedTail != null) {
                assertEquals(expectedTail, dll.tail);
            }

            // head and tail should have null prev and next pointers respectively
            assertNull(dll.head.getPrev());
            assertNull(dll.tail.getNext());

            // we should be able to successfully traverse list end to end, both forwards and backwards,
            // and nodes should match up
            if (expectedElementsInForwardOrder != null) {
                assertEquals(expectedElementsInForwardOrder, dll.traverse());
            }

            if (expectedElementsInForwardOrder != null) {
                List<TestNodeType> expectedElementsInReverseOrder = new ArrayList<>();
                for (int i = expectedElementsInForwardOrder.size() - 1; i >= 0; i--) {
                    expectedElementsInReverseOrder.add(expectedElementsInForwardOrder.get(i));
                }
                assertEquals(expectedElementsInReverseOrder, dll.traverseBackward());
            }
        }
    }

    private static class TestNodeType implements Cacheable {
        int data;

        public TestNodeType(int data) {
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

            TestNodeType nodeType = (TestNodeType) o;

            return data == nodeType.data;
        }

        @Override
        public int hashCode() {
            return data;
        }
    }
}
