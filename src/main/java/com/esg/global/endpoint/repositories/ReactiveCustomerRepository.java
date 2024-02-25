package com.esg.global.endpoint.repositories;

import com.esg.global.endpoint.domain.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveCustomerRepository extends ReactiveCrudRepository<Customer, Long> {
  Mono<Customer> findByCustomerRef(Long customerRef);
}
