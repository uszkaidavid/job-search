package com.bredex.jobsearch.controller;

import com.bredex.jobsearch.model.Client;
import com.bredex.jobsearch.service.ClientService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {
  @Autowired
  private ClientService clientService;

  @PostMapping
  public ResponseEntity<Map<String, String>> registerClient(@Valid @RequestBody Client client) {
    UUID apiKey = clientService.registerClient(client);
    Map<String, String> response = new HashMap<>();
    response.put("apiKey", apiKey.toString());
    return ResponseEntity.ok(response);
  }
}
