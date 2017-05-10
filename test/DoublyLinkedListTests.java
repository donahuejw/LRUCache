import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DoublyLinkedListTests {
    @Test
    public void insertNewHeadIntoEmptyListCreatesListWithSingleElementAsHeadAndTailTest() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> newHead = new DoublyLinkedList.Node<>(new TestType(5));

        assertEquals(0, dll.getSize());
        assertNull(dll.head);
        assertNull(dll.tail);

        dll.insertAsHead(newHead);

        checkThatListIsConsistent(dll, newHead, newHead, Lists.newArrayList(new TestType(5)));
    }

    @Test
    public void insertNewHeadIntoExistingListPutsNewElementAsHeadOfList() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> startingHead = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(startingHead);

        assertEquals(1, dll.getSize());
        assertEquals(startingHead, dll.head);
        assertEquals(startingHead, dll.tail);

        DoublyLinkedList.Node<TestType> newHead = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(newHead);

        checkThatListIsConsistent(dll, newHead, startingHead, Lists.newArrayList(new TestType(10), new TestType(5)));
    }

    @Test
    public void insertExistingHeadAsNewHeadOfListLeavesListUnchanged() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(secondNodeAdded);

        ArrayList<TestType> expectedElements = Lists.newArrayList(new TestType(10), new TestType(5));

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
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> onlyAddedNode = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(onlyAddedNode);

        assertEquals(1, dll.getSize());
        assertEquals(onlyAddedNode, dll.head);
        assertEquals(onlyAddedNode, dll.tail);

        TestType tailNodeData = dll.removeTail();

        assertEquals(tailNodeData, onlyAddedNode.getData());
        checkThatListIsConsistent(dll);
    }

    @Test
    public void removeTailOnTwoElementListReturnsExpectedElementAndProperlySetsUpNewTail() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(secondNodeAdded);

        ArrayList<TestType> expectedElements = Lists.newArrayList(new TestType(10), new TestType(5));
        checkThatListIsConsistent(dll, secondNodeAdded, firstNodeAdded, expectedElements);

        TestType removedTail = dll.removeTail();
        assertEquals(removedTail, firstNodeAdded.getData());
        checkThatListIsConsistent(dll, secondNodeAdded, secondNodeAdded, Lists.newArrayList(new TestType(10)));
    }

    @Test
    public void removeTailOnMoreThanTwoElementListReturnsExpectedElementAndProperlySetsUpNewTail() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(secondNodeAdded);
        DoublyLinkedList.Node<TestType> thirdNodeAdded = new DoublyLinkedList.Node<>(new TestType(15));
        dll.insertAsHead(thirdNodeAdded);

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(new TestType(15), new TestType(10), new TestType(5)));

        TestType removedTail = dll.removeTail();
        assertEquals(removedTail, firstNodeAdded.getData());

        checkThatListIsConsistent(dll, thirdNodeAdded, secondNodeAdded, Lists.newArrayList(new TestType(15), new TestType(10)));
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
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> onlyAddedNode = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(onlyAddedNode);

        assertEquals(1, dll.getSize());
        assertEquals(onlyAddedNode, dll.head);
        assertEquals(onlyAddedNode, dll.tail);

        assertEquals(onlyAddedNode.getData(), dll.removeHead());
        checkThatListIsConsistent(dll, null, null, Collections.emptyList());
    }

    @Test
    public void removeHeadOnTwoOrMoreElementListReturnsExpectedElementAndProperlySetsUpNewHead() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(secondNodeAdded);
        DoublyLinkedList.Node<TestType> thirdNodeAdded = new DoublyLinkedList.Node<>(new TestType(15));
        dll.insertAsHead(thirdNodeAdded);

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(new TestType(15), new TestType(10), new TestType(5)));

        assertEquals(dll.removeHead(), thirdNodeAdded.getData());

        checkThatListIsConsistent(dll, secondNodeAdded, firstNodeAdded, Lists.newArrayList(new TestType(10), new TestType(5)));
    }

    @Test
    public void removeReturnsTrueIfElementIsInList() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(secondNodeAdded);

        assertEquals(firstNodeAdded, dll.tail);
        assertTrue(dll.remove(firstNodeAdded));

        checkThatListIsConsistent(dll);
    }

    @Test
    public void removeReturnsFalseIfElementIsNotInList() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);

        DoublyLinkedList.Node<TestType> missingNode = new DoublyLinkedList.Node<>(new TestType(15));
        DoublyLinkedList.Node<TestType> current = dll.head;

        // make sure node is not in list
        while (current != null) {
            assertNotEquals(missingNode, current);
            current = current.getNext();
        }

        assertFalse(dll.remove(missingNode));
    }

    @Test
    public void removeResultsInEmptyListIfArgIsOnlyMemberOfList() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> onlyAddedNode = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(onlyAddedNode);

        assertEquals(1, dll.getSize());
        assertEquals(onlyAddedNode, dll.head);
        assertEquals(onlyAddedNode, dll.tail);

        assertTrue(dll.remove(onlyAddedNode));

        checkThatListIsConsistent(dll, null, null, Collections.emptyList());
    }

    @Test
    public void removeThrowsIllegalArgExceptionOnNullArg() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
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
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(secondNodeAdded);

        assertEquals(2, dll.getSize());
        assertEquals(secondNodeAdded, dll.head);
        assertEquals(firstNodeAdded, dll.tail);

        assertTrue(dll.remove(secondNodeAdded));

        checkThatListIsConsistent(dll, firstNodeAdded, firstNodeAdded, null);
    }

    @Test
    public void removeSetsUpNewTailIfCurrentTailIsPassedAsArg() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
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
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        TestType firstElem = new TestType(5);
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(firstElem);
        dll.insertAsHead(firstNodeAdded);
        TestType secondElem = new TestType(10);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(secondElem);
        dll.insertAsHead(secondNodeAdded);
        TestType thirdElem = new TestType(15);
        DoublyLinkedList.Node<TestType> thirdNodeAdded = new DoublyLinkedList.Node<>(thirdElem);
        dll.insertAsHead(thirdNodeAdded);

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(thirdElem, secondElem, firstElem));

        assertTrue(dll.remove(secondNodeAdded));

        checkThatListIsConsistent(dll, thirdNodeAdded, firstNodeAdded, Lists.newArrayList(thirdElem, firstElem));
    }

    @Test
    public void removeClearsPrevAndNextPointersOnRemovedNode() throws Exception {
        DoublyLinkedList<TestType> dll = new DoublyLinkedList<>();
        DoublyLinkedList.Node<TestType> firstNodeAdded = new DoublyLinkedList.Node<>(new TestType(5));
        dll.insertAsHead(firstNodeAdded);
        DoublyLinkedList.Node<TestType> secondNodeAdded = new DoublyLinkedList.Node<>(new TestType(10));
        dll.insertAsHead(secondNodeAdded);
        DoublyLinkedList.Node<TestType> thirdNodeAdded = new DoublyLinkedList.Node<>(new TestType(15));
        dll.insertAsHead(thirdNodeAdded);

        assertEquals(firstNodeAdded, secondNodeAdded.getNext());
        assertEquals(thirdNodeAdded, secondNodeAdded.getPrev());

        dll.remove(secondNodeAdded);
        assertNull("expected removed node's previous pointer to be NULL", secondNodeAdded.getPrev());
        assertNull("expected removed node's next pointer to be NULL", secondNodeAdded.getNext());
    }


    private void checkThatListIsConsistent(DoublyLinkedList<TestType> dll) {
        checkThatListIsConsistent(dll, null, null, null);
    }

    private void checkThatListIsConsistent(DoublyLinkedList<TestType> dll, DoublyLinkedList.Node<TestType> expectedHead,
                                           DoublyLinkedList.Node<TestType> expectedTail, List<TestType> expectedElementsInForwardOrder) {
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
                List<TestType> expectedElementsInReverseOrder = new ArrayList<>();
                for (int i = expectedElementsInForwardOrder.size() - 1; i >= 0; i--) {
                    expectedElementsInReverseOrder.add(expectedElementsInForwardOrder.get(i));
                }
                assertEquals(expectedElementsInReverseOrder, dll.traverseBackward());
            }
        }
    }
}
