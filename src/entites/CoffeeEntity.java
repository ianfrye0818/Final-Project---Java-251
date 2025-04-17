package entites;

public class CoffeeEntity {
  private int coffeeId;
  private String name;
  private String description;
  private double price;
  private boolean inStock;

  public CoffeeEntity() {

  }

  public CoffeeEntity(int coffeeId, String name, String description, double price, boolean inStock) {

    this.coffeeId = coffeeId;
    this.name = name;
    this.description = description;
    this.price = price;
    this.inStock = inStock;
  }

  public int getCoffeeId() {
    return coffeeId;
  }

  public void setCoffeeId(int coffeeId) {
    this.coffeeId = coffeeId;
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

  @Override
  public String toString() {
    return "CoffeeEntity [coffeeId=" + coffeeId + ", name=" + name + ", description=" + description + ", price=" + price
        + ", inStock=" + inStock + "]";
  }

  public class Builder {
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

    public CoffeeEntity build() {
      return new CoffeeEntity(coffeeId, name, description, price, inStock);
    }
  }

}
