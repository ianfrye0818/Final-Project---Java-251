package exceptions;

import java.sql.SQLException;

public class SqlCreationException extends RuntimeException {
    private SQLException sqlException;

    public SqlCreationException(String message) {
        super(message);
    }

    public SqlCreationException() {
        super("There was an error creating this object in the database");
    }

    public SqlCreationException(String message, SQLException sqlException) {
        super(message);
        this.sqlException = sqlException;
    }


    public SQLException getSqlException() {
        return sqlException;
    }
}
