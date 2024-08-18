package test;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// public class CustomTextField extends JTextField {

// public CustomTextField(int columns, int width, int height, int topBorder, int leftBorder,
// int bottomBorder, int rightBorder, Color borderColor) {
// super(columns);

// // Set the preferred, minimum, and maximum sizes explicitly
// Dimension preferredSize = new Dimension(width, height);
// setPreferredSize(preferredSize);
// setMinimumSize(preferredSize);
// setMaximumSize(preferredSize);

// // Create and set a custom border
// Border border = BorderFactory.createMatteBorder(topBorder, leftBorder, bottomBorder,
// rightBorder, borderColor);
// setBorder(border);
// }
// }

public class CustomTextField extends JTextField {

    public CustomTextField(int columns, int width, int height, int top, int left, int bottom,
            int right, Color borderColor, String text) {
        super(columns);

        Dimension customSize = new Dimension(width, height);
        setPreferredSize(customSize);
        setMinimumSize(customSize);
        setMaximumSize(customSize);

        Border border = BorderFactory.createMatteBorder(top, left, bottom, right, borderColor);
        setBorder(border);

        // Set columns to 0 or a minimal value to prevent default width calculation
        setColumns(0);
        setText(text);
    }
}


