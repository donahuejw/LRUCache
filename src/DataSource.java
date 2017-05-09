import java.util.Optional;

/**
 * simple interface to allow for 2nd-level data source to be injected at Cache construction.  2nd-level data
 * source is used to handle cache misses
 * @param <T> Type of elements being cached. Must implement the {@link Cacheable interface}
 */
public interface DataSource<T extends Cacheable> {
    /**
     * Attempts to retrieve desired data item from the underlying data source via an ID lookup.
     * @param ID the unique ID of the element to be retrieved from the underlying data source
     * @return an Optional containing either the retrieved element or null if no element
     * was found for the provided ID
     * @throws DataSourceRetrievalException if there is an unrecoverable error while
     * trying to query the underlying data source
     */
    public Optional<T> getFromDataSource(String ID) throws DataSourceRetrievalException;
}
