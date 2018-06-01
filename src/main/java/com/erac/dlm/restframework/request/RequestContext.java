package com.erac.dlm.restframework.request;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestContext {

  // The following fields are used for routing requests
  private String accept;

  // The following fields are used for request processing
  private Locale locale = Locale.US;
  private String etag;

  // The following fields are used for timing requests
  private long executionStartTime;
  private long sla;

  // The following fields are used for tracking requests
  private String callingApplication;
  private String spanId;
  private String traceId;
  private String workflowId;

  private String callerId;
  private String deviceLocationId;

  //This field is provided to the user to have a location to store request scoped data
  private Map<String, Object> customData = new HashMap<>();

  public String getAccept() {
    return accept;
  }

  public void setAccept(String accept) {
    this.accept = accept;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public String getEtag() {
    return etag;
  }

  public void setEtag(String etag) {
    this.etag = etag;
  }

  public long getExecutionStartTime() {
    return executionStartTime;
  }

  public void setExecutionStartTime(long executionStartTime) {
    this.executionStartTime = executionStartTime;
  }

  public long getSla() {
    return sla;
  }

  public void setSla(long sla) {
    this.sla = sla;
  }

  public String getCallingApplication() {
    return callingApplication;
  }

  public void setCallingApplication(String callingApplication) {
    this.callingApplication = callingApplication;
  }

  public String getSpanId() {
    return spanId;
  }

  public void setSpanId(String spanId) {
    this.spanId = spanId;
  }

  public String getTraceId() {
    return traceId;
  }

  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  public String getCallerId() {
    return callerId;
  }

  public void setCallerId(String callerId) {
    this.callerId = callerId;
  }

  public String getDeviceLocationId() {
    return deviceLocationId;
  }

  public void setDeviceLocationId(String deviceLocationId) {
    this.deviceLocationId = deviceLocationId;
  }

  public Map<String, Object> getCustomData() {
    return customData;
  }

  public void setCustomData(Map<String, Object> customData) {
    this.customData = customData;
  }
}