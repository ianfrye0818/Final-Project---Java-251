package dto;

/**
 * A data transfer object (DTO) class for creating a new order.
 * It contains the quantity ordered, total price, coffee ID, and customer ID.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CreateOrderDto {
  private double qtyOrdered;
  private double total;
  private int coffeeId;
  private int customerId;

  public CreateOrderDto(double qtyOrdered, double total, int coffeeId, int customerId) {
    this.qtyOrdered = qtyOrdered;
    this.total = total;
    this.coffeeId = coffeeId;
    this.customerId = customerId;
  }

  public double getQtyOrdered() {
    return qtyOrdered;
  }

  public void setQtyOrdered(double qtyOrdered) {
    this.qtyOrdered = qtyOrdered;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public int getCoffeeId() {
    return coffeeId;
  }

  public void setCoffeeId(int coffeeId) {
    this.coffeeId = coffeeId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  @Override
  public String toString() {
    return "CreateOrderDto [qtyOrdered=" + qtyOrdered + ", total=" + total + ", coffeeId=" + coffeeId
        + ", customerId=" + customerId + "]";
  }

  public static class Builder {
    private double qtyOrdered;
    private double total;
    private int coffeeId;
    private int customerId;

    public Builder setQtyOrdered(double qtyOrdered) {
      this.qtyOrdered = qtyOrdered;
      return this;
    }

    public Builder setTotal(double total) {
      this.total = total;
      return this;
    }

    public Builder setCoffeeId(int coffeeId) {
      this.coffeeId = coffeeId;
      return this;
    }

    public Builder setCustomerId(int customerId) {
      this.customerId = customerId;
      return this;
    }

    public CreateOrderDto build() {
      return new CreateOrderDto(qtyOrdered, total, coffeeId, customerId);
    }
  }

}
