package Interfaces;

import models.Customer;

public interface ICustomerRepository extends IRepository<Customer> {
    Customer findByEmail(String email);
}
