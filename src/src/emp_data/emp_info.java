package src.emp_data;

public class emp_info {
  private int id;
  private String firstName;
  private String lastName;
  private int phone;
  private String email;
  private int nid;
  private String position;
  private String department;


  public emp_info(int id, String firstName, String lastName, int phone, String email, int nid,
      String position, String department) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.nid = nid;
    this.position = position;
    this.department = department;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public int getNid() {
    return nid;
  }

  public String getPosition() {
    return position;
  }

  public String getDepartment() {
    return department;
  }
}
