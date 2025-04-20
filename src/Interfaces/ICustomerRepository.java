package Interfaces;

import dto.CreateCustomerDto;
import entites.Customer;

/**
 * An interface for a repository that manages customer entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public interface ICustomerRepository extends IRepository<Customer, CreateCustomerDto> {
    Customer findByEmail(String email);
}
