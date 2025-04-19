package entites;

import dto.OrderCoffeeDto;
import dto.OrderCustomerDto;

public class Order {
    private Integer orderId;
    private OrderCustomerDto customer;
    private OrderCoffeeDto coffee;
    private double total;
    private double qtyOrdered;

    public Order(Integer orderId, OrderCustomerDto customer, OrderCoffeeDto coffee, double total,
            double qtyOrdered) {
        this.orderId = orderId;
        this.customer = customer;
        this.coffee = coffee;
        this.total = total;
        this.qtyOrdered = qtyOrdered;
    }

    public Order() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderCustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(OrderCustomerDto customer) {
        this.customer = customer;
    }

    public OrderCoffeeDto getCoffee() {
        return coffee;
    }

    public void setCoffee(OrderCoffeeDto coffee) {
        this.coffee = coffee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    @Override
    public String toString() {
        return String.format("Order ID: %d, Customer Name: %s, Coffee Name: %s, Quantity Ordered: %.2f, Total: %.2f",
                orderId, customer.getCustomerName(), coffee.getCoffeeName(), qtyOrdered, total);
    }

    public static class Builder {
        private Integer orderId;
        private OrderCustomerDto customer;
        private OrderCoffeeDto coffee;
        private double total;
        private double qtyOrdered;

        public Builder setOrderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setCustomer(OrderCustomerDto customer) {
            this.customer = customer;
            return this;
        }

        public Builder setCoffee(OrderCoffeeDto coffee) {
            this.coffee = coffee;
            return this;
        }

        public Builder setTotal(double total) {
            this.total = total;
            return this;
        }

        public Builder setQtyOrdered(double qtyOrdered) {
            this.qtyOrdered = qtyOrdered;
            return this;
        }

        public Order build() {
            return new Order(orderId, customer, coffee, total, qtyOrdered);
        }

    }
}
