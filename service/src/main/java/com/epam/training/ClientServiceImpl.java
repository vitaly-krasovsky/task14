package com.epam.training;

import java.util.Collection;

/**
 * @author Vitaly_Krasovsky.
 */
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository = new ClientRepositoryImpl();

    public Client create(Client client) {
        return clientRepository.create(client);
    }

    public Client update(Client client) {
        return clientRepository.update(client);
    }

    public void delete(int id) {
        clientRepository.delete(id);
    }

    public Client get(int id) {
        return clientRepository.get(id);
    }

    public Collection<Client> getAll() {
        return clientRepository.getAll();
    }
}
