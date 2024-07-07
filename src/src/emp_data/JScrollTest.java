package src.emp_data;

import javax.swing.*;
import java.awt.*;

public class JScrollTest {
  public static void main(String[] args) {
    // Create a new JFrame
    JFrame frame = new JFrame("JTextArea with JScrollPane Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

    // Create a JTextArea
    JTextArea textArea = new JTextArea(10, 30);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);

    // Create a JScrollPane and add the JTextArea to it
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Add the JScrollPane to the frame
    frame.add(scrollPane, BorderLayout.CENTER);

    // Set the frame's visibility to true
    frame.setVisible(true);
  }
}
