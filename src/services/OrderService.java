package services;

import controllers.AppController;
import models.Order;

import java.util.List;

public class OrderService {

    private final AppController controller;

    public OrderService(AppController controller) {
        this.controller = controller;
    }

    public List<Order> getAllOrders() {
        return this.controller.getOrderRepository().findAll();
    }

    public Order getOrderById(int id) {
        return this.controller.getOrderRepository().findById(id);
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return this.controller.getOrderRepository().findByCustomerId(customerId);
    }

    public boolean createOrder(Order order) {
        return this.controller.getOrderRepository().save(order);
    }

    public boolean updateOrder(Order order) {
        return this.controller.getOrderRepository().save(order);

    }

    public boolean deleteOrder(int id) {
        return this.controller.getOrderRepository().deleteById(id);
    }
}
