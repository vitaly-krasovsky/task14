package com.epam.training;

import java.util.Collection;

/**
 * @author Vitaly_Krasovsky.
 */
public interface ClientRepository {
    Client create(Client client);

    Client update(Client client);

    void delete(int id);

    Client get(int id);

    Collection<Client> getAll();
}
