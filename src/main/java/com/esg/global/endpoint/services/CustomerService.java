package com.esg.global.endpoint.services;

import com.esg.global.endpoint.domain.Customer;
import com.esg.global.endpoint.exceptions.DocumentNotFoundException;
import com.esg.global.endpoint.repositories.ReactiveCustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerService {

  private final ReactiveCustomerRepository customerRepository;

  public CustomerService(ReactiveCustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Mono<Void> saveCustomerDetail(Customer customer) {
    log.info("Saving customer details ref: {}", customer.customerRef());
    return customerRepository.save(customer).then();
  }

  public Mono<Customer> getCustomerByRef(Long customerRef) {
    log.info("Retrieving customer details for ref: {}", customerRef);
    return customerRepository.findByCustomerRef(customerRef)
      .switchIfEmpty(Mono.error(new DocumentNotFoundException("Customer Details Not Found")));
  }
}
