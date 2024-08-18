package utils.ImgUtils;

import javax.swing.*;
import utils.DatabaseConnection;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

public class ImageDisplayWithResize {

    public static ImageIcon getResizedImageIconFromDatabase(int employeeId, int maxWidth) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ImageIcon imageIcon = null;

        try {
            // Open a connection
            conn = DatabaseConnection.getConnection();

            // Create SQL Query to get the image data
            String sql = "SELECT Emp_Img FROM Employees WHERE Employee_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);

            // Execute the query
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Get the image data from the result set
                Blob blob = rs.getBlob("Emp_Img");
                byte[] imageBytes = blob.getBytes(1, (int) blob.length());

                // Convert byte array to BufferedImage
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                BufferedImage originalImage = ImageIO.read(bis);

                // Resize the image while maintaining aspect ratio
                BufferedImage resizedImage = resizeImageWithAspectRatio(originalImage, maxWidth);

                // Convert BufferedImage to ImageIcon
                imageIcon = new ImageIcon(resizedImage);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        return imageIcon;
    }

    // Method to resize BufferedImage while maintaining aspect ratio
    public static BufferedImage resizeImageWithAspectRatio(BufferedImage originalImage,
            int maxWidth) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Calculate the new height while maintaining the aspect ratio
        int newWidth = maxWidth;
        int newHeight = (int) ((double) originalHeight / originalWidth * newWidth);

        // Create a new BufferedImage with the new size
        BufferedImage resizedImage =
                new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Resized Image Display with Aspect Ratio");
            frame.setSize(100, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Retrieve and resize the image from the database
            ImageIcon imageIcon = getResizedImageIconFromDatabase(1, 50); // Employee ID and max
                                                                          // width

            // Display the image
            JLabel imageLabel = new JLabel();
            imageLabel.setIcon(imageIcon);
            frame.add(imageLabel, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
