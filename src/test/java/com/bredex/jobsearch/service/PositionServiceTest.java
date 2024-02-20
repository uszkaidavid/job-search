package com.bredex.jobsearch.service;

import com.bredex.jobsearch.model.Position;
import com.bredex.jobsearch.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PositionServiceTest {

  @Mock
  private PositionRepository positionRepository;

  @InjectMocks
  private PositionService positionService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testCreatePosition() {
    Position position = new Position();
    position.setJobTitle("Software Engineer");
    position.setLocation("San Francisco");

    when(positionRepository.save(position)).thenReturn(position);
    Position savedPosition = positionService.createPosition(position);

    verify(positionRepository).save(position);
    assertEquals(position, savedPosition);
  }

  @Test
  void testSearchPositions() {
    String keyword = "Java";
    String location = "New York";
    List<Position> expectedPositions = new ArrayList<>();

    when(positionRepository.findByJobTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(keyword, location))
        .thenReturn(expectedPositions);

    List<Position> foundPositions = positionService.searchPositions(keyword, location);
    verify(positionRepository).findByJobTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(keyword, location);
    assertEquals(expectedPositions, foundPositions);
  }

  @Test
  void testGetPositionById() {
    long id = 1L;
    Position expectedPosition = new Position();
    expectedPosition.setId(id);

    when(positionRepository.findById(id)).thenReturn(Optional.of(expectedPosition));
    Position foundPosition = positionService.getPositionById(id);

    verify(positionRepository).findById(id);
    assertEquals(expectedPosition, foundPosition);
  }
}
