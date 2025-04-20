package components;

import Interfaces.ITableData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * An abstract base class for creating styled JTables with common configurations
 * such as font, row height, header styling, and selection behavior.
 * It implements the {@link ITableData} interface to provide basic data handling
 * capabilities and requires subclasses to implement the {@link #loadData()}
 * method.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 *
 * @param <T> The type of the data held in the table.
 */
public abstract class StyledTable<T> extends JTable implements ITableData<T> {
    private final DefaultTableModel model;
    // Default values
    private static final int DEFAULT_ROW_HEIGHT = 35;
    private static final Font DEFAULT_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    private static final Font DEFAULT_HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final boolean DEFAULT_IS_EDITABLE = false;
    protected List<T> data;

    /**
     * Constructs a new {@code StyledTable} with the specified column names and
     * default settings
     * (not editable, default row height and font, single selection mode).
     *
     * @param columnNames A {@code List} of strings representing the table's column
     *                    headers.
     */
    public StyledTable(List<String> columnNames) {
        this(columnNames, DEFAULT_IS_EDITABLE, DEFAULT_ROW_HEIGHT, DEFAULT_FONT, ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Constructs a new {@code StyledTable} with the specified column names and
     * editability,
     * using default row height, font, and single selection mode.
     *
     * @param columnNames A {@code List} of strings representing the table's column
     *                    headers.
     * @param isEditable  A boolean indicating whether the table cells should be
     *                    editable.
     */
    public StyledTable(List<String> columnNames, boolean isEditable) {
        this(columnNames, isEditable, DEFAULT_ROW_HEIGHT, DEFAULT_FONT, ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Constructs a new {@code StyledTable} with comprehensive customization options
     * for column names,
     * editability, row height, font, and selection mode. It initializes the table
     * model and applies
     * the specified styling.
     *
     * @param columnNames    A {@code List} of strings representing the table's
     *                       column headers.
     * @param isEditable     A boolean indicating whether the table cells should be
     *                       editable.
     * @param rowHeight      The height of each row in the table.
     * @param font           The font to be used for the table cells.
     * @param selectionModel The selection mode for the table (e.g.,
     *                       {@link ListSelectionModel#SINGLE_SELECTION}).
     */
    public StyledTable(List<String> columnNames, boolean isEditable,
            int rowHeight, Font font, int selectionModel) {
        // Create model
        this.model = new DefaultTableModel(columnNames.toArray(), 0) {
            /**
             * Overrides the {@code isCellEditable} method to control whether cells can be
             * edited.
             *
             * @param row    The row index of the cell.
             * @param column The column index of the cell.
             * @return {@code true} if the cell is editable based on the table's
             *         {@code isEditable} setting;
             *         {@code false} otherwise.
             */
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable;
            }
        };
        this.setModel(model);

        // Apply default styling
        setupDefaultStyling(rowHeight, font, selectionModel);
    }

    /**
     * Applies the default styling to the table, including row height, font,
     * selection mode,
     * header font and reordering, and background/selection colors.
     *
     * @param rowHeight      The height of each row.
     * @param font           The font for the table cells.
     * @param selectionModel The selection mode for the table.
     */
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

    /**
     * Sets the horizontal alignment of the text in the specified columns to center.
     *
     * @param columns An array of integer indices representing the columns to be
     *                center-aligned.
     *                The indices are 0-based.
     */
    public void setCenterAlignedColumns(int... columns) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int column : columns) {
            if (column < getColumnCount()) {
                getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
            }
        }
    }

    /**
     * Adds a new row of data to the table.
     *
     * @param rowData An array of {@code Object} representing the data for the new
     *                row.
     *                The number of elements should match the number of columns in
     *                the table.
     */
    public void addRow(Object[] rowData) {
        model.addRow(rowData);
    }

    /**
     * Clears all rows of data from the table, keeping only the column headers.
     */
    public void clearRows() {
        model.setRowCount(0);
    }

    /**
     * Abstract method to be implemented by subclasses to load data into the table.
     * This method is responsible for fetching the data of type {@code T} and
     * populating
     * the table's model.
     */
    @Override
    public abstract void loadData();

    /**
     * Returns the currently selected item in the table.
     *
     * @return The selected item of type {@code T}, or {@code null} if no row is
     *         selected
     *         or if the data list is {@code null}.
     */
    @Override
    public T getSelectedItem() {
        int selectedRow = getSelectedRow();
        return selectedRow != -1 && data != null ? data.get(selectedRow) : null;
    }
}