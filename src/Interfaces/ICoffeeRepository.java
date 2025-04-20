package Interfaces;

import dto.CreateCoffeeDto;
import entites.Coffee;

/**
 * An interface for a repository that manages coffee entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public interface ICoffeeRepository extends IRepository<Coffee, CreateCoffeeDto> {

}
