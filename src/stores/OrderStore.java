package stores;

import Interfaces.IStore;
import dto.OrderCoffeeDto;
import dto.OrderCustomerDto;
import models.Order;

public class OrderStore implements IStore<Order> {
    private Order currentOrder = new Order.Builder()
            .setCustomer(new OrderCustomerDto.Builder()
                    .setCustomerName("John Doe")
                    .setCustomerId(1)
                    .setCustomerEmail("john.doe@example.com")
                    .setCustomerPhone("123-456-7890")
                    .build())
            .setCoffee(new OrderCoffeeDto.Builder().setCoffeeId(22).setCoffeeName("Espresso").setPrice(4.99).build())
            .setNumberOrdered(4).build();

    @Override
    public void set(Order order) {
        this.currentOrder = order;
    }

    @Override
    public void clear() {
        this.currentOrder = null;
    }

    @Override
    public Order get() {
        return this.currentOrder;
    }
}
