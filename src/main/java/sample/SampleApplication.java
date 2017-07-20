package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sample.service.PersonService;

import javax.transaction.Transactional;

/**
 * @author Tushar Chokshi @ 7/19/17.
 */
@SpringBootApplication // @EnableAutoConfiguration + @ComponentScan
//@EnableJpaRepositories
// https://github.com/michl-b/spring-boot-sample-data-jpa-h2/tree/master/src/main/java/de/michlb/sample
public class SampleApplication {

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(SampleApplication.class, args);

        PersonService personService = context.getBean(PersonService.class);
        save(personService);

        save4(personService);

        System.out.println(personService.loadAll());
    }

    protected static void save(PersonService personService) throws Throwable{
        try {
            personService.save();
        } catch (Throwable e) {
            System.out.println(e);
        }
    }

    @Transactional(rollbackOn = Throwable.class)
    protected static void save4(PersonService personService) throws Throwable{
        personService.save4();
        try {
            personService.save5();
        } catch (Throwable e) {
            System.out.println(e);
        }
    }


}