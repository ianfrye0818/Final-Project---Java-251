package dto;

import models.Order;

public class OrderTableDto {
  private final Integer orderId;
  private final String customerName;
  private final String coffeeName;
  private final Double quantity;
  private final Double totalPrice;

  public OrderTableDto(Integer orderId, String customerName, String coffeeName, Double quantity, Double totalPrice) {
    this.orderId = orderId;
    this.customerName = customerName;
    this.coffeeName = coffeeName;
    this.quantity = quantity;
    this.totalPrice = totalPrice;
  }

  // create a builder for the OrderTableDto
  public static class Builder {
    private Integer orderId;
    private String customerName;
    private String coffeeName;
    private Double quantity;
    private Double totalPrice;

    public Builder setOrderId(Integer orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder setCustomerName(String customerName) {
      this.customerName = customerName;
      return this;
    }

    public Builder setCoffeeName(String coffeeName) {
      this.coffeeName = coffeeName;
      return this;
    }

    public Builder setQuantity(Double quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder setTotalPrice(Double totalPrice) {
      this.totalPrice = totalPrice;
      return this;
    }

    public OrderTableDto build() {
      return new OrderTableDto(orderId, customerName, coffeeName, quantity, totalPrice);
    }
  }

  // getters and setters
  public Integer getOrderId() {
    return orderId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public String getCoffeeName() {
    return coffeeName;
  }

  public Double getQuantity() {
    return quantity;
  }

  public Double getTotalPrice() {
    return totalPrice;
  }

  public static OrderTableDto fromOrder(Order order) {
    return new Builder()
        .setOrderId(order.getOrderId())
        .setCustomerName(order.getCustomer().getCustomerName())
        .setCoffeeName(order.getCoffee().getCoffeeName())
        .setQuantity(order.getNumberOrdered())
        .setTotalPrice(order.getTotal())
        .build();
  }

  @Override
  public String toString() {
    return "OrderTableDto [orderId=" + orderId + ", customerName=" + customerName + ", coffeeName=" + coffeeName
        + ", quantity=" + quantity + ", totalPrice=" + totalPrice + "]";
  }

}
