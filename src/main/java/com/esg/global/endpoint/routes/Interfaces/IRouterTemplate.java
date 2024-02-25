package com.esg.global.endpoint.routes.Interfaces;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public interface IRouterTemplate {
  ServerResponse logEndPointRequest(ServerRequest request, ServerResponse response);
}
