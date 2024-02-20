package com.bredex.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SearchRequest {

  @NotEmpty(message = "Keyword must not be empty")
  @Size(max = 50, message = "Keyword length must not exceed 50 characters")
  private String keyword;

  @NotEmpty(message = "Location must not be empty")
  @Size(max = 50, message = "Location length must not exceed 50 characters")
  private String location;

}