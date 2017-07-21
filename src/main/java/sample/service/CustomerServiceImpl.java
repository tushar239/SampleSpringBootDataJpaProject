package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sample.domain.Customer;
import sample.repository.CustomerRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
        int i = 1 / 0;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void saveInDifferentTransaction(Customer customer) {
        customerRepository.save(customer);
        int i = 1 / 0;
    }

    public void saveWithoutTransaction(Customer customer) {
        customerRepository.save(customer);
        int i = 1 / 0;
    }

    @Override
    public List<Customer> loadAll() {
        return customerRepository.findAll();
    }


}
