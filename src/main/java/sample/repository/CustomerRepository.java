package sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.domain.Customer;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
