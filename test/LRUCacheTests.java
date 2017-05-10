import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
    }

    @Test
    public void cacheReturnsAlreadyCachedItemWithoutHittingDataSource() throws Exception {
        TestDataSource testDataSource = new TestDataSource();
        String cacheID = "5";
        TestType dataItem = new TestType(5);
        testDataSource.addDataItem(cacheID, dataItem);
        LRUCache<TestType> cache = new LRUCache<>(testDataSource);

        assertEquals(dataItem, cache.getFromCache(cacheID).get());
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
    }

    @Test
    public void accessingItemNotYetInCachePlacesItAtFrontOfLRUListToStart() throws Exception {
        TestDataSource testDataSource = new TestDataSource();
        TestType dataItem5 = new TestType(5);
        testDataSource.addDataItem("5", dataItem5);
        TestType dataItem10 = new TestType(10);
        testDataSource.addDataItem("10", dataItem10);
        LRUCache<TestType> cache = new LRUCache<>(testDataSource, 2);

        cache.getFromCache("5");
        assertEquals(dataItem5, cache.lruList.head.getData());
        assertEquals(dataItem5, cache.lruList.tail.getData());
        assertEquals(1, testDataSource.getQueryCount());

        cache.getFromCache("10");
        assertEquals(dataItem10, cache.lruList.head.getData());
        assertEquals(dataItem5, cache.lruList.tail.getData());
        assertEquals(2, testDataSource.getQueryCount());
    }
}
