/**
 * This interface must be implemented by any time to be stored in {@link LRUCache}
 */
public interface Cacheable {
    /**
     * Returns a unique ID to be used as the retrieval key for the item being cached.
     * @return a String that represents the unique ID for a specific instance
     */
    public String getID();
}
