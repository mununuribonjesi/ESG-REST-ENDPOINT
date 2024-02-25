package com.esg.global.endpoint.exceptions;

public class InternalServerErrorException extends RuntimeException{
  public InternalServerErrorException(String message) {
    super(message);
  }
}
