package entites;

public class OrderEntity {
  private int orderId;
  private int customerId;
  private int coffeeId;
  private int quantity;
  private double total;

  public OrderEntity() {
  }

  public OrderEntity(int orderId, int customerId, int coffeeId, int quantity, double total) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.coffeeId = coffeeId;
    this.quantity = quantity;
    this.total = total;
  }

  public int getOrderId() {
    return orderId;
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

  public int getCoffeeId() {
    return coffeeId;
  }

  public void setCoffeeId(int coffeeId) {
    this.coffeeId = coffeeId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  @Override
  public String toString() {
    return "OrderEntity [orderId=" + orderId + ", customerId=" + customerId + ", coffeeId=" + coffeeId + ", quantity="
        + quantity + ", total=" + total + "]";
  }

  public class Builder {
    private int orderId;
    private int customerId;
    private int coffeeId;
    private int quantity;
    private double total;

    public Builder setOrderId(int orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder setCustomerId(int customerId) {
      this.customerId = customerId;
      return this;
    }

    public Builder setCoffeeId(int coffeeId) {
      this.coffeeId = coffeeId;
      return this;
    }

    public Builder setQuantity(int quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder setTotal(double total) {
      this.total = total;
      return this;
    }

    public OrderEntity build() {
      return new OrderEntity(orderId, customerId, coffeeId, quantity, total);
    }
  }

}
