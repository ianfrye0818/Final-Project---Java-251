package services;

import entites.Customer;
import entites.Order;
import repositories.CustomerRepository;

import java.sql.SQLException;
import java.util.List;
import Interfaces.IOrderRepository;
import dto.CreateOrderDto;
import dto.UpdateCustomerDto;
import dto.UpdateOrderDto;

public class OrderService {

    private final IOrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(IOrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
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
        // save the order
        Order newOrder = this.orderRepository.save(order);
        // update the customer's credit limit
        Customer orderCustomer = this.customerRepository.findById(order.getCustomerId());
        UpdateCustomerDto dto = UpdateCustomerDto.fromCustomer(orderCustomer);
        dto.setCreditLimit(orderCustomer.getCreditLimit() - order.getTotal());
        this.customerRepository.update(dto);
        // return the order
        return newOrder;
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
