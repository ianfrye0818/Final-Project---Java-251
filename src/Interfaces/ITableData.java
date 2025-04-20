package Interfaces;

/**
 * An interface for a table data that manages a table.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public interface ITableData<T> {
  void loadData();

  T getSelectedItem();
}
