package Interfaces;

import java.util.List;

import models.Order;

public interface IOrderRepository extends IRepository<Order> {
  List<Order> findByCustomerId(int customerId);
}
