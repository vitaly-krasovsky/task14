package com.epam.training;

import java.util.Collection;

/**
 * @author Vitaly_Krasovsky.
 */
public interface AccountService {
    Account create(Account account);

    Account update(Account account);

    void delete(int id);

    Account get(int id);

    Collection<Account> getAll();
}
