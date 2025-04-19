package Interfaces;

import java.util.List;

import dto.CreateOrderDto;
import entites.Order;

public interface IOrderRepository extends IRepository<Order, CreateOrderDto> {
  List<Order> findByCustomerId(int customerId);
}
