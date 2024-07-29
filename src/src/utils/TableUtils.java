package src.utils;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class TableUtils extends JPanel {
  private static JTable table;
  private DefaultTableModel tableModel;
  private JScrollPane scrollpane;

  public TableUtils() {

    table = getTable(tableModel);

    // Create JScrollPane with JTable
    scrollpane = getScrollPane(table);
  }

  public DefaultTableModel getTableModel(String[] column) {
    return new DefaultTableModel(column, 0);
  }

  public JScrollPane getScrollPane(JTable table) {
    JScrollPane scrollpane = new JScrollPane(table);
    scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollpane.getVerticalScrollBar().setBackground(Color.LIGHT_GRAY);
    scrollpane.getHorizontalScrollBar().setBackground(Color.LIGHT_GRAY);
    scrollpane.getViewport().setBackground(Color.WHITE);
    scrollpane.setBackground(Color.WHITE);

    // Set custom components in the corners
    scrollpane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, createCornerLabel());
    scrollpane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, createCornerLabel());

    // Customize the UI of the vertical scroll bar to hide arrow buttons
    customizeScrollBar(scrollpane.getVerticalScrollBar());
    // Customize the UI of the horizontal scroll bar to hide arrow buttons
    customizeScrollBar(scrollpane.getHorizontalScrollBar());

    return scrollpane;
  }

  private void customizeScrollBar(JScrollBar scrollBar) {
    scrollBar.setUI(new BasicScrollBarUI() {
      @Override
      protected void configureScrollBarColors() {
        this.thumbColor = new Color(11, 57, 163); // Set thumb color
      }

      @Override
      protected JButton createDecreaseButton(int orientation) {
        return createZeroButton(); // Hide the decrease button
      }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        return createZeroButton(); // Hide the increase button
      }

      private JButton createZeroButton() {
        JButton button = new JButton();
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
      }
    });
  }

  public JTable getTable(DefaultTableModel model) {
    JTable table = new JTable(model);
    table.setBackground(Color.WHITE);
    table.setForeground(Color.BLACK);
    table.setFont(new Font("Times New Roman", Font.PLAIN, 13));
    table.setGridColor(Color.BLACK);
    table.setShowGrid(true);

    // Set intercell spacing to default
    table.setIntercellSpacing(new Dimension(1, 1));
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    // Set the custom cell renderer to color rows
    table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

    // Set the custom header renderer
    JTableHeader header = table.getTableHeader();
    header.setDefaultRenderer(new CustomHeaderRenderer());
    header.setFont(new Font("Arial", Font.BOLD, 14));

    return table;
  }

  public static void adjustColumnWidth(JTable table, int columnIndex, int width) {
    TableColumnModel columnModel = table.getColumnModel();
    TableColumn column = columnModel.getColumn(columnIndex);
    column.setPreferredWidth(width);
    column.setMinWidth(width);
    column.setMaxWidth(width);
  }


  private static JLabel createCornerLabel() {
    JLabel cornerLabel = new JLabel("");
    cornerLabel.setBackground(Color.LIGHT_GRAY);
    cornerLabel.setOpaque(true);
    return cornerLabel;
  }

  private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      Component c =
          super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (!isSelected) {
        if (row % 2 == 0) { // Highlight gray and white in table
          c.setBackground(Color.LIGHT_GRAY);
          c.setForeground(Color.BLACK);
        } else {
          c.setBackground(Color.WHITE);
          c.setForeground(Color.BLACK);
        }
      } else {
        c.setBackground(Color.BLUE); // For selected row
        c.setForeground(Color.WHITE); // For selected row
      }
      return c;
    }
  }

  // Custom HeaderRenderer to set column header colors
  private static class CustomHeaderRenderer extends DefaultTableCellRenderer {
    public CustomHeaderRenderer() {
      setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      Component c =
          super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      c.setBackground(new Color(15, 41, 102));
      c.setForeground(Color.WHITE);
      return c;
    }
  }
}
