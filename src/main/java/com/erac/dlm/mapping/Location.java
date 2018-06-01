package com.erac.dlm.mapping;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class Location implements Serializable {
  private static final long serialVersionUID = 1L;

  private String legacyIorgId;
  private Integer iorgId;
  private String peopleSoftOrgId;

  private Location() {
  }

  public Location(String legacyIorgId, Integer iorgId, String peopleSoftOrgId) {
    this.legacyIorgId = legacyIorgId;
    this.iorgId = iorgId;
    this.peopleSoftOrgId = peopleSoftOrgId;
  }

  public String getLegacyIorgId() {
    return legacyIorgId;
  }

  public void setLegacyIorgId(String legacyIorgId) {
    this.legacyIorgId = legacyIorgId;
  }

  public Integer getIorgId() {
    return iorgId;
  }

  public void setIorgId(Integer iorgId) {
    this.iorgId = iorgId;
  }

  public String getPeopleSoftOrgId() {
    return peopleSoftOrgId;
  }

  public void setPeopleSoftOrgId(String peopleSoftOrgId) {
    this.peopleSoftOrgId = peopleSoftOrgId;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
