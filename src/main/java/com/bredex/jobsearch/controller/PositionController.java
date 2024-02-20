package com.bredex.jobsearch.controller;

import com.bredex.jobsearch.model.Position;
import com.bredex.jobsearch.model.SearchRequest;
import com.bredex.jobsearch.service.ApiKeyService;
import com.bredex.jobsearch.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/position")
public class PositionController {

  @Autowired
  private PositionService positionService;

  @Autowired
  private ApiKeyService apiKeyService;

  @Value("${base.url}")
  private String baseUrl;

  @PostMapping
  public ResponseEntity<Map<String, String>> createPosition(@Validated @RequestBody Position position, @RequestHeader("X-API-Key") String apiKey) {
    Map<String, String> response = new HashMap<>();

    if (!apiKeyService.isValidApiKey(apiKey)) {
      response.put("apiKey", "Invalid API key");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    Position savedPosition = positionService.createPosition(position);
    String positionUrl = String.format("%s/position/%s", baseUrl, savedPosition.getId());
    response.put("url", positionUrl);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchPositions(@Validated SearchRequest searchRequest, @RequestHeader("X-API-Key") String apiKey) {

    if (!apiKeyService.isValidApiKey(apiKey)) {
      Map<String, String> response = new HashMap<>();
      response.put("apiKey", "Invalid API key");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    List<Position> matchingPositions = positionService.searchPositions(searchRequest.getKeyword(), searchRequest.getLocation());

    List<String> positionUrls = new ArrayList<>();
    for (Position position : matchingPositions) {
      String positionUrl = String.format("%s/position/%s", baseUrl, position.getId());
      positionUrls.add(positionUrl);
    }

    Map<String, List<String>> response = new HashMap<>();
    response.put("urls", positionUrls);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPositionById(@PathVariable Long id) {
    Position position = positionService.getPositionById(id);

    if (position != null) {
      return ResponseEntity.ok(position);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Position not found");
    }
  }

}

