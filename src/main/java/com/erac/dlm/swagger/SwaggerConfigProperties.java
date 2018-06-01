package com.erac.dlm.swagger;

import java.util.Map;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfigProperties {

  private String appName;

  private String description;

  @URL
  private String infoUrl;

  private String contactName;

  private String contactEmail;

  private String apiVersion;

  private Map<String, Map<String, String>> apiDocs;

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInfoUrl() {
    return infoUrl;
  }

  public void setInfoUrl(String infoUrl) {
    this.infoUrl = infoUrl;
  }

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public Map<String, Map<String, String>> getApiDocs() {
    return apiDocs;
  }

  public void setApiDocs(Map<String, Map<String, String>> apiDocs) {
    this.apiDocs = apiDocs;
  }

}
