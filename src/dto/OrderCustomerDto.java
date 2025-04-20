package dto;

/**
 * A data transfer object (DTO) class for representing an order customer.
 * It contains the order ID, customer ID, customer name, email, and phone.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class OrderCustomerDto {
  private int orderId;
  private int customerId;
  private String customerName;
  private String customerEmail;
  private String customerPhone;

  public OrderCustomerDto(int orderId, int customerId, String customerName, String customerEmail,
      String customerPhone) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.customerName = customerName;
    this.customerEmail = customerEmail;
    this.customerPhone = customerPhone;
  }

  public int getOrderId() {
    return orderId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  @Override
  public String toString() {
    return "OrderCustomerDto{" +
        "orderId=" + orderId +
        ", customerId=" + customerId +
        ", customerName='" + customerName + '\'' +
        '}';
  }

  public static class Builder {
    private int orderId;
    private int customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    public Builder setOrderId(int orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder setCustomerName(String customerName) {
      this.customerName = customerName;
      return this;
    }

    public OrderCustomerDto build() {
      return new OrderCustomerDto(orderId, customerId, customerName, customerEmail, customerPhone);
    }

    public Builder setCustomerId(int customerId) {
      this.customerId = customerId;
      return this;
    }

    public Builder setCustomerEmail(String customerEmail) {
      this.customerEmail = customerEmail;
      return this;
    }

    public Builder setCustomerPhone(String customerPhone) {
      this.customerPhone = customerPhone;
      return this;
    }
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

}
