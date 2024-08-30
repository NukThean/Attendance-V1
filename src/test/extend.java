package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DatabaseConnection;

public class extend {
  public extend() {}

  public static void retrieveImageAndSave(int employeeId, String outputPath) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = DatabaseConnection.getConnection();
      String sql = "SELECT Emp_Img FROM Employees WHERE Employee_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, employeeId);

      rs = pstmt.executeQuery();
      if (rs.next()) {
        Blob blob = rs.getBlob("Emp_Img");
        byte[] imageBytes = blob.getBytes(1, (int) blob.length());

        // Save the image to a file
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
          fos.write(imageBytes);
          System.out.println("Image retrieved and saved successfully!");
        }
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
  }

  public static void main(String[] args) {
    // Upload an image (optional step)
    // ImageUploaderDragDrop.FileDropHandler.uploadImage("path_to_your_image.jpg", 1);

    // Retrieve and save the image to compare
    retrieveImageAndSave(2, "D:\\image.jpg");
  }

}


