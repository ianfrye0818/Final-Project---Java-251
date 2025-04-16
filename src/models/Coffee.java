package models;

public class Coffee {

    private Integer coffeeId;
    private String coffeeName;
    private String coffeeDescription;
    private double price;
    private boolean isInStock;

    public Coffee(Integer coffeeId, String coffeeName, String coffeeDescription, double price, boolean isInStock) {
        this.coffeeId = coffeeId;
        this.coffeeName = coffeeName;
        this.coffeeDescription = coffeeDescription;
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

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getCoffeeDescription() {
        return coffeeDescription;
    }

    public void setCoffeeDescription(String coffeeDescription) {
        this.coffeeDescription = coffeeDescription;
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
                ", coffeeName='" + coffeeName + '\'' +
                ", coffeeDescription='" + coffeeDescription + '\'' +
                ", price=" + price +
                ", isInStock=" + isInStock +
                '}';
    }

    public static class Builder {
        private Integer coffeeId;
        private String coffeeName;
        private String coffeeDescription;
        private double price;
        private boolean isInStock;

        public Builder setCoffeeId(Integer coffeeId) {
            this.coffeeId = coffeeId;
            return this;
        }

        public Builder setCoffeeName(String coffeeName) {
            this.coffeeName = coffeeName;
            return this;
        }

        public Builder setCoffeeDescription(String coffeeDescription) {
            this.coffeeDescription = coffeeDescription;
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
            return new Coffee(coffeeId, coffeeName, coffeeDescription, price, isInStock);
        }
    }

}