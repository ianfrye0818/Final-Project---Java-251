package dto;

public class OrderCoffeeDto {
  private int orderId;
  private int coffeeId;
  private String coffeeName;
  private double price;

  public OrderCoffeeDto(int orderId, int coffeeId, String coffeeName, double price) {
    this.orderId = orderId;
    this.coffeeId = coffeeId;
    this.coffeeName = coffeeName;
    this.price = price;
  }

  public int getOrderId() {
    return orderId;
  }

  public String getCoffeeName() {
    return coffeeName;
  }

  public double getPrice() {
    return price;
  }

  public int getCoffeeId() {
    return coffeeId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  public void setCoffeeId(int coffeeId) {
    this.coffeeId = coffeeId;
  }

  public void setCoffeeName(String coffeeName) {
    this.coffeeName = coffeeName;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "OrderCoffeeDto{" +
        "orderId=" + orderId +
        ", coffeeName='" + coffeeName + '\'' +
        ", price=" + price +
        '}';
  }

  public static class Builder {
    private int orderId;
    private int coffeeId;
    private String coffeeName;
    private double price;

    public Builder setOrderId(int orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder setCoffeeId(int coffeeId) {
      this.coffeeId = coffeeId;
      return this;
    }

    public Builder setCoffeeName(String coffeeName) {
      this.coffeeName = coffeeName;
      return this;
    }

    public Builder setPrice(double price) {
      this.price = price;
      return this;
    }

    public OrderCoffeeDto build() {
      return new OrderCoffeeDto(orderId, coffeeId, coffeeName, price);
    }
  }
}
