package com.erac.dlm.mapping;

import java.util.Locale;

import com.erac.rentalclients.rest.client.api.exception.BadRequestException;
import com.erac.rentalclients.rest.client.api.exception.NotFoundException;

public interface RetrieveMappingService {
  Location retrieveControllingMapping(String branchPeopleSoftOrgId, Locale locale)
      throws BadRequestException, NotFoundException;

  Location retrievePhysicalMapping(String branchPeopleSoftOrgId, Locale locale)
      throws BadRequestException, NotFoundException;
}
