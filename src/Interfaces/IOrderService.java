package Interfaces;

import dto.CreateOrderDto;
import dto.UpdateOrderDto;
import entites.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface for a service that manages order entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public interface IOrderService {
    List<Order> getAllOrders();

    Order getOrderById(int id);

    List<Order> getOrdersByCustomerId(int customerId);

    Order createOrder(CreateOrderDto order);

    Order updateOrder(UpdateOrderDto order);

    boolean deleteOrder(int id);

    void resetDatabase() throws SQLException;

    void populateDatabase() throws SQLException;
}
