package dto;

/**
 * A data transfer object (DTO) class for creating a new customer.
 * It contains the first name, last name, email, phone, street, city, state,
 * zip, and credit limit of a customer.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CreateCustomerDto {
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String street;
  private String city;
  private String state;
  private String zip;
  private double creditLimit;

  public CreateCustomerDto(String firstName, String lastName, String email, String phone, String street, String city,
      String state, String zip, double creditLimit) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.creditLimit = creditLimit;
  }

  public CreateCustomerDto() {
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public double getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(double creditLimit) {
    this.creditLimit = creditLimit;
  }

  @Override
  public String toString() {
    return "CreateCustomerDto [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phone="
        + phone + ", street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip + ", creditLimit="
        + creditLimit + "]";
  }

  public static class Builder {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String zip;
    private double creditLimit;

    public Builder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setPhone(String phone) {
      this.phone = phone;
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

    public Builder setCreditLimit(double creditLimit) {
      this.creditLimit = creditLimit;
      return this;
    }

    public CreateCustomerDto build() {
      return new CreateCustomerDto(firstName, lastName, email, phone, street, city, state, zip, creditLimit);
    }
  }

}
