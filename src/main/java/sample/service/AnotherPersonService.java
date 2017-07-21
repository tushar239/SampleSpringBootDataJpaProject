package sample.service;

import javax.transaction.Transactional;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
public interface AnotherPersonService {
    @Transactional
    void save() throws Throwable;
}
