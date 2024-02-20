package com.bredex.jobsearch.repository;

import com.bredex.jobsearch.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
  List<Position> findByJobTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(String keyword, String location);
}
