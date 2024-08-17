package Admindashboard;

// img class for clean code and easy calling image to use
import javax.swing.*;

public class img extends JPanel {
  private JLabel emoji1; // Declare emoji1 as a class-level variable
  private JLabel emoji2;
  private JLabel emoji3;
  private JLabel emoji4;
  private JLabel emoji5;
  private JLabel emoji6;
  private JLabel emoji7;

  public img() {
    String imagePath1 = "D:\\Attendance V1\\img\\man.png";
    ImageIcon imageIcon1 = new ImageIcon(imagePath1);
    emoji1 = new JLabel(imageIcon1); // Initialize emoji1 in the constructor

    String imagePath2 = "D:\\Attendance V1\\img\\late.png";
    ImageIcon imageIcon2 = new ImageIcon(imagePath2);
    emoji2 = new JLabel(imageIcon2); // Initialize emoji1 in the constructor

    String imagePath3 = "D:\\Attendance V1\\img\\earlyout.png";
    ImageIcon imageIcon3 = new ImageIcon(imagePath3);
    emoji3 = new JLabel(imageIcon3); // Initialize emoji1 in the constructor

    String imagePath4 = "D:\\Attendance V1\\img\\present.png";
    ImageIcon imageIcon4 = new ImageIcon(imagePath4);
    emoji4 = new JLabel(imageIcon4); // Initialize emoji1 in the constructor

    String imagePath5 = "D:\\Attendance V1\\img\\onleave.png";
    ImageIcon imageIcon5 = new ImageIcon(imagePath5);
    emoji5 = new JLabel(imageIcon5); // Initialize emoji1 in the constructor

    String imagePath6 = "D:\\Attendance V1\\img\\absence.png";
    ImageIcon imageIcon6 = new ImageIcon(imagePath6);
    emoji6 = new JLabel(imageIcon6); // Initialize emoji1 in the constructor

    String imagePath7 = "D:\\Attendance V1\\img\\graphic.png";
    ImageIcon imageIcon7 = new ImageIcon(imagePath7);
    emoji7 = new JLabel(imageIcon7); // Initialize emoji1 in the constructor
  }


  // Method to access emoji1
  public JLabel getEmoji1() {
    return emoji1;
  }

  public JLabel getEmoji2() {
    return emoji2;
  }

  public JLabel getEmoji3() {
    return emoji3;
  }

  public JLabel getEmoji4() {
    return emoji4;
  }

  public JLabel getEmoji5() {
    return emoji5;
  }

  public JLabel getEmoji6() {
    return emoji6;
  }

  public JLabel getEmoji7() {
    return emoji7;
  }
}
