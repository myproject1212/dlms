package com.erac.dlm.exception;

import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.erac.rentalclients.rest.client.api.exception.BadRequestException;
import com.erac.rentalclients.rest.client.api.exception.ServiceException;
import com.erac.rentalclients.rest.client.api.exception.ServiceMessage;
import com.erac.rentalclients.rest.client.api.exception.ServiceMessageBuilder;

import javassist.NotFoundException;

@ControllerAdvice("com.erac.dlm")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  public static final String NOT_FOUND_CODE = "DLM000001";
  public static final String BAD_REQUEST_CODE = "DLM000002";

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ServiceMessage> handleNotFoundException(NotFoundException notFoundException, WebRequest webRequest) {
    // @formatter:off
    ServiceMessage serviceMessage = new ServiceMessageBuilder().withCode(NOT_FOUND_CODE).withLocale(webRequest.getLocale())
        .withLocalizedMessage(notFoundException.getLocalizedMessage()).withTimestamp(LocalTime.now().toNanoOfDay())
        .withSupportInformation(webRequest.getDescription(false)).build();
    // @formatter:on

    return new ResponseEntity<>(serviceMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ServiceMessage> handleBadRequestException(BadRequestException badRequestException, WebRequest webRequest) {
    // @formatter:off
    ServiceMessage serviceMessage = new ServiceMessageBuilder().withCode(BAD_REQUEST_CODE).withLocale(webRequest.getLocale())
        .withLocalizedMessage(badRequestException.getLocalizedMessage()).withTimestamp(LocalTime.now().toNanoOfDay())
        .withSupportInformation(webRequest.getDescription(false)).build();
    // @formatter:on

    return new ResponseEntity<>(serviceMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ServiceMessage> handleGenericException(Exception exception, WebRequest webRequest) {
    // @formatter:off
    ServiceMessage serviceMessage = new ServiceMessageBuilder().withCode("DLM000003").withLocale(webRequest.getLocale())
        .withLocalizedMessage(exception.getLocalizedMessage()).build();
    // @formatter:on

    return new ResponseEntity<>(serviceMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<List<ServiceMessage>> handleServiceException(ServiceException exception) {
    return new ResponseEntity<>(exception.getServiceMessages(), HttpStatus.BAD_REQUEST);
  }
}
