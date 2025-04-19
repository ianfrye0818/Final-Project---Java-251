package dto;

public class UpdateOrderDto extends CreateOrderDto {
  private final int orderId;

  public UpdateOrderDto(int orderId, int customerId, int coffeeId, double qtyOrdered, double total) {
    super(qtyOrdered, total, coffeeId, customerId);
    this.orderId = orderId;
  }

  public int getOrderId() {
    return orderId;
  }

  public static class Builder {
    private int orderId;
    private int customerId;
    private int coffeeId;
    private double qtyOrdered;
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

    public Builder setQtyOrdered(double qtyOrdered) {
      this.qtyOrdered = qtyOrdered;
      return this;
    }

    public Builder setTotal(double total) {
      this.total = total;
      return this;
    }

    public UpdateOrderDto build() {
      return new UpdateOrderDto(
          orderId,
          customerId,
          coffeeId,
          qtyOrdered,
          total);
    }
  }

}
