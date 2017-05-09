import java.util.HashMap;
import java.util.Map;

public class LRUCache<T extends Cacheable> {
    private Map<String, DoublyLinkedList.Node<T>> cache;
    private DoublyLinkedList<T> lruList;
    private DataSource<T> dataSource;
    private int maxCapacity;

    private static final int DEFAULT_MAX_CAPACITY = 1000;

    public LRUCache(DataSource<T> dataSource, int maxCapacity) {
        this.dataSource = dataSource;
        this.maxCapacity = maxCapacity;
        cache = new HashMap<>(2*maxCapacity-1);
        lruList = new DoublyLinkedList<>();
    }

    public LRUCache(DataSource<T> dataSource) {
        this(dataSource, DEFAULT_MAX_CAPACITY);
    }

    public synchronized T getFromCache(String uid) throws DataSourceRetrievalException {
        DoublyLinkedList.Node<T> result = cache.get(uid);

        if (result != null) {
            // Got a cache hit, so we need to remove the Node from its current location
            // in the LinkedList so that it can be placed at the head

            if (lruList.head != result) {
                //if result is already at the head of the LinkedList, no modifications would be needed
                lruList.remove(result);
            }
        } else {
            // cache miss, so retrieve from source DB and store in the cache

            T missingData = dataSource.getFromDataSource(uid);

            result = new DoublyLinkedList.Node<>(missingData);

            if (cache.size() == maxCapacity) {
                //evict LRU item, which is simply the tail of our list
                T oldTail = lruList.removeTail();
                cache.remove(oldTail.getID());
            }

            cache.put(uid, result);
        }

        // finally, before returning result set it as the new head of the
        // list, if it's not already the head, to reflect its status as MRU
        if (lruList.head != result) {
            lruList.insertAsHead(result);
        }
        return result.getData();
    }
}