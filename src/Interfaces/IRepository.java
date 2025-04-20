package Interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface for a repository that manages entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public interface IRepository<M, D> {
    void initTable() throws SQLException;

    List<M> findAll();

    M findById(int id);

    M save(D obj);

    M update(D obj);

    boolean deleteById(int id);

    void resetDatabase() throws SQLException;

    void populateDatabase() throws SQLException;
}
