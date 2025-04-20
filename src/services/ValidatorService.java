package services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dto.CreateCoffeeDto;
import dto.CreateCustomerDto;
import dto.CreateOrderDto;

/**
 * Provides static utility methods for validating various data inputs, such as
 * email addresses, phone numbers, zip codes, and ensuring fields are not empty,
 * not zero, or not null. It also includes specific validation rules for
 * {@link CreateCustomerDto}, {@link CreateCoffeeDto}, and
 * {@link CreateOrderDto} objects.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class ValidatorService {

  /**
   * Checks if the provided email address is valid based on a standard email
   * format
   * using a regular expression.
   *
   * @param email The email address to validate.
   * @return {@code true} if the email is valid, {@code false} otherwise.
   */
  public static boolean isValidEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  /**
   * Checks if the provided phone number is valid. It removes any non-digit
   * characters
   * and verifies if the remaining string has a length of 10 and consists only of
   * digits.
   *
   * @param phone The phone number to validate.
   * @return {@code true} if the phone number is valid, {@code false} otherwise.
   */
  public static boolean isValidPhoneNumber(String phone) {
    String digitsOnly = phone.replaceAll("[^0-9]", "");
    return digitsOnly.length() == 10 && digitsOnly.matches("\\d+");
  }

  /**
   * Checks if the provided zip code is valid. It trims any leading or trailing
   * whitespace and verifies if the resulting string has a length of 5 and
   * consists
   * only of digits.
   *
   * @param zip The zip code to validate.
   * @return {@code true} if the zip code is valid, {@code false} otherwise.
   */
  public static boolean isValidZipCode(String zip) {
    String trimmedZip = zip.trim();
    return trimmedZip.length() == 5 && trimmedZip.matches("\\d+");
  }

  /**
   * Checks if all the provided string values are not null and not empty (after
   * trimming whitespace).
   *
   * @param values An array of strings to validate.
   * @return {@code true} if all strings are valid (not null and not empty),
   *         {@code false} otherwise.
   */
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

  /**
   * Checks if all the provided double values are not null and greater than zero.
   *
   * @param values An array of doubles to validate.
   * @return {@code true} if all doubles are valid (not null and greater than
   *         zero), {@code false} otherwise.
   */
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

  /**
   * Checks if all the provided objects are not null.
   *
   * @param objects An array of objects to validate.
   * @return {@code true} if all objects are not null, {@code false} otherwise.
   */
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

  /**
   * Validates a {@link CreateCustomerDto} object. It checks if the object itself
   * is not null and if all the required fields (first name, last name, email,
   * phone, street, city, state, zip) are not null and not empty.
   *
   * @param customer The {@link CreateCustomerDto} object to validate.
   * @return {@code true} if the customer data is valid, {@code false} otherwise.
   */
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

  /**
   * Validates a {@link CreateCoffeeDto} object. It checks if the object itself
   * is not null and if the name and description are not null and not empty, and
   * if the price is not null and greater than zero.
   *
   * @param coffee The {@link CreateCoffeeDto} object to validate.
   * @return {@code true} if the coffee data is valid, {@code false} otherwise.
   */
  public static boolean isValidCoffee(CreateCoffeeDto coffee) {
    return coffee != null
        && isValidNotEmpty(coffee.getName(), coffee.getDescription())
        && isValidNotZero(coffee.getPrice());
  }

  /**
   * Validates a {@link CreateOrderDto} object. It checks if the object itself
   * is not null and if the customer ID and coffee ID are not null, and if the
   * quantity ordered and total are not null and greater than zero.
   *
   * @param order The {@link CreateOrderDto} object to validate.
   * @return {@code true} if the order data is valid, {@code false} otherwise.
   */
  public static boolean isValidOrder(CreateOrderDto order) {
    return order != null
        && isValidNotNull(order.getCustomerId(), order.getCoffeeId())
        && isValidNotZero(order.getQtyOrdered(), order.getTotal());
  }
}