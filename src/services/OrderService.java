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

/**
 * Implements the {@link IOrderService} interface to provide business logic
 * for managing {@link Order} entities. This service interacts with both the
 * {@link IOrderRepository} for order data access and the
 * {@link ICustomerRepository} to update customer information upon order
 * creation.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;

    /**
     * Constructs a new {@code OrderService} with the specified order and customer
     * repositories.
     *
     * @param orderRepository    The {@link IOrderRepository} to be used for order
     *                           data access.
     * @param customerRepository The {@link ICustomerRepository} to be used for
     *                           customer data access.
     */
    public OrderService(IOrderRepository orderRepository, ICustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves all {@link Order} entities from the data source. This method
     * delegates the operation to the {@link IOrderRepository#findAll()} method.
     *
     * @return A {@code List} of all {@link Order} entities.
     */
    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    /**
     * Retrieves a specific {@link Order} entity by its ID. This method delegates
     * the operation to the {@link IOrderRepository#findById(int)} method.
     *
     * @param id The ID of the order to retrieve.
     * @return The {@link Order} entity with the specified ID, or {@code null} if
     *         not found.
     */
    @Override
    public Order getOrderById(int id) {
        return this.orderRepository.findById(id);
    }

    /**
     * Retrieves all {@link Order} entities associated with a specific customer ID.
     * This method delegates the operation to the
     * {@link IOrderRepository#findByCustomerId(int)} method.
     *
     * @param customerId The ID of the customer whose orders are to be retrieved.
     * @return A {@code List} of {@link Order} entities for the given customer ID.
     */
    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        return this.orderRepository.findByCustomerId(customerId);
    }

    /**
     * Creates a new {@link Order} entity using the provided {@link CreateOrderDto}.
     * After successfully saving the order using the {@link IOrderRepository},
     * this method updates the credit limit of the associated customer by deducting
     * the total price of the order. It then returns the newly created
     * {@link Order}.
     *
     * @param order The {@link CreateOrderDto} containing the details of the order
     *              to create.
     * @return The newly created {@link Order} entity.
     */
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

    /**
     * Updates an existing {@link Order} entity using the provided
     * {@link UpdateOrderDto}. This method delegates the update operation to the
     * {@link IOrderRepository#update(CreateOrderDto)} method.
     *
     * @param order The {@link UpdateOrderDto} containing the updated details of the
     *              order.
     * @return The updated {@link Order} entity.
     */
    @Override
    public Order updateOrder(UpdateOrderDto order) {
        return this.orderRepository.update(order);
    }

    /**
     * Deletes an {@link Order} entity by its ID. This method delegates the delete
     * operation to the {@link IOrderRepository#deleteById(int)} method.
     *
     * @param id The ID of the order to delete.
     * @return {@code true} if the order was successfully deleted; {@code false}
     *         otherwise.
     */
    @Override
    public boolean deleteOrder(int id) {
        return this.orderRepository.deleteById(id);
    }

    /**
     * Resets the underlying data store for orders. This method delegates the
     * operation to the {@link IOrderRepository#resetDatabase()} method.
     *
     * @throws SQLException If a database error occurs during the reset operation.
     */
    @Override
    public void resetDatabase() throws SQLException {
        this.orderRepository.resetDatabase();
    }

    /**
     * Populates the underlying data store with initial order data. This method
     * delegates the operation to the {@link IOrderRepository#populateDatabase()}
     * method.
     *
     * @throws SQLException If a database error occurs during the population
     *                      operation.
     */
    @Override
    public void populateDatabase() throws SQLException {
        this.orderRepository.populateDatabase();
    }
}