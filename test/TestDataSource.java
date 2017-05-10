import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class TestDataSource implements DataSource<TestType> {
    private int queryCount = 0;
    private String lastRetrievedID;

    Map<String, TestType> valuesToReturn = new HashMap<>();

    public TestDataSource() {}

    void addDataItem(String cacheID, TestType dataItem) {
        valuesToReturn.put(cacheID, dataItem);
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void resetQueryCount() {
        this.queryCount = 0;
    }

    public String getLastRetrievedID() {
        return lastRetrievedID;
    }

    @Override
    public Optional getFromDataSource(String id) throws DataSourceRetrievalException {
        ++queryCount;
        lastRetrievedID = id;
        return Optional.ofNullable(valuesToReturn.get(id));
    }
}
