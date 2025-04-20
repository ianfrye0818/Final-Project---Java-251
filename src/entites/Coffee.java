package entites;

import dto.OrderCoffeeDto;

/**
 * A class representing a coffee entity.
 * It contains the coffee ID, name, description, price, and in-stock status.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class Coffee {

    private Integer coffeeId;
    private String name;
    private String description;
    private double price;
    private boolean isInStock;

    public Coffee(Integer coffeeId, String name, String description, double price, boolean isInStock) {
        this.coffeeId = coffeeId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isInStock = isInStock;
    }

    public Coffee() {
    }

    public Integer getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(Integer coffeeId) {
        this.coffeeId = coffeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String coffeeName) {
        this.name = coffeeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String coffeeDescription) {
        this.description = coffeeDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "coffeeId=" + coffeeId +
                ", coffeeName='" + name + '\'' +
                ", coffeeDescription='" + description + '\'' +
                ", price=" + price +
                ", isInStock=" + isInStock +
                '}';
    }

    public static class Builder {
        private Integer coffeeId;
        private String name;
        private String description;
        private double price;
        private boolean isInStock;

        public Builder setCoffeeId(Integer coffeeId) {
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

        public Builder setInStock(boolean isInStock) {
            this.isInStock = isInStock;
            return this;
        }

        public Coffee build() {
            return new Coffee(coffeeId, name, description, price, isInStock);
        }
    }

    public OrderCoffeeDto toOrderCoffeeDto(int orderId) {
        return new OrderCoffeeDto.Builder()
                .setOrderId(orderId)
                .setCoffeeId(this.coffeeId)
                .setCoffeeName(this.name)
                .setPrice(this.price)
                .build();
    }

}