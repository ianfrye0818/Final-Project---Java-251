package entites;

public class Customer {
  private int customerId;
  private String firstName;
  private String lastName;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String email;
  private String password;
  private String phone;
  private double creditLimit;

  public Customer() {
  }

  public Customer(int customerId, String firstName, String lastName, String street, String city, String state,
      String zip, String email, String password, String phone, double creditLimit) {
    this.customerId = customerId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.creditLimit = creditLimit;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public double getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(double creditLimit) {
    this.creditLimit = creditLimit;
  }

  @Override
  public String toString() {
    return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName + ", street="
        + street + ", city=" + city + ", state=" + state + ", zip=" + zip + ", email=" + email + ", password="
        + password + ", phone=" + phone + ", creditLimit=" + creditLimit + "]";
  }

  public class Builder {
    private int customerId;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String password;
    private String phone;
    private double creditLimit;

    public Builder setCustomerId(int customerId) {
      this.customerId = customerId;
      return this;
    }

    public Builder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder setStreet(String street) {
      this.street = street;
      return this;
    }

    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    public Builder setState(String state) {
      this.state = state;
      return this;
    }

    public Builder setZip(String zip) {
      this.zip = zip;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder setPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder setCreditLimit(double creditLimit) {
      this.creditLimit = creditLimit;
      return this;
    }

    public Customer build() {
      return new Customer(customerId, firstName, lastName, street, city, state, zip, email, password, phone,
          creditLimit);
    }
  }

}
