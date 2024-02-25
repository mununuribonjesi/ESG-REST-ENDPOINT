package com.esg.global.endpoint.handlers;

import com.esg.global.endpoint.domain.Customer;
import com.esg.global.endpoint.dtos.response.GenericMessageResponse;
import com.esg.global.endpoint.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomerHandler extends HandlerTemplate {

  private final CustomerService customerService;

  public CustomerHandler(CustomerService customerService) {
    this.customerService = customerService;
  }

  public Mono<ServerResponse> addCustomer(ServerRequest  request) {
    Mono<Customer> customerRequest = request.bodyToMono(Customer.class);

    return customerRequest.flatMap(customerService::saveCustomerDetail)
        .then(ServerResponse.status(201)
          .bodyValue(new GenericMessageResponse("Customer details saved successfully")))
          .onErrorResume(Exception.class, this::handleResponseError);
  }

  public Mono<ServerResponse> getCustomer(ServerRequest request) {
    Long customerRef = Long.parseLong(request.pathVariable("customerRef"));

    return customerService.getCustomerByRef(customerRef)
      .flatMap(customer -> ServerResponse.ok().bodyValue(customer))
      .onErrorResume(Exception.class, this::handleResponseError);
  }
}
