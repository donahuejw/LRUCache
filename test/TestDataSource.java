import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class TestDataSource implements DataSource<TestType> {
    int queryCount = 0;
    Map<String, TestType> valuesToReturn = new HashMap<>();

    public TestDataSource() {}

    void addDataItem(String cacheID, TestType dataItem) {
        valuesToReturn.put(cacheID, dataItem);
    }

    public int getQueryCount() {
        return queryCount;
    }

    @Override
    public Optional getFromDataSource(String ID) throws DataSourceRetrievalException {
        ++queryCount;
        return Optional.ofNullable(valuesToReturn.get(ID));
    }
}
