package Interfaces;

import java.util.List;

import dto.CreateOrderDto;
import entites.Order;

/**
 * An interface for a repository that manages order entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public interface IOrderRepository extends IRepository<Order, CreateOrderDto> {
  List<Order> findByCustomerId(int customerId);
}
