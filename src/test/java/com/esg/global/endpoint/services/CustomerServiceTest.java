package com.esg.global.endpoint.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.esg.global.endpoint.domain.Customer;
import com.esg.global.endpoint.exceptions.DocumentNotFoundException;
import com.esg.global.endpoint.repositories.ReactiveCustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

  @Mock
  private ReactiveCustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  @Test
  void saveCustomerDetail_shouldSaveCustomer() {
    Customer customer = Customer.builder().build();
    given(customerRepository.save(any(Customer.class))).willReturn(Mono.empty());

    Mono<Void> result  = customerService.saveCustomerDetail(customer);
    StepVerifier.create(result).expectNextCount(0).verifyComplete();
  }

  @Test
  void getCustomerByRef_shouldRetrieveCustomerDetails() {
    long customerRef = 123L;
    Customer expected = Customer.builder().build();
    given(customerRepository.findByCustomerRef(customerRef)).willReturn(Mono.just(expected));

    Mono<Customer> result = customerService.getCustomerByRef(customerRef);
    StepVerifier.create(result).expectNext(expected).verifyComplete();
  }

  @Test
  void getCustomerByRef_shouldNotFindCustomerDetails() {
    long customerRef = 123L;
    Customer expected = Customer.builder().build();
    given(customerRepository.findByCustomerRef(customerRef)).willReturn(Mono.empty());

    Mono<Customer> result = customerService.getCustomerByRef(customerRef);
    StepVerifier.create(result).expectErrorMatches( throwable
      -> throwable instanceof DocumentNotFoundException).verify();
  }
}