package com.esg.global.endpoint.exceptions;

public class DocumentNotFoundException extends RuntimeException{
  public DocumentNotFoundException(String message) {
    super(message);
  }
}
