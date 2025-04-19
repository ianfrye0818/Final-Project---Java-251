package services;

import entites.Order;
import java.sql.SQLException;
import java.util.List;
import Interfaces.IOrderRepository;
import dto.CreateOrderDto;
import dto.UpdateOrderDto;

public class OrderService {

    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return this.orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return this.orderRepository.findByCustomerId(customerId);
    }

    public Order createOrder(CreateOrderDto order) {
        return this.orderRepository.save(order);
    }

    public Order updateOrder(UpdateOrderDto order) {
        return this.orderRepository.update(order);

    }

    public boolean deleteOrder(int id) {
        return this.orderRepository.deleteById(id);
    }

    public void resetDatabase() throws SQLException {
        this.orderRepository.resetDatabase();
    }

    public void populateDatabase() throws SQLException {
        this.orderRepository.populateDatabase();
    }
}
