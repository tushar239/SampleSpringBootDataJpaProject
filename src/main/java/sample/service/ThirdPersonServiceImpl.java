package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sample.domain.Person;
import sample.repository.PersonRepository;

import javax.transaction.Transactional;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
@Component
public class ThirdPersonServiceImpl implements ThirdPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional(/*value = Transactional.TxType.REQUIRED,*/ rollbackOn = Throwable.class)
    @Override
    public void save() throws Throwable {
        personRepository.save(new Person(5L, "P", "C"));
        throw new Exception("some exception");
    }
}
