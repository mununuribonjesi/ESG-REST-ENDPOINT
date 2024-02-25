package com.esg.global.endpoint.handlers.Interfaces;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IHandlerTemplate {
  Mono<ServerResponse> handleResponseError(Throwable exception);
}
