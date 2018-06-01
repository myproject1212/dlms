package com.erac.dlm.country;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryDO, Long> {

  public CountryDO findByCountryIsoCodeAndRecordStatus(String countryIsoCode, String recordStatus);

}
