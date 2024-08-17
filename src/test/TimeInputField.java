package test;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeInputField {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Time Input Field");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(300, 100);

      JFormattedTextField timeField = createTimeField();
      frame.add(timeField, BorderLayout.CENTER);

      frame.setVisible(true);
    });
  }

  private static JFormattedTextField createTimeField() {
    SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
    format.setLenient(false);

    // Create a formatter for the JFormattedTextField
    DateFormatter dateFormatter = new DateFormatter(format);
    JFormattedTextField timeField = new JFormattedTextField(dateFormatter);
    timeField.setColumns(10);
    timeField.setFocusLostBehavior(JFormattedTextField.PERSIST);

    // Set up the DocumentFilter to ensure the colon is non-editable
    ((AbstractDocument) timeField.getDocument()).setDocumentFilter(new TimeDocumentFilter());

    // Add an ActionListener to handle the toggle between AM and PM
    timeField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          String text = timeField.getText();
          if (text != null && !text.isEmpty()) {
            Date date = format.parse(text);
            String ampm = text.endsWith("AM") ? "PM" : "AM";
            timeField.setText(format.format(date).replaceAll("AM|PM", ampm));
          }
        } catch (ParseException ex) {
          ex.printStackTrace();
        }
      }
    });

    return timeField;
  }

  // Custom DocumentFilter to ensure the colon is non-editable
  static class TimeDocumentFilter extends DocumentFilter {
    private static final String COLON = ":";

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
        throws BadLocationException {
      if (offset == 2 && !string.equals(COLON)) {
        return; // Do not allow insertion at the colon position
      }
      super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
        throws BadLocationException {
      if (offset == 2 && !text.equals(COLON)) {
        return; // Do not allow replacement at the colon position
      }
      super.replace(fb, offset, length, text, attrs);
    }
  }

  // Custom DateFormatter to use with JFormattedTextField
  static class DateFormatter extends JFormattedTextField.AbstractFormatter {
    private final SimpleDateFormat format;

    DateFormatter(SimpleDateFormat format) {
      this.format = format;
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
      return text == null || text.isEmpty() ? null : format.parse(text);
    }

    @Override
    public String valueToString(Object value) {
      return value == null ? "" : format.format((Date) value);
    }
  }
}
