package com.esg.global.endpoint.routes;

import com.esg.global.endpoint.routes.Interfaces.IRouterTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
public class RouterTemplate implements IRouterTemplate {

  @Override
  public ServerResponse logEndPointRequest(ServerRequest request, ServerResponse response) {
    log.info("Request Path: {} Response Status: {} Http Method: {}",
      request.path(), response.statusCode(), request.method());
    return response;
  }
}
