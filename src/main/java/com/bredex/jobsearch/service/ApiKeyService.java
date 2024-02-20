package com.bredex.jobsearch.service;

import com.bredex.jobsearch.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiKeyService {

  @Autowired
  private ClientRepository clientRepository;

  public boolean isValidApiKey(String apiKey) {
    try {
      UUID uuid = UUID.fromString(apiKey);
      return clientRepository.existsByApiKey(uuid);
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
