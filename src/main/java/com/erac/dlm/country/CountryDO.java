package com.erac.dlm.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.erac.dlm.domain.AuditableEntity;

@Entity
@Table(name = "dlm_cntry")
public class CountryDO extends AuditableEntity {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(nullable = false, name = "cntry_iso_cde", columnDefinition = "varchar(3)")
  private String countryIsoCode;

  @Column(nullable = false, name = "cntry_dsc", columnDefinition = "varchar(80)")
  private String countryDesc;

  public String getCountryIsoCode() {
    return countryIsoCode;
  }

  public void setCountryIsoCode(String countryIsoCode) {
    this.countryIsoCode = countryIsoCode;
  }

  public String getCountryDesc() {
    return countryDesc;
  }

  public void setCountryDesc(String countryDesc) {
    this.countryDesc = countryDesc;
  }

}
