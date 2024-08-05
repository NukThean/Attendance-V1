package src.loginpage;

public class Admin {
  private int userId;
  private String password;
  private Role role;
  private String name; // Add this field

  // Constructor
  public Admin(int userId, String password, Role role, String name) {
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
}

