package com.erac.dlm.restframework.interceptor;

import static com.erac.dlm.restframework.domain.CommonErrors.*;
import static com.erac.dlm.restframework.response.RequestResponseHeaders.*;
import static org.springframework.util.StringUtils.parseLocaleString;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.erac.arch.inf.common.exception.InvalidCountryException;
import com.erac.arch.inf.common.exception.InvalidLanguageException;
import com.erac.arch.inf.common.util.LocaleUtil;
import com.erac.dlm.restframework.request.RequestContext;
import com.erac.rentalclients.rest.client.api.exception.ServiceException;
import com.erac.rentalclients.rest.client.api.exception.ServiceMessage;
import com.erac.rentalclients.rest.client.api.exception.ServiceMessageBuilder;

@Component
public class HeaderValidationInterceptor extends HandlerInterceptorAdapter {
  private static final Logger LOG = LoggerFactory.getLogger(HeaderValidationInterceptor.class);

  @Autowired
  private RequestContext requestContext;

  @Autowired
  private MessageSource messageSource;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (!StringUtils.contains(request.getServletPath(), "/api/")) {
      LOG.debug("Skipping header validations for non api requests");
      return true;
    }

    List<ServiceMessage> serviceMessages = new ArrayList<>();
    validateSuperPreHandle(request, response, handler, serviceMessages);
    Locale locale = validateEhiLocale(request, serviceMessages);
    requestContext.setLocale(locale);
    validateEhiCallingApplication(request, serviceMessages);
    validateAuthorization(request, serviceMessages);
    validateAccept(request, serviceMessages);
    validateEhiCallerId(request, serviceMessages);
    validateEhiSpanId(request, serviceMessages);
    validateEhiTraceId(request, serviceMessages);

    populateRequestContext(request);

    if (!serviceMessages.isEmpty()) {
      throw new ServiceException(serviceMessages, LocalTime.now().toNanoOfDay());
    }

    return true;
  }

  private void populateRequestContext(HttpServletRequest request) {
    requestContext.setCallingApplication(request.getHeader(CALLING_APPLICATION));
    requestContext.setSpanId(request.getHeader(SPAN_ID));
    requestContext.setTraceId(request.getHeader(TRACE_ID));
    requestContext.setAccept(request.getHeader(ACCEPT));
    requestContext.setEtag(request.getHeader(ETAG_MATCH));
    requestContext.setCallerId(request.getHeader(CALLER_ID));
    requestContext.setDeviceLocationId(request.getHeader(DEVICE_LOCATION_ID));
  }

  private void validateAccept(HttpServletRequest request, List<ServiceMessage> messages) {
    String headerValue = request.getHeader(ACCEPT);
    validateStringNotEmpty(headerValue, ACCEPT_HEADER_INVALID, messages);
  }

  private void validateAuthorization(HttpServletRequest request, List<ServiceMessage> messages) {
    validateStringNotEmpty(request.getHeader(AUTHORIZATION), AUTHORIZATION_HEADER_INVALID, messages);
  }

  private void validateEhiCallerId(HttpServletRequest request, List<ServiceMessage> messages) {
    String value = request.getHeader(CALLER_ID);

    if (validateStringNotEmpty(value, EHI_CALLER_ID_HEADER_INVALID, messages)) {
      validateLength(value, CALLER_ID_MAX_LENGTH, EHI_CALLER_ID_HEADER_LENGTH_INVALID, messages);
    }
  }

  private void validateEhiCallingApplication(HttpServletRequest request, List<ServiceMessage> messages) {
    String value = request.getHeader(CALLING_APPLICATION);

    if (validateStringNotEmpty(value, EHI_CALLING_APP_HEADER_INVALID, messages)) {
      validateLength(value, CALLING_APPLICATION_MAX_LENGTH, EHI_CALLING_APP_HEADER_LENGTH_INVALID, messages);
    }
  }

  private Locale validateEhiLocale(HttpServletRequest request, List<ServiceMessage> messages) {
    String value = request.getHeader(LOCALE);

    if (validateStringNotEmpty(value, EHI_LOCALE_HEADER_INVALID, messages)) {
      return validateLocale(value, EHI_LOCALE_HEADER_INVALID, messages);
    }
    return Locale.US;
  }

  private void validateEhiSpanId(HttpServletRequest request, List<ServiceMessage> messages) {
    validateStringNotEmpty(request.getHeader(SPAN_ID), EHI_SPAN_ID_HEADER_INVALID, messages);
  }

  private void validateEhiTraceId(HttpServletRequest request, List<ServiceMessage> messages) {
    validateStringNotEmpty(request.getHeader(TRACE_ID), EHI_TRACE_ID_HEADER_INVALID, messages);
  }

  private void validateLength(String value, Integer limit, String errorCode, List<ServiceMessage> messages) {
    if (value.length() > limit) {
      messages.add(createServiceMessage(errorCode));
    }
  }

  private Locale validateLocale(String value, String errorCode, List<ServiceMessage> messages) {
    String normalizedLocale = value.replace("-", "_");
    Locale locale = parseLocaleString(normalizedLocale);

    if (null != locale && StringUtils.isNotBlank(locale.getLanguage()) && StringUtils.isNotBlank(locale.getCountry())) {
      try {
        locale = LocaleUtil.getValidLocale(locale.getLanguage().toLowerCase(), locale.getCountry().toUpperCase());

        if (!normalizedLocale.equals(locale.getISO3Language() + "_" + locale.getISO3Country()) &&
            !normalizedLocale.equals(locale.getLanguage() + "_" + locale.getCountry())) {
          messages.add(createServiceMessage(errorCode));
          return Locale.US;
        }
        else {
          return locale;
        }
      }
      catch (InvalidCountryException ice) {
        LOG.info("Invalid country code: " + locale.getCountry(), ice);
      }
      catch (InvalidLanguageException ile) {
        LOG.info("Invalid language code: " + locale.getLanguage(), ile);
      }
    }

    messages.add(createServiceMessage(errorCode));
    return Locale.US;
  }

  private void validateSuperPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler, List<ServiceMessage> messages) {
    try {
      if (!super.preHandle(request, response, handler)) {

        messages.add(createServiceMessage(UNKNOWN_ERROR));
      }
    }
    catch (Exception e) {
      messages.add(createServiceMessage(UNKNOWN_ERROR));
    }
  }

  private boolean validateStringNotEmpty(String value, String errorCode, List<ServiceMessage> messages) {
    if (StringUtils.isBlank(value)) {
      messages.add(createServiceMessage(errorCode));
      return false;
    }
    return true;
  }

  private ServiceMessage createServiceMessage(String errorCode) {
    String messageText = messageSource.getMessage(errorCode, null, requestContext.getLocale());
    // @formatter:off
    return new ServiceMessageBuilder()
        .withCode(errorCode)
        .withLocale(requestContext.getLocale())
        .withLocalizedMessage(messageText)
        .withTimestamp(LocalTime.now().toNanoOfDay())
        .withSeverity(ServiceMessage.ERROR)
        .build();
    // @formatter:on
  }
}

