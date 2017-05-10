import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Simple implementation of an in-memory cache that uses a least-recently-used eviction policy once
 * it has reached its maximum capacity
 * @param <T> concrete type of elements to be stored in the Cache.  Type must implement Cacheable interface
 *           to ensure they have a unique ID to use as lookup key for the cache (and backing data source)
 */
public class LRUCache<T extends Cacheable> {
    private Map<String, DoublyLinkedList.Node<T>> cache;
    private DoublyLinkedList<T> lruList;
    private DataSource<T> dataSource;
    private int maxCapacity;

    private static final int DEFAULT_MAX_CAPACITY = 1000;

    /**
     * Constructs a new LRUCache with the specified backing data source and capacity
     * @param dataSource the backing data source to be queried in order to satisfy a {@link #getFromCache(String)}
     *                   request when a cache miss occurs
     * @param maxCapacity maximum capacity of the cache, which when reached will result in the least-recently-used
     *                    item in the cache being evicted the next time a new item needs to be added
     */
    public LRUCache(DataSource<T> dataSource, int maxCapacity) {
        this.dataSource = dataSource;
        this.maxCapacity = maxCapacity;
        cache = new HashMap<>(2*maxCapacity-1);
        lruList = new DoublyLinkedList<>();
    }

    /**
     * Constructs a new LRUCache with the specified backing data source and the default maximum capacity of 1000
     * @param dataSource the backing data source to be queried in order to satisfy a {@link #getFromCache(String)}
     *                   request when a cache miss occurs
     */
    public LRUCache(DataSource<T> dataSource) {
        this(dataSource, DEFAULT_MAX_CAPACITY);
    }

    /**
     * Retrieves the item mapped to by the specified cacheID from the cache and returns it. If the item is not
     * currently in the cache then an attempt is made to retrieve it from the backing data source specified in
     * the constructor
     *
     * @param cachedID unique ID of the item to be retrieved from the cache or, if not in the cache,
     *                 the underlying data source
     * @return an Optional containing the retrieved item or, if it is not in the cache and cannot be
     * retrieved from the backing data source, then NULL
     * @throws DataSourceRetrievalException if the desired item is not currently in the cache and
     * an unrecoverable error occurs while trying to retrieve it from the backing data source
     */
    public synchronized Optional<T> getFromCache(String cachedID) throws DataSourceRetrievalException {
        DoublyLinkedList.Node<T> result = cache.get(cachedID);

        if (result != null) {
            // Got a cache hit, so we need to remove the Node from its current location
            // in the LinkedList so that it can be placed at the head

            if (lruList.head != result) {
                //if result is already at the head of the LinkedList, no modifications would be needed
                lruList.remove(result);
            }
        } else {
            // cache miss, so retrieve from source DB and store in the cache

            Optional<T> fromDataSource = dataSource.getFromDataSource(cachedID);

            if (!fromDataSource.isPresent()) {
                return Optional.empty();
            }

            T missingData = fromDataSource.get();

            result = new DoublyLinkedList.Node<>(missingData);

            if (cache.size() == maxCapacity) {
                //evict LRU item, which is simply the tail of our list
                T oldTail = lruList.removeTail();
                cache.remove(oldTail.getID());
            }

            cache.put(cachedID, result);
        }

        // finally, before returning result set it as the new head of the
        // list, if it's not already the head, to reflect its status as MRU
        if (lruList.head != result) {
            lruList.insertAsHead(result);
        }
        return Optional.of(result.getData());
    }
}