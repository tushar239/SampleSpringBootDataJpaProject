package sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.domain.Person;
import sample.repository.PersonRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Tushar Chokshi @ 7/19/17.
 */
@Service
@Transactional // -- if you provide a transaction at spring bean class level, all methods will inherit it. If method has its own @Transactional, then that will be used.
/*
        Spring transactions are proxy-based.

        Here's thus how it works when bean A causes a transactional of bean B :
            A has in fact a reference to a proxy, which delegates to the bean B.

            A ---> proxy ---> B

            B's transactional method called from A's transactional method can use A's transaction.

            If B has two transactional methods where one is calling another, another method will use same transaction as A. @Transactional on another method has no effect.

 */
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional
    public List<Person> loadAll() {
        return personRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class) // rollbackOn is mandatory for the transaction to be rolled back on exception
    public void save() throws Throwable {
        save1();
        try {
            save3();
        } catch (Throwable t) { // if you don't catch an exception here, exception thrown by save3() will be thrown by save() also and that will cause save1() also rollback
            System.out.println(t);
        }
        save2(); // if save2() throws an exception, this entire transaction that covers save() will be rolled back because save2()'s Transaction is same as save()'s Transaction as it is defaulted to Propagation level REQUIRED.

    }

    @Override
    @Transactional
    // default propagation level is 'Required'. So, transaction will be created by for save() method and if it exists in ThreadLocal, then that will be used otherwise new transaction will be created.
    public void save1() throws Throwable {
        personRepository.save(new Person(1L, "T", "C"));
    }

    @Override
    @Transactional
    // default propagation level is 'Required'. So, transaction will be created by for save() method and if it exists in ThreadLocal, then that will be used otherwise new transaction will be created.
    public void save2() throws Throwable {
        personRepository.save(new Person(2L, "K", "C"));

        //throw new Exception("something happened"); // to test transaction support
    }

    // This will not create a new Transaction, if save3() is being called from the transactional method that is inside the same spring bean.
    // https://stackoverflow.com/questions/28480480/propagation-requires-new-does-not-create-a-new-transaction-in-spring-with-jpa
    /*
        Spring transactions are proxy-based. Here's thus how it works when bean A causes a transactional of bean B. A has in fact a reference to a proxy, which delegates to the bean B. This proxy is the one which starts and commits/rollbacks the transaction:
        A ---> proxy ---> B

        In your code, a transactional method of A calls another transactional method of A. So Spring can't intercept the call and start a new transaction. It's a regular method call without any proxy involved.

        So, if you want a new transaction to start, the method save3()() should be in another bean, injected into this bean.

        see save5() is working because it is called from another bean
     */
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Throwable.class)
    public void save3() throws Throwable {
        personRepository.save(new Person(3L, "K", "C"));

        throw new Exception("something happened"); // to test transaction support
    }

    @Transactional
    public void save4() throws Throwable {
        personRepository.save(new Person(4L, "L", "C"));
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Throwable.class) // works
    public void save5() throws Throwable {
        personRepository.save(new Person(5L, "U", "C"));

        throw new Exception("something happened"); // to test transaction support
    }

}
