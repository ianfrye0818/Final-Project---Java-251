package services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dto.CreateCoffeeDto;
import dto.CreateCustomerDto;
import dto.CreateOrderDto;

public class ValidatorService {
  public static boolean isValidEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public static boolean isValidPhoneNumber(String phone) {
    String digitsOnly = phone.replaceAll("[^0-9]", "");
    return digitsOnly.length() == 10 && digitsOnly.matches("\\d+");
  }

  public static boolean isValidZipCode(String zip) {
    String trimmedZip = zip.trim();
    return trimmedZip.length() == 5 && trimmedZip.matches("\\d+");
  }

  public static boolean isValidNotEmpty(String... values) {
    if (values == null)
      return false;
    for (String value : values) {
      if (value == null || value.trim().isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public static boolean isValidNotZero(Double... values) {
    if (values == null)
      return false;
    for (Double value : values) {
      if (value == null || value <= 0) {
        return false;
      }
    }
    return true;
  }

  public static boolean isValidNotNull(Object... objects) {
    if (objects == null)
      return false;
    for (Object obj : objects) {
      if (obj == null) {
        return false;
      }
    }
    return true;
  }

  public static boolean isValidCustomer(CreateCustomerDto customer) {
    return customer != null
        && isValidNotEmpty(
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getPhone(),
            customer.getStreet(),
            customer.getCity(),
            customer.getState(),
            customer.getZip());
  }

  // Specific validator for Coffee
  public static boolean isValidCoffee(CreateCoffeeDto coffee) {
    return coffee != null
        && isValidNotEmpty(
            coffee.getName(),
            coffee.getDescription())
        && isValidNotZero(coffee.getPrice());
  }

  // Specific validator for Order
  public static boolean isValidOrder(CreateOrderDto order) {
    return order != null
        && isValidNotNull(order.getCustomerId(), order.getCoffeeId())
        && isValidNotZero(order.getQtyOrdered(), order.getTotal());
  }
}
