package com.bredex.jobsearch.controller;

import com.bredex.jobsearch.model.Client;
import com.bredex.jobsearch.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientControllerTest {
  @Mock
  private ClientService clientService;

  @InjectMocks
  private ClientController clientController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testRegisterClient() {
    Client client = new Client();
    client.setName("John Doe");
    client.setEmail("john@example.com");

    UUID apiKey = UUID.randomUUID();
    when(clientService.registerClient(client)).thenReturn(apiKey);

    ResponseEntity<Map<String, String>> responseEntity = clientController.registerClient(client);

    verify(clientService, times(1)).registerClient(client);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(apiKey.toString(), responseEntity.getBody().get("apiKey"));
  }

}
