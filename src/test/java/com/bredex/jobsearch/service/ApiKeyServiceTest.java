package com.bredex.jobsearch.service;

import com.bredex.jobsearch.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ApiKeyServiceTest {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private ApiKeyService apiKeyService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testValidApiKey() {
    UUID validUuid = UUID.randomUUID();
    when(clientRepository.existsByApiKey(validUuid)).thenReturn(true);

    assertTrue(apiKeyService.isValidApiKey(validUuid.toString()));
    assertFalse(apiKeyService.isValidApiKey("invalid-api-key"));
  }

  @Test
  void testInvalidApiKey() {
    assertFalse(apiKeyService.isValidApiKey("invalid-api-key"));
  }
}
