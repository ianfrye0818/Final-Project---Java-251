package Interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {
    void initTable() throws SQLException;

    List<T> findAll();

    T findById(int id);

    boolean save(T obj);

    boolean update(T obj);

    boolean deleteById(int id);
}
