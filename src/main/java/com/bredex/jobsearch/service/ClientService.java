package com.bredex.jobsearch.service;

import com.bredex.jobsearch.model.Client;
import com.bredex.jobsearch.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {
  @Autowired
  private ClientRepository clientRepository;

  public UUID registerClient(Client client) {
    UUID apiKey = generateApiKey();
    client.setApiKey(apiKey);
    clientRepository.save(client);
    return apiKey;
  }

  protected UUID generateApiKey() {
    return UUID.randomUUID();
  }
}

