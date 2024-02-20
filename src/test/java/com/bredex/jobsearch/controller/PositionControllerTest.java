package com.bredex.jobsearch.controller;

import com.bredex.jobsearch.model.Position;
import com.bredex.jobsearch.model.SearchRequest;
import com.bredex.jobsearch.service.ApiKeyService;
import com.bredex.jobsearch.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PositionControllerTest {

  @Mock
  private PositionService positionService;

  @Mock
  private ApiKeyService apiKeyService;

  @InjectMocks
  private PositionController positionController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testCreatePosition() {
    Position position = new Position();
    position.setJobTitle("Software Engineer");
    position.setLocation("San Francisco");

    when(apiKeyService.isValidApiKey("valid-api-key")).thenReturn(true);
    when(positionService.createPosition(position)).thenReturn(position);
    ResponseEntity<Map<String, String>> responseEntity = positionController.createPosition(position, "valid-api-key");

    verify(positionService, times(1)).createPosition(position);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

  @Test
  void testSearchPositions() {
    SearchRequest searchRequest = new SearchRequest();
    searchRequest.setKeyword("Java");
    searchRequest.setLocation("New York");

    List<Position> matchingPositions = new ArrayList<>();
    Position position1 = new Position();
    position1.setId(1L);
    position1.setJobTitle("Java Developer");
    position1.setLocation("New York");
    Position position2 = new Position();
    position2.setId(2L);
    position2.setJobTitle("Senior Java Developer");
    position2.setLocation("New York");
    matchingPositions.add(position1);
    matchingPositions.add(position2);

    when(apiKeyService.isValidApiKey("valid-api-key")).thenReturn(true);
    when(positionService.searchPositions(searchRequest.getKeyword(), searchRequest.getLocation())).thenReturn(matchingPositions);
    ResponseEntity<?> responseEntity = positionController.searchPositions(searchRequest, "valid-api-key");

    verify(positionService, times(1)).searchPositions(searchRequest.getKeyword(), searchRequest.getLocation());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  void testGetPositionById() {
    Long positionId = 1L;
    Position position = new Position();
    position.setId(positionId);
    position.setJobTitle("Software Engineer");
    position.setLocation("San Francisco");

    when(positionService.getPositionById(positionId)).thenReturn(position);
    ResponseEntity<?> responseEntity = positionController.getPositionById(positionId);

    verify(positionService, times(1)).getPositionById(positionId);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(position, responseEntity.getBody());
  }

  @Test
  void testGetPositionById_PositionNotFound() {
    Long positionId = 1L;
    when(positionService.getPositionById(positionId)).thenReturn(null);

    ResponseEntity<?> responseEntity = positionController.getPositionById(positionId);

    verify(positionService, times(1)).getPositionById(positionId);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("Position not found", responseEntity.getBody());
  }
}
