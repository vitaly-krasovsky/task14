package com.epam.training;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Collection;


/**
 * @author vkrasovsky
 */
public class ClientRepositoryImpl implements ClientRepository {
    private static final Logger LOGGER = LogManager.getLogger(ClientRepositoryImpl.class);
    private EntityManager entityManager;


    public Client create(Client client) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {

            //TODO: this already done by json mapper?
            client.addAccounts(client.getAccounts());

            transaction.begin();
            entityManager.persist(client);

            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return client;
    }

    public Client update(Client client) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Client clientManaged = null;

        try {
            //TODO: this already done by json mapper?
            client.addAccounts(client.getAccounts());

            transaction.begin();

            clientManaged = entityManager.merge(client);


            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return clientManaged;
    }

    public void delete(int id) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Client client = entityManager.find(Client.class, id);
            //TODO: orphan remove here or add annotation?
//            client.removeAccounts();
            entityManager.remove(client);

            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public Client get(int id) {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Client client = null;
        try {
            transaction.begin();
            client = entityManager.find(Client.class, id);
            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return client;
    }

    public Collection<Client> getAll() {
        entityManager = RepositoryHelper.getEntityManager().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Collection<Client> clients = null;

        try {
            transaction.begin();
            Query query = entityManager.createQuery("SELECT c FROM Client c");
            clients = (Collection<Client>) query.getResultList();
            transaction.commit();
            entityManager.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return clients;
    }
}
