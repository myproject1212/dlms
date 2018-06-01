package com.erac.dlm.mapping;

import java.io.Serializable;
import java.time.LocalDate;

public class Mapping implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String rentalLocationPsIorgId;
  private String controllingLocationPsIorgId;
  private String physicalLocationPsIorgId;
  private LocalDate effectiveDate;

  public String getRentalLocationPsIorgId() {
    return rentalLocationPsIorgId;
  }

  public void setRentalLocationPsIorgId(String rentalLocationPsIorgId) {
    this.rentalLocationPsIorgId = rentalLocationPsIorgId;
  }

  public String getControllingLocationPsIorgId() {
    return controllingLocationPsIorgId;
  }

  public void setControllingLocationPsIorgId(String controllingLocationPsIorgId) {
    this.controllingLocationPsIorgId = controllingLocationPsIorgId;
  }

  public LocalDate getEffectiveDate() {
    return effectiveDate;
  }

  public void setEffectiveDate(LocalDate effectiveDate) {
    this.effectiveDate = effectiveDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPhysicalLocationPsIorgId() {
    return physicalLocationPsIorgId;
  }

  public void setPhysicalLocationPsIorgId(String physicalLocationPsIorgId) {
    this.physicalLocationPsIorgId = physicalLocationPsIorgId;
  }
}
