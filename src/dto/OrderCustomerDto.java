package dto;

public class OrderCustomerDto {
  private int orderId;
  private int customerId;
  private String customerName;

  public OrderCustomerDto(int orderId, int customerId, String customerName) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.customerName = customerName;
  }

  public int getOrderId() {
    return orderId;
  }

  public String getCustomerName() {
    return customerName;
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

    public Builder setOrderId(int orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder setCustomerName(String customerName) {
      this.customerName = customerName;
      return this;
    }

    public OrderCustomerDto build() {
      return new OrderCustomerDto(orderId, customerId, customerName);
    }

    public Builder setCustomerId(int customerId) {
      this.customerId = customerId;
      return this;
    }
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

}
