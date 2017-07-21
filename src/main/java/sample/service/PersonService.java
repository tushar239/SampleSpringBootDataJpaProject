package sample.service;

import sample.domain.Person;

import java.util.List;

/**
 * @author Tushar Chokshi @ 7/19/17.
 */
public interface PersonService {
    List<Person> loadAll();

    void save(Person person);
}
