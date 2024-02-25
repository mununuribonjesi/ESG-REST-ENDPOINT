package com.esg.global.endpoint.handlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.esg.global.endpoint.dtos.response.GenericMessageResponse;
import com.esg.global.endpoint.exceptions.DocumentNotFoundException;
import com.esg.global.endpoint.exceptions.InternalServerErrorException;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.reactive.function.server.ServerResponse;

class HandlerTemplateTest {

  @ParameterizedTest(name = "Returns server response based on exception")
  @MethodSource
  void handleResponseErrorByException(Exception exception, ServerResponse serverResponse) {

    HandlerTemplate handlerTemplate = new HandlerTemplate();

    var actual = handlerTemplate.handleResponseError(exception);
    assertThat(Objects.requireNonNull(actual.block()).statusCode()).isEqualTo(serverResponse.statusCode());
  }

  static Stream<Arguments> handleResponseErrorByException() {
    InternalServerErrorException internalServerErrorException = new InternalServerErrorException(null);
    DocumentNotFoundException documentNotFoundException = new DocumentNotFoundException(null);

    ServerResponse internalServerErrorResponse = ServerResponse
      .status(500).bodyValue(new GenericMessageResponse(null)).block();
    ServerResponse notFoundResponse = ServerResponse
      .status(404).bodyValue(new GenericMessageResponse(null)).block();

    return Stream.of(
      Arguments.of(internalServerErrorException, internalServerErrorResponse),
      Arguments.of(documentNotFoundException, notFoundResponse)
    );
  }
}