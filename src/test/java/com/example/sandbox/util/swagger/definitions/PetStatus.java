package com.example.sandbox.util.swagger.definitions;

import lombok.Getter;

@Getter
public enum PetStatus {
  AVAILABLE("available"),
  PENDING("pending"),
  SOLD("sold");

  private final String status;

  PetStatus(String status) {
    this.status = status;
  }
}
