package com.epam.training;

import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;

/**
 * @author Vitaly_Krasovsky.
 */
public class AccountRepositoryImpl implements AccountRepository {
    private static final Logger LOGGER = LogManager.getLogger(AccountRepositoryImpl.class);
    private EntityManager entityManager;


    public Account create(Account account) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {

            account.getClient().addAccounts(Lists.newArrayList(account));

            transaction.begin();
            entityManager.persist(account.getClient());
            entityManager.persist(account);

            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            account = null;
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return account;
    }

    public Account update(Account account) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Account accountManaged = null;
        Client clientManaged = null;

        try {

            account.getClient().addAccounts(Lists.newArrayList(account));

            transaction.begin();
            clientManaged = entityManager.merge(account.getClient());
            accountManaged = entityManager.merge(account);

//            clientManaged.addAccounts(Lists.newArrayList(account));

            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return accountManaged;
    }

    public void delete(int id) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Account account = entityManager.find(Account.class, id);
            entityManager.remove(account.getClient());
            entityManager.remove(account);

            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public Account get(int id) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Account account = null;
        try {
            transaction.begin();
            account = entityManager.find(Account.class, id);
            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return account;
    }

    public Collection<Account> getAll() {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Collection<Account> accounts = null;

        try {
            transaction.begin();
            Query query = entityManager.createQuery("SELECT a FROM Account a");
            accounts = (Collection<Account>) query.getResultList();
            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return accounts;
    }
}
