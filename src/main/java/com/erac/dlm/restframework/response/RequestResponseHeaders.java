package com.erac.dlm.restframework.response;

import org.springframework.http.HttpHeaders;

public class RequestResponseHeaders {

  /* We use some common Http Headers. */
  public static final String ACCEPT = HttpHeaders.ACCEPT;
  public static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;
  public static final String CONTENT_TYPE = HttpHeaders.CONTENT_TYPE;
  public static final String ETAG_MATCH = HttpHeaders.IF_MATCH;

  /* This is a key part of the security details. */
  public static final String EHI_API_KEY = "Ehi-Api-Key";

  /* We use these to determine how to communicate with the caller. */
  public static final String LOCALE = "Ehi-Locale";
  public static final String CALLER_ID = "Ehi-Caller-Id";
  public static final String CALLING_APPLICATION = "Ehi-Calling-Application";
  public static final String DEVICE_LOCATION_ID = "Ehi-Device-Location-ID";

  /* These are for performance reporting */
  public static final String SERVICE_ELAPSED_MILLIS = "Ehi-Service-Elapsed-Millis";

  /* We use these to track where we are in a chained transaction request. */
  public static final String UNIQUE_ID = "Ehi-Unique-Id";
  public static final String WORKFLOW_ID = "Ehi-Workflow-Id";
  public static final String SPAN_ID = "Ehi-Span-Id";
  public static final String TRACE_ID = "Ehi-Trace-Id";

  /* To be returned in the response */
  public static final String EHI_RESPONSE_SERVICE_HANDLER_VERSION = "ehi-service-handler-version";
  public static final String EHI_RESPONSE_SERVICE_HANDLER_INTERFACE_VERSION = "ehi-service-handler-current-interface-version";
  public static final String EHI_RESPONSE_SERVICE_HANDLER_HOST = "ehi-service-handler-host-instance";
  public static final String EHI_SERVICE_ELAPSED_TIME_MILLIS = "ehi-service-method-elapsed-time";

  /* To be used in populating response header content */
  public static final String PORT_SEPARATOR = ":";
  public static final String MILLIS = "ms";

  /* These are for validations. */
  public static final int CALLING_APPLICATION_MAX_LENGTH = 30;
  public static final int CALLER_ID_MAX_LENGTH = 30;

  /* For specifying JSON request body */
  public static final String APPLICATION_JSON = "application/json";
}

