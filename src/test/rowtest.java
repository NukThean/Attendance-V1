package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class rowtest {
  public static void main(String[] args) {
    JFrame frame = new JFrame("Clickable Table Rows");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    String[] columnNames = {"Column 1", "Column 2", "Column 3"};
    Object[][] data = {{"row1", "Data 2", "Data 3"}, {"row2", "Data 4", "Data 5"}};

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model);

    // Add a mouse listener to the table
    table.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.getSelectedRow();
        if (row >= 0) {
          // Get the row ID from the first column
          String rowId = (String) model.getValueAt(row, 0);

          // Navigate to the corresponding page based on the row ID
          if (rowId.equals("row1")) {
            // Open page1.html (replace with your desired action)
            System.out.println("Clicked row 1: Navigating to page1.html");
          } else if (rowId.equals("row2")) {
            // Open page2.html (replace with your desired action)
            System.out.println("Clicked row 2: Navigating to page2.html");
          }
          // ... more conditions for other rows ...
        }
      }
    });

    frame.add(table);
    frame.pack();
    frame.setVisible(true);
  }
}
