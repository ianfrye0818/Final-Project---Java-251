package dto;

import entites.Coffee;

public class UpdateCoffeeDto extends CreateCoffeeDto {
  private final int coffeeId;

  public UpdateCoffeeDto(int coffeeId, String name, String description, double price, boolean inStock) {
    super(name, description, price, inStock);
    this.coffeeId = coffeeId;
  }

  public int getCoffeeId() {
    return coffeeId;
  }

  public static class Builder {
    private int coffeeId;
    private String name;
    private String description;
    private double price;
    private boolean inStock;

    public Builder setCoffeeId(int coffeeId) {
      this.coffeeId = coffeeId;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setPrice(double price) {
      this.price = price;
      return this;
    }

    public Builder setInStock(boolean inStock) {
      this.inStock = inStock;
      return this;
    }

    public UpdateCoffeeDto build() {
      return new UpdateCoffeeDto(coffeeId, name, description, price, inStock);
    }
  }

  public Coffee toCoffee() {
    return new Coffee.Builder()
        .setCoffeeId(coffeeId)
        .setName(getName())
        .setDescription(getDescription())
        .setPrice(getPrice())
        .setInStock(isInStock())
        .build();
  }

  public static UpdateCoffeeDto fromCoffee(Coffee coffee) {
    return new Builder()
        .setCoffeeId(coffee.getCoffeeId())
        .setName(coffee.getName())
        .setDescription(coffee.getDescription())
        .setPrice(coffee.getPrice())
        .setInStock(coffee.getIsInStock())
        .build();
  }

}
