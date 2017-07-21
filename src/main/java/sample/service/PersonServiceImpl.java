package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sample.domain.Person;
import sample.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Tushar Chokshi @ 7/19/17.
 */
@Service
//@Transactional // -- if you provide a transaction at spring bean class level, all methods will inherit it. If method has its own @Transactional, then that will be used.
@Component
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional
    public List<Person> loadAll() {
        return personRepository.findAll();
    }

    public void save(Person person) {
        personRepository.save(person);
    }

}
