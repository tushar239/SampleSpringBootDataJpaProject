package sample.service;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
public interface MiddleService {
    void saveWithoutTransaction();

    void saveInSameTransaction();

    void saveInDifferentTransactions();

    void loadAll();
}
