package com.bredex.jobsearch.service;

import com.bredex.jobsearch.model.InitialData;
import com.bredex.jobsearch.repository.ClientRepository;
import com.bredex.jobsearch.repository.PositionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class InitialDataLoaderService {
  private final ClientRepository clientRepository;
  private final PositionRepository positionRepository;
  private final ResourceLoader resourceLoader;

  public InitialDataLoaderService(ClientRepository clientRepository, PositionRepository positionRepository, ResourceLoader resourceLoader) {
    this.clientRepository = clientRepository;
    this.positionRepository = positionRepository;
    this.resourceLoader = resourceLoader;
  }

  @PostConstruct
  public void loadInitialData() {
    try {
      Resource resource = resourceLoader.getResource("classpath:data.json");
      InputStream jsonContent = resource.getInputStream();

      ObjectMapper objectMapper = new ObjectMapper();
      InitialData initialData = objectMapper.readValue(jsonContent, InitialData.class);

      clientRepository.saveAll(initialData.getClients());
      positionRepository.saveAll(initialData.getPositions());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

