package Interfaces;

import java.sql.SQLException;
import java.util.List;

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
