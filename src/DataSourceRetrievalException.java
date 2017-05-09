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
