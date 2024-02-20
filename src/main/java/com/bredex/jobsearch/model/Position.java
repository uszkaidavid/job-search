package com.bredex.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Position {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Job title is required")
  @Size(max = 50, message = "Job title must be at most 50 characters")
  @Column(nullable = false, length = 50)
  private String jobTitle;

  @NotBlank(message = "Geographical location is required")
  @Size(max = 50, message = "Geographical location must be at most 50 characters")
  @Column(nullable = false, length = 50)
  private String location;

}
