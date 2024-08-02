package src.loginpage;

public class User {
  private int userId;
  private String sex;
  private String password;
  private Role role;
  private String name; // Add this field
  private static String[] department = {"HR", "Finance", "IT", "Marketing"};

  // Constructor
  public User(int userId, String password, Role role, String name) {
    this.userId = userId;
    this.password = password;
    this.role = role;
    this.name = name; // Initialize the name field
  }

  // Getters and setters
  public int getuserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public static String[] getDepartment() {
    return department;
  }


}
