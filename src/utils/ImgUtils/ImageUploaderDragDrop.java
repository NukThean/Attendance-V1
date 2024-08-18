package utils.ImgUtils;

import javax.swing.*;
import javax.swing.filechooser.FileView;
import utils.DatabaseConnection;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class ImageUploaderDragDrop {

    public static class FileDropHandler extends DropTargetAdapter {
        @Override
        public void drop(DropTargetDropEvent e) {
            try {
                int id = 1;
                e.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable transferable = e.getTransferable();
                if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    java.util.List<?> files = (java.util.List<?>) transferable
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (!files.isEmpty()) {
                        File file = (File) files.get(0);
                        uploadImage(file.getAbsolutePath(), id);
                    }
                }
                e.dropComplete(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                e.rejectDrop();
            }
        }

        public static void uploadImage(String imagePath, int id) {
            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                conn = DatabaseConnection.getConnection();
                String sql = "UPDATE Employees SET Emp_Img = ? WHERE Employee_id = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(2, id); // Assuming Employee ID is 1 for this example

                File file = new File(imagePath);
                FileInputStream fis = new FileInputStream(file);
                pstmt.setBinaryStream(1, fis, (int) file.length());

                int rowAffected = pstmt.executeUpdate();
                if (rowAffected > 0) {
                    System.out.println("Image uploaded successfully!");
                }

            } catch (SQLException | IOException se) {
                se.printStackTrace();
            } finally {
                try {
                    if (pstmt != null)
                        pstmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
        }
    }

    public static File showFileChooser(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");

        // Set a custom file view
        fileChooser.setFileView(new FileView() {
            @Override
            public Icon getIcon(File f) {
                if (f.isDirectory()) {
                    return new ImageIcon("path_to_custom_folder_icon.png"); // Update the path to
                                                                            // your custom folder
                                                                            // icon
                } else {
                    return new ImageIcon("path_to_custom_file_icon.png"); // Update the path to your
                                                                          // custom file icon
                }
            }
        });

        // Optionally, customize file filter
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg")
                        || f.getName().toLowerCase().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "Image Files (*.jpg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drag and Drop Image Uploader");
            frame.setSize(500, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            int id = 1;
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createTitledBorder("Drag and Drop Image Here"));
            panel.setLayout(new BorderLayout());
            panel.setDropTarget(new DropTarget(panel, new FileDropHandler()));

            // Add a button to open a file chooser
            JButton selectFileButton = new JButton("Select Image...");
            selectFileButton.addActionListener(e -> {
                File selectedFile = showFileChooser(frame);
                if (selectedFile != null) {
                    new FileDropHandler().uploadImage(selectedFile.getAbsolutePath(), id);
                }
            });

            panel.add(selectFileButton, BorderLayout.SOUTH);

            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
