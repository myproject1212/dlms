package com.erac.dlm.restframework.interceptor;

import static com.erac.dlm.restframework.domain.CommonErrors.ACCEPT_HEADER_INVALID;
import static com.erac.dlm.restframework.domain.CommonErrors.AUTHORIZATION_HEADER_INVALID;
import static com.erac.dlm.restframework.domain.CommonErrors.EHI_CALLER_ID_HEADER_INVALID;
import static com.erac.dlm.restframework.domain.CommonErrors.EHI_CALLING_APP_HEADER_INVALID;
import static com.erac.dlm.restframework.domain.CommonErrors.EHI_CALLING_APP_HEADER_LENGTH_INVALID;
import static com.erac.dlm.restframework.domain.CommonErrors.EHI_LOCALE_HEADER_INVALID;
import static com.erac.dlm.restframework.domain.CommonErrors.EHI_SPAN_ID_HEADER_INVALID;
import static com.erac.dlm.restframework.domain.CommonErrors.EHI_TRACE_ID_HEADER_INVALID;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.ACCEPT;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.AUTHORIZATION;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.CALLER_ID;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.CALLING_APPLICATION;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.CALLING_APPLICATION_MAX_LENGTH;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.LOCALE;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.SPAN_ID;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.TRACE_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.erac.dlm.restframework.request.RequestContext;
import com.erac.rentalclients.rest.client.api.exception.ServiceException;

@RunWith(MockitoJUnitRunner.class)
public class UT_HeaderValidationInterceptor {
  private static final String CALLER_ID_HEADER = "caller";
  private static final String TRACE_ID_HEADER = "trace";
  private static final String SPAN_ID_HEADER = "span";
  private static final String LOCALE_HEADER = "eng_CAN";
  private static final String ACCEPT_HEADER = "Accept";
  private static final String CALLING_APP_HEADER = "Calling App";
  private static final String AUTH_HEADER = "Auth token";

  @InjectMocks
  private HeaderValidationInterceptor underTest;

  @Mock
  private MessageSource messageSource;

  @Mock
  private RequestContext requestContext;

  @Mock
  private HandlerInterceptorAdapter handlerInterceptorAdapter;

  private MockHttpServletRequest httpRequest;
  private HttpServletResponse httpResponse;

  @Before
  public void setUp()
      throws Exception {
    httpRequest = new MockHttpServletRequest();
    httpRequest.setMethod("GET");
    httpRequest.setServletPath("/api/");
    httpResponse = new MockHttpServletResponse();
  }

  @Test
  public void preHandleHappyPath()
      throws Exception {
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);

    assertTrue(underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter));
  }

  @Test
  public void preHandleAcceptHeaderBlank()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, "");
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }

    assertEquals(ACCEPT_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleCallingApplicationHeaderBlank()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }
    assertEquals(EHI_CALLING_APP_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleCallingApplicationHeaderLengthGreaterThanMaxLength()
      throws Exception {
    String errorCode = null;
    final String callingApp = RandomStringUtils.randomAlphanumeric(CALLING_APPLICATION_MAX_LENGTH + 1);
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, callingApp);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }

    assertEquals(EHI_CALLING_APP_HEADER_LENGTH_INVALID, errorCode);

  }

  @Test
  public void preHandleAuthorizationHeaderBlank()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }

    assertEquals(AUTHORIZATION_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleLocaleHeaderBlank()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }

    assertEquals(EHI_LOCALE_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleMalformedLocaleHeader()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);
    httpRequest.addHeader(LOCALE, "badly_formed_locale");

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }

    assertEquals(EHI_LOCALE_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleCallerIdHeaderBlank()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }

    assertEquals(EHI_CALLER_ID_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleSpanIdHeaderBlank()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);
    httpRequest.addHeader(TRACE_ID, TRACE_ID_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }
    assertEquals(EHI_SPAN_ID_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleTraceIdHeaderBlank()
      throws Exception {
    String errorCode = null;
    httpRequest.addHeader(ACCEPT, ACCEPT_HEADER);
    httpRequest.addHeader(CALLING_APPLICATION, CALLING_APP_HEADER);
    httpRequest.addHeader(AUTHORIZATION, AUTH_HEADER);
    httpRequest.addHeader(CALLER_ID, CALLER_ID_HEADER);
    httpRequest.addHeader(SPAN_ID, SPAN_ID_HEADER);
    httpRequest.addHeader(LOCALE, LOCALE_HEADER);

    try {
      underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter);
    }
    catch (ServiceException se) {
      errorCode = se.getServiceMessages().get(0).getCode();
    }

    assertEquals(EHI_TRACE_ID_HEADER_INVALID, errorCode);
  }

  @Test
  public void preHandleAllowInfoCalls()
      throws Exception {
    httpRequest.setServletPath("/info");
    assertTrue(underTest.preHandle(httpRequest, httpResponse, handlerInterceptorAdapter));
  }
}
