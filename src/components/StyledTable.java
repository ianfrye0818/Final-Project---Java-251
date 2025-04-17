package components;

import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Interfaces.ITableData;

import java.awt.*;

public abstract class StyledTable<T> extends JTable implements ITableData<T> {
  private final DefaultTableModel model;
  // Default values
  private static final int DEFAULT_ROW_HEIGHT = 35;
  private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 16);
  private static final Font DEFAULT_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
  private static final boolean DEFAULT_IS_EDITABLE = false;
  protected List<T> data;

  public StyledTable(List<T> data, List<String> columnNames) {
    this(data, columnNames, DEFAULT_IS_EDITABLE, DEFAULT_ROW_HEIGHT, DEFAULT_FONT, ListSelectionModel.SINGLE_SELECTION);
  }

  public StyledTable(List<T> data, List<String> columnNames, boolean isEditable) {
    this(data, columnNames, isEditable, DEFAULT_ROW_HEIGHT, DEFAULT_FONT, ListSelectionModel.SINGLE_SELECTION);
  }

  public StyledTable(List<T> data, List<String> columnNames, boolean isEditable,
      int rowHeight, Font font, int selectionModel) {
    // Create model
    this.model = new DefaultTableModel(columnNames.toArray(), 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return isEditable;
      }
    };
    this.setModel(model);

    // Apply default styling
    setupDefaultStyling(rowHeight, font, selectionModel);
  }

  private void setupDefaultStyling(int rowHeight, Font font, int selectionModel) {
    // Basic table settings
    setRowHeight(rowHeight);
    setFont(font);
    setSelectionMode(selectionModel);

    // Header styling
    getTableHeader().setFont(DEFAULT_HEADER_FONT);
    getTableHeader().setReorderingAllowed(false);

    // Default background colors
    setBackground(Color.WHITE);
    getTableHeader().setBackground(new Color(245, 245, 245));

    // Selection colors
    setSelectionBackground(new Color(79, 55, 48, 50));
    setSelectionForeground(Color.BLACK);
  }

  // Method to center align specific columns
  public void setCenterAlignedColumns(int... columns) {
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);

    for (int column : columns) {
      if (column < getColumnCount()) {
        getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
      }
    }
  }

  // Method to right align specific columns
  public void setRightAlignedColumns(int... columns) {
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

    for (int column : columns) {
      if (column < getColumnCount()) {
        getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
      }
    }
  }

  // Method to set custom column widths
  public void setColumnWidths(int... widths) {
    for (int i = 0; i < widths.length && i < getColumnCount(); i++) {
      getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
    }
  }

  // Method to add a row of data
  public void addRow(Object[] rowData) {
    model.addRow(rowData);
  }

  // Method to clear all rows
  public void clearRows() {
    model.setRowCount(0);
  }

  @Override
  public abstract void loadData();

  @Override
  public T getSelectedItem() {
    int selectedRow = getSelectedRow();
    return selectedRow != -1 && data != null ? data.get(selectedRow) : null;
  }
}
