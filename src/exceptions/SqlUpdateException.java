package exceptions;

import java.sql.SQLException;

public class SqlUpdateException extends RuntimeException {
    private SQLException sqlException;

    public SqlUpdateException(String message) {
        super(message);
    }

    public SqlUpdateException() {
        super("There was an error updating this object in the database");
    }

    public SqlUpdateException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }

    public SQLException getSqlException() {
        return sqlException;
    }

}
