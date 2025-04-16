package exceptions;

import java.sql.SQLException;

public class SqlSaveException extends RuntimeException {
    private SQLException sqlException;

    public SqlSaveException(String message) {
        super(message);
    }

    public SqlSaveException() {
        super("There was an error saving this object in the database");
    }

    public SqlSaveException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public SQLException getSqlException() {
        return sqlException;
    }
}
