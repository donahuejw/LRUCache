/**
 * This class represents a checked Exception to be thrown when there is an unrecoverable error while attempting
 * to query the {@link DataSource} for a given ID
 */
public class DataSourceRetrievalException extends Exception {
    public DataSourceRetrievalException() {
    }

    public DataSourceRetrievalException(String message) {
        super(message);
    }

    public DataSourceRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceRetrievalException(Throwable cause) {
        super(cause);
    }

    public DataSourceRetrievalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
