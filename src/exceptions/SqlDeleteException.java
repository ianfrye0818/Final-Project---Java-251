package exceptions;

import java.sql.SQLException;

public class SqlDeleteException extends RuntimeException {
    private SQLException sqlException;

    public SqlDeleteException(String message) {
        super(message);
    }

    public SqlDeleteException() {
        super("There was an error deleting this object from the database");
    }

    public SqlDeleteException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
