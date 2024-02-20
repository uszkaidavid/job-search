package com.bredex.jobsearch.service;

import com.bredex.jobsearch.model.Position;
import com.bredex.jobsearch.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {

  private final PositionRepository positionRepository;

  public Position createPosition(Position position) {
    return positionRepository.save(position);
  }

  public List<Position> searchPositions(String keyword, String location) {
    return positionRepository.findByJobTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(keyword, location);
  }

  public Position getPositionById(Long id) {
    Optional<Position> positionOptional = positionRepository.findById(id);
    return positionOptional.orElse(null);
  }
}
