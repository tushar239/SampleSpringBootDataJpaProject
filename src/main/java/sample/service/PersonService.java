package sample.service;

import sample.domain.Person;

import java.util.List;

/**
 * @author Tushar Chokshi @ 7/19/17.
 */
public interface PersonService {
    List<Person> loadAll();

    void save1() throws Throwable;

    void save2() throws Throwable;

    void save() throws Throwable;

    void save4() throws Throwable;

    void save5() throws Throwable;
}
