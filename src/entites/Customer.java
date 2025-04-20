package entites;

import dto.OrderCustomerDto;

/**
 * A class representing a customer entity.
 * It contains the customer ID, first name, last name, street, city, state,
 * zip, email, phone, and credit limit.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class Customer {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String phone;
    private double creditLimit;

    public Customer(Integer customerId, String firstName, String lastName, String street, String city, String state,
            String zip, String email, String phone, double creditLimit) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.email = email;
        this.phone = phone;
        this.creditLimit = creditLimit;
    }

    public Customer() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
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

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", creditLimit=" + creditLimit +
                '}';
    }

    public static class Builder {
        private Integer customerId;
        private String firstName;
        private String lastName;
        private String street;
        private String city;
        private String state;
        private String zip;
        private String email;
        private String phone;
        private double creditLimit;

        public Builder setCustomerId(Integer customerId) {
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

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setCreditLimit(double creditLimit) {
            this.creditLimit = creditLimit;
            return this;
        }

        public Customer build() {
            return new Customer(customerId, firstName, lastName, street, city, state, zip, email, phone, creditLimit);
        }

        public OrderCustomerDto toOrderDto(int orderId) {
            return new OrderCustomerDto.Builder()
                    .setOrderId(orderId)
                    .setCustomerId(this.customerId)
                    .setCustomerName(this.firstName + " " + this.lastName)
                    .setCustomerEmail(this.email)
                    .setCustomerPhone(this.phone)
                    .build();
        }

    }
}