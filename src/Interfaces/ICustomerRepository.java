package Interfaces;

import dto.CreateCustomerDto;
import entites.Customer;

public interface ICustomerRepository extends IRepository<Customer, CreateCustomerDto> {
    Customer findByEmail(String email);
}
