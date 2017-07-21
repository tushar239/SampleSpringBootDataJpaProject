package sample.service;

import sample.domain.Customer;

import java.util.List;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
public interface CustomerService {
    void save(Customer customer);

    void saveInDifferentTransaction(Customer customer);

    void saveWithoutTransaction(Customer customer);

    List<Customer> loadAll();
}

