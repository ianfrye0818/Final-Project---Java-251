package services;

import Interfaces.ICustomerRepository;
import Interfaces.IOrderRepository;
import Interfaces.IOrderService;
import dto.CreateOrderDto;
import dto.UpdateCustomerDto;
import dto.UpdateOrderDto;
import entites.Customer;
import entites.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;

    public OrderService(IOrderRepository orderRepository, ICustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order getOrderById(int id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        return this.orderRepository.findByCustomerId(customerId);
    }

    @Override
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

    @Override
    public Order updateOrder(UpdateOrderDto order) {
        return this.orderRepository.update(order);

    }

    @Override
    public boolean deleteOrder(int id) {
        return this.orderRepository.deleteById(id);
    }

    @Override
    public void resetDatabase() throws SQLException {
        this.orderRepository.resetDatabase();
    }

    @Override
    public void populateDatabase() throws SQLException {
        this.orderRepository.populateDatabase();
    }
}
