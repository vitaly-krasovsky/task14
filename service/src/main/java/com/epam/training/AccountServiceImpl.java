package com.epam.training;

import java.util.Collection;

/**
 * @author Vitaly_Krasovsky.
 */
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository = new AccountRepositoryImpl();

    public Account create(Account account) {
        return accountRepository.create(account);
    }

    public Account update(Account account) {
        return accountRepository.update(account);
    }

    public void delete(int id) {
        accountRepository.delete(id);
    }

    public Account get(int id) {
        return accountRepository.get(id);
    }

    public Collection<Account> getAll() {
        return accountRepository.getAll();
    }
}
