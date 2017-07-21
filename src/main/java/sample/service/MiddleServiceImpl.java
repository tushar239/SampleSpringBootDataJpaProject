package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sample.domain.Customer;
import sample.domain.Person;

import javax.transaction.Transactional;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
@Component
public class MiddleServiceImpl implements MiddleService {

    @Autowired
    private PersonService personService;

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public void saveInSameTransaction() {

        Person person = new Person(1L, "O", "C");
        personService.save(person);

        Customer customer = new Customer(1L, 1L);
        customerService.save(customer);

    }

    /*
    person will be saved, but not customer
     */
    @Override
    @Transactional
    public void saveInDifferentTransactions() {
        Person person = new Person(2L, "T", "C");
        personService.save(person);

        try {
            Customer customer = new Customer(2L, 2L);
            customerService.saveInDifferentTransaction(customer);
        } catch (Exception e) { // If you don't catch an exception here, if saveInDifferentTransaction throws an exception, this method will also throw an exception and person transaction will also be rolled back.
            System.out.println(e);
        }
    }

    @Override
    public void saveWithoutTransaction() {
        Person person = new Person(3L, "M", "C");
        personService.save(person);

        Customer customer = new Customer(3L, 3L);
        customerService.saveWithoutTransaction(customer);
    }


    @Override
    public void loadAll() {
        System.out.println("Persons:" + personService.loadAll());

        System.out.println("Customers: " + customerService.loadAll());
    }
}
