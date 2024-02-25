package com.esg.global.endpoint.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.esg.global.endpoint.domain.Customer;
import com.esg.global.endpoint.dtos.response.GenericMessageResponse;
import com.esg.global.endpoint.exceptions.DocumentNotFoundException;
import com.esg.global.endpoint.exceptions.InternalServerErrorException;
import com.esg.global.endpoint.routes.CustomerRouter;
import com.esg.global.endpoint.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

@WebFluxTest(CustomerHandler.class)
class CustomerRouterTest {

  @MockBean
  private CustomerService customerService;

  @Autowired
  private WebTestClient webTestClient;

  @BeforeEach
  public void setUp(){
    CustomerHandler customerHandler = new CustomerHandler(customerService);
    RouterFunction<?> routes = new CustomerRouter().customerRoutes(customerHandler);
    webTestClient = WebTestClient.bindToRouterFunction(routes).build();
  }


  @Test
  void addCustomer_shouldReturnCreatedResponse_201() {
    Customer customer = Customer.builder().build();
    given(customerService.saveCustomerDetail(any(Customer.class))).willReturn(Mono.empty());

    GenericMessageResponse genericMessageResponse = new GenericMessageResponse("Customer details saved successfully");
    webTestClient.post()
      .uri("/v1/customer")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(customer)
      .exchange()
      .expectStatus().isEqualTo(201)
      .expectBody(new ParameterizedTypeReference<GenericMessageResponse>(){})
      .consumeWith(actual -> assertThat(actual.getResponseBody()).isEqualTo(genericMessageResponse));
  }

  @Test
  void addCustomer_shouldReturnInternalServerErrorOnError_500() {
    Customer customer = Customer.builder().build();
    given(customerService.saveCustomerDetail(any(Customer.class)))
      .willReturn(Mono.error(new InternalServerErrorException("Error saving customer details")));

    GenericMessageResponse genericMessageResponse = new GenericMessageResponse("Error saving customer details");

    webTestClient.post()
      .uri("/v1/customer")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(customer)
      .exchange()
      .expectStatus().isEqualTo(500)
      .expectBody(new ParameterizedTypeReference<GenericMessageResponse>(){})
      .consumeWith(actual -> assertThat(actual.getResponseBody()).isEqualTo(genericMessageResponse));
  }

  @Test
  void getCustomerRef_shouldReturnTheCustomer_200() {
    Customer customer = Customer.builder()
      .customerName("John Doe")
      .customerRef(123L)
      .county("South Yorkshire")
      .addressLine1("671 Longsett Road")
      .postcode("S15 6AJ")
      .town("Sehffield")
      .country("United Kingdom").build();

    given(customerService.getCustomerByRef(123L)).willReturn(Mono.just(customer));

    webTestClient.get()
      .uri("/v1/customer/123")
      .exchange()
      .expectStatus().isEqualTo(200)
      .expectBody(new ParameterizedTypeReference<Customer>(){})
      .consumeWith(actual -> assertThat(actual.getResponseBody()).isEqualTo(customer));
  }

  @Test
  void getCustomerRef_shouldNotFindCustomer_404() {

    given(customerService.getCustomerByRef(any()))
      .willReturn(Mono.error(new DocumentNotFoundException("Customer Details Not Found")));

    GenericMessageResponse genericMessageResponse = new GenericMessageResponse("Customer Details Not Found");

    webTestClient.get()
      .uri("/v1/customer/123")
      .exchange()
      .expectStatus().isEqualTo(404)
      .expectBody(new ParameterizedTypeReference<GenericMessageResponse>(){})
      .consumeWith(actual -> assertThat(actual.getResponseBody()).isEqualTo(genericMessageResponse));
  }

  @Test
  void getCustomerRef_shouldNotFindCustomer_500() {

    given(customerService.getCustomerByRef(any()))
      .willReturn(Mono.error(new InternalServerErrorException("Error Getting Customer Details")));

    GenericMessageResponse genericMessageResponse = new GenericMessageResponse("Error Getting Customer Details");

    webTestClient.get()
      .uri("/v1/customer/123")
      .exchange()
      .expectStatus().isEqualTo(500)
      .expectBody(new ParameterizedTypeReference<GenericMessageResponse>(){})
      .consumeWith(actual -> assertThat(actual.getResponseBody()).isEqualTo(genericMessageResponse));
  }
}