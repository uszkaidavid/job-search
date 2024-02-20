package com.bredex.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InitialData {
  @JsonProperty("clients")
  private List<Client> clients;

  @JsonProperty("positions")
  private List<Position> positions;
}