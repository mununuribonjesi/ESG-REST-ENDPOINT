package com.esg.global.endpoint.routes;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.esg.global.endpoint.handlers.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouter extends RouterTemplate {

  @Bean
  public RouterFunction<ServerResponse> customerRoutes(CustomerHandler customerHandler) {
    return route()
      .POST( "v1/customer",customerHandler::addCustomer)
      .GET("v1/customer/{customerRef}", customerHandler::getCustomer)
      .after(this::logEndPointRequest).build();
  }
}
