package com.esg.global.endpoint.handlers;

import com.esg.global.endpoint.handlers.Interfaces.IHandlerTemplate;
import com.esg.global.endpoint.dtos.response.GenericMessageResponse;
import com.esg.global.endpoint.exceptions.DocumentNotFoundException;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class HandlerTemplate implements IHandlerTemplate {

  @Override
  public Mono<ServerResponse> handleResponseError(Throwable exception) {
    if(exception.getClass() == DocumentNotFoundException.class) {
      return ServerResponse.status(404).bodyValue(new GenericMessageResponse(exception.getMessage()));
    }

    return ServerResponse.status(500).bodyValue(new GenericMessageResponse(exception.getMessage()));
  }
}
