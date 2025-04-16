package services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

  public static <T> boolean isValidNotEmpty(T obj) {
    if (obj == null) {
      return false;
    }

    try {
      for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        Object value = field.get(obj);
        if (value == null) {
          return false;
        }
        if (value instanceof String && ((String) value).trim().isEmpty()) {
          return false;
        }
      }
      return true;
    } catch (IllegalAccessException e) {
      System.out.println("Error accessing fields: " + e.getMessage());
      return false;
    }
  }

}
