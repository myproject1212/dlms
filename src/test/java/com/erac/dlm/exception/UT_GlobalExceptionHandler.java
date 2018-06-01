package com.erac.dlm.exception;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;

import com.erac.rentalclients.rest.client.api.exception.BadRequestException;
import com.erac.rentalclients.rest.client.api.exception.NotFoundException;
import com.erac.rentalclients.rest.client.api.exception.ServiceMessage;

@RunWith(MockitoJUnitRunner.class)
public class UT_GlobalExceptionHandler {
  private GlobalExceptionHandler underTest;

  @Mock
  private WebRequest mockWebRequest;

  @Before
  public void setUp()
      throws Exception {
    underTest = new GlobalExceptionHandler();
  }

  @Test
  public void testNotFoundException() {
    NotFoundException notFoundException = new NotFoundException();

    when(mockWebRequest.getLocale()).thenReturn(Locale.US);

    ResponseEntity<ServiceMessage> errorReturn = underTest.handleNotFoundException(notFoundException, mockWebRequest);

    assertEquals(HttpStatus.NOT_FOUND, errorReturn.getStatusCode());
  }

  @Test
  public void testHandleBadRequestException() {
    BadRequestException badRequestExcpetion = new BadRequestException();

    when(mockWebRequest.getLocale()).thenReturn(Locale.US);

    ResponseEntity<ServiceMessage> errorReturn = underTest.handleBadRequestException(badRequestExcpetion, mockWebRequest);

    assertEquals(HttpStatus.BAD_REQUEST, errorReturn.getStatusCode());
  }

  @Test
  public void testHandleGenericException() {
    Exception exception = new RuntimeException();

    when(mockWebRequest.getLocale()).thenReturn(Locale.US);

    ResponseEntity<ServiceMessage> errorReturn = underTest.handleGenericException(exception, mockWebRequest);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorReturn.getStatusCode());
  }



}