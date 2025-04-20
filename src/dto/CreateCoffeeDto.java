package dto;

import entites.Coffee;

/**
 * A data transfer object (DTO) class for creating a new coffee.
 * It contains the name, description, price, and in-stock status of a coffee.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CreateCoffeeDto {
  private String name;
  private String description;
  private double price;
  private boolean inStock;

  public CreateCoffeeDto(String name, String description, double price, boolean inStock) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.inStock = inStock;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public boolean isInStock() {
    return inStock;
  }

  public void setInStock(boolean inStock) {
    this.inStock = inStock;
  }

  public static class Builder {
    private String name;
    private String description;
    private double price;
    private boolean inStock;

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

    public CreateCoffeeDto build() {
      return new CreateCoffeeDto(name, description, price, inStock);
    }
  }

  @Override
  public String toString() {
    return "CreateCoffeeDto{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", inStock=" + inStock +
        '}';
  }

  public Coffee toCoffee(Integer coffeeId) {
    return new Coffee.Builder()
        .setCoffeeId(coffeeId)
        .setName(name)
        .setDescription(description)
        .setPrice(price)
        .setInStock(inStock)
        .build();
  }
}
