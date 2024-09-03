package Admin.emp_data;

public class Employee {
  private int id;
  private String firstName;
  private String lastName;
  private int phone;
  private String email;
  private int nid;
  private String position;
  private String department;
  private String sex;
  private String sShift;
  private String eShift;
  private float annaul_leave;
  private float sick_leave;
  private float special_leave;
  private float block_leave;

  // data for use with arraylist in empform and Info_input

  public Employee(String Emp_img, int id, String firstName, String lastName, String sex, int phone,
      String email, int nid, String position, String department, String sShift, String eShift,
      float annaul_leave, float sick_leave, float special_leave, float block_leave) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.sex = sex;
    this.phone = phone;
    this.email = email;
    this.nid = nid;
    this.position = position;
    this.department = department;
    this.sShift = sShift;
    this.eShift = eShift;
    this.annaul_leave = annaul_leave;
    this.sick_leave = sick_leave;
    this.special_leave = special_leave;
    this.block_leave = block_leave;
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

  public String getStartshift() {
    return sShift;
  }

  public String getEndshift() {
    return eShift;
  }

  public String getSex() {
    return sex;
  }

  public float getAnnaul_leave() {
    return annaul_leave;
  }

  public float getSick_leave() {
    return sick_leave;
  }

  public float getSpecial_leave() {
    return special_leave;
  }

  public float getBlock_leave() {
    return block_leave;
  }
}

