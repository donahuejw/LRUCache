import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LRUCacheTests {
    @Test
    public void cacheHitsDataSourceForNotYetCachedItem() throws Exception {
        TestDataSource testDataSource = new TestDataSource();
        String cacheID = "5";
        TestType dataItem = new TestType(5);
        testDataSource.addDataItem(cacheID, dataItem);
        LRUCache<TestType> cache = new LRUCache<>(testDataSource);

        assertEquals(dataItem, cache.getFromCache(cacheID).get());
        assertEquals(1, testDataSource.getQueryCount());
        assertEquals(cacheID, testDataSource.getLastRetrievedID());
    }

    @Test
    public void cacheReturnsAlreadyCachedItemWithoutHittingDataSource() throws Exception {
        TestDataSource testDataSource = new TestDataSource();
        String cacheID = "5";
        TestType dataItem = new TestType(5);
        testDataSource.addDataItem(cacheID, dataItem);
        LRUCache<TestType> cache = new LRUCache<>(testDataSource);

        assertEquals(dataItem, cache.getFromCache(cacheID).get());
        assertEquals(1, testDataSource.getQueryCount());
        assertEquals(dataItem, cache.getFromCache(cacheID).get());
        assertEquals(1, testDataSource.getQueryCount());
    }

    @Test
    public void cacheEvictsLeastRecentlyUsedItemWhenFull() throws Exception {
        TestDataSource testDataSource = new TestDataSource();
        testDataSource.addDataItem("5", new TestType(5));
        testDataSource.addDataItem("10", new TestType(10));
        testDataSource.addDataItem("15", new TestType(15));
        LRUCache<TestType> cache = new LRUCache<>(testDataSource, 2);

        // fill cache to capacity
        cache.getFromCache("5");
        cache.getFromCache("10");
        assertEquals(2, testDataSource.getQueryCount());

        // retrieving both again should result in no additional queries to data source
        cache.getFromCache("5");
        cache.getFromCache("10");
        assertEquals(2, testDataSource.getQueryCount());

        // retrieval of uncached element should result in another query and replacement of LRU element (i.e., 5)
        cache.getFromCache("15");
        assertEquals(3, testDataSource.getQueryCount());

        // now, attempt to get item 5 should result in another hit on the data source because it was evicted
        cache.getFromCache("5");
        assertEquals(4, testDataSource.getQueryCount());
        assertEquals("5", testDataSource.getLastRetrievedID());
    }

    @Test
    public void accessingCachedItemMovesItToFrontOfLRUList() throws Exception {
        TestDataSource testDataSource = new TestDataSource();
        testDataSource.addDataItem("5", new TestType(5));
        testDataSource.addDataItem("10", new TestType(10));
        testDataSource.addDataItem("15", new TestType(15));
        LRUCache<TestType> cache = new LRUCache<>(testDataSource, 2);

        // fill cache to capacity
        cache.getFromCache("5");
        cache.getFromCache("10");
        assertEquals(2, testDataSource.getQueryCount());

        // retrieving item 5 again, which should move 10 to LRU position
        cache.getFromCache("5");
        assertEquals(2, testDataSource.getQueryCount());

        // retrieval of uncached element should result in another query and replacement of LRU element (i.e., 10)
        cache.getFromCache("15");
        assertEquals(3, testDataSource.getQueryCount());

        // now, attempt to get item 10 should result in another hit on the data source because it was evicted
        cache.getFromCache("10");
        assertEquals(4, testDataSource.getQueryCount());
        assertEquals("10", testDataSource.getLastRetrievedID());
    }

    @Test
    public void accessingItemNotYetInCachePlacesItAtHeadOfLRUListToStart() throws Exception {
        TestDataSource testDataSource = new TestDataSource();
        TestType dataItem5 = new TestType(5);
        testDataSource.addDataItem("5", dataItem5);
        TestType dataItem10 = new TestType(10);
        testDataSource.addDataItem("10", dataItem10);
        TestType dataItem15 = new TestType(15);
        testDataSource.addDataItem("15", dataItem15);

        LRUCache<TestType> cache = new LRUCache<>(testDataSource, 2);

        // populate cache
        cache.getFromCache("5");
        cache.getFromCache("10");

        //force cache to evict LRU (which should be '5') in order to bring in '15'
        cache.getFromCache("15");
        assertEquals(3, testDataSource.getQueryCount());

        // at this point, LRUlist should contain 15, 10, in that order, since '15' was put at head of list
        // getting '5' again should cause a cache miss, and 10 should get evicted
        testDataSource.resetQueryCount();
        cache.getFromCache("5");
        assertEquals(1, testDataSource.getQueryCount());
        assertEquals("5", testDataSource.getLastRetrievedID());

        // we can test this is true by getting "15" again, which should not result in a cache miss
        // i.e., queryCount and lastRetrievedID stay the same
        cache.getFromCache("15");
        assertEquals(1, testDataSource.getQueryCount());
        assertEquals("5", testDataSource.getLastRetrievedID());
    }
}
