public interface DataSource<T extends Cacheable> {
    public T getFromDataSource(String ID) throws DataSourceRetrievalException;
}
