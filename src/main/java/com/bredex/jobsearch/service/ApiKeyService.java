package com.bredex.jobsearch.service;

import com.bredex.jobsearch.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {
  private final ClientRepository clientRepository;

  public boolean isValidApiKey(String apiKey) {
    try {
      UUID uuid = UUID.fromString(apiKey);
      return clientRepository.existsByApiKey(uuid);
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
