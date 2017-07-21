package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sample.service.MiddleService;

/**
 * @author Tushar Chokshi @ 7/19/17.
 */
@SpringBootApplication // @EnableAutoConfiguration + @ComponentScan

/*
@EnableAutoConfiguration will add JpaRepositoriesAutoConfiguration and that will add HibernateJpaAutoConfiguration, if spring-boot-starter-data-jpa is added as a dependency.
After adding this dependency and using @EnableAutoConfiguration, if you don't want to use these AutoConfiguration classes, then you need to exclude them from @EnableAutoConfiguration.

Without spring-boot, you normally need to to use @Enable*** and Configurer. For JPA, you need to use @EnableJpaRepositories (does not need any configurer) and @EnableTransactionManagement with TransactionManagementConfigurer.
In spring-boot, @EnableJpaRepositories is added by JpaRepositoriesAutoConfiguration (JpaRepositoriesAutoConfiguration imports JpaRepositoriesAutoConfigureRegistrar and it adds that annotation).
*/

/*
 What does @EnableJpaRepositories do?

 It scans all JpaRepositories from this package to child packages by default or the package that you provide.
 If you open this annotation, it doesn't suggest you to add any ConfigurerAdapter.
 Normally any @Enable*** requires you to add related ConfigurerAdapter.
 e.g. @EnableWebMvc requires you to add customized WebMvcConfigurerAdapter. Same for @EnableWebSecurity.
 If you use spring-boot, then @EnableAutoConfiguration will add AutoConfigurerAdapter, so you don't need to add your own.
*/


/*
What if you don't want to use spring-boot for spring-data-jpa ?

You don't need a dependency of spring-boot-starter-data-jpa. You need a dependency of spring-data-jpa.

You need to do following:

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement // If you see its javadoc, it requires you to use TransactionManagementConfigurer
class ApplicationConfig implements TransactionManagementConfigurer {

  @Bean
  public DataSource dataSource() {

    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    return builder.setType(EmbeddedDatabaseType.HSQL).build();

    OR

    your own datasource using JNDI or using DataSourceBuilder
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("com.acme.domain");
    factory.setDataSource(dataSource());
    return factory;
  }

  @Override
  public PlatformTransactionManager transactionManager() {

    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory());
    return txManager;
  }
}
 */


/*
Transaction Management:

    https://www.youtube.com/watch?v=iDRPHQ52pns  --- Watch this video for better understanding in pictorial form.

    Spring transactions are proxy-based. There is a proxy class for each spring bean having @Transactional on class or method level.

    Here's thus how it works when bean A causes a transactional of bean B :
        A has in fact a reference to a proxy, which delegates to the bean B.

        A ---> proxy ---> B

        B's transactional method called from A's transactional method can use A's transaction.

        If B has two transactional methods where one is calling another, another method will use same transaction as A. @Transactional on another method has no effect.
        e.g.

        class A {
            @Transactional  // default propagation level is REQUIRED.
            public void method1() {
                method2();
                classB.method1();
                classB.method2();
            }

            @Transactional(REQUIRES_NEW) // this method will use method1()'s transaction only because method1() is calling it from the same class. @Transactional is ignored for this method.
            public void method2() {
            }
        }

        class B {
            @Transactional // This method will get the same transaction as Class A's method1()'s transaction because default propagation level is REQUIRED.
            public void method1() {
            }

            @Transactional(REQUIRES_NEW) // new transaction will be created inside outer transaction created by method1()
            public void method2() {
            }
        }

    Class-Level @Transactional

        all methods inside a class by default get class-level @Transactional unless they have their own.
 */


// https://github.com/michl-b/spring-boot-sample-data-jpa-h2/tree/master/src/main/java/de/michlb/sample
public class SampleApplication {

    public static void main(String[] args) throws Throwable {
        ConfigurableApplicationContext context = SpringApplication.run(SampleApplication.class, args);

        MiddleService middleService = context.getBean(MiddleService.class);

        System.out.println();
        System.out.println("Trying to save person and customer without any transaction...");
        try {
            middleService.saveWithoutTransaction();
        } catch (Exception e) {
            System.out.println(e);
        }

        middleService.loadAll();

        System.out.println();
        System.out.println("Trying to save person and customer in same transaction...");
        try {
            middleService.saveInSameTransaction();
        } catch (Exception e) {
            System.out.println(e);
        }

        middleService.loadAll();

        System.out.println();
        System.out.println("Trying to save person and customer in different transactions...");
        try {
            middleService.saveInDifferentTransactions();
        } catch (Exception e) {
            System.out.println(e);
        }

        middleService.loadAll();
    }



}