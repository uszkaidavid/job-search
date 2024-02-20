package com.bredex.jobsearch.service;

import com.bredex.jobsearch.model.Client;
import com.bredex.jobsearch.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceTest {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private ClientService clientService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testRegisterClient() {
    Client client = new Client();
    client.setName("Test Client");
    client.setEmail("test@example.com");

    UUID apiKey = UUID.randomUUID();
    ClientService clientServiceSpy = org.mockito.Mockito.spy(clientService);
    when(clientServiceSpy.generateApiKey()).thenReturn(apiKey);

    UUID generatedApiKey = clientServiceSpy.registerClient(client);
    verify(clientRepository).save(client);

    assert generatedApiKey != null;
    assert generatedApiKey.equals(apiKey);
  }
}


