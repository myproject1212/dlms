package com.erac.dlm.mapping;

import static com.erac.dlm.restframework.domain.CommonErrors.NO_ACTIVE_LOCATION_CODE;
import static com.erac.dlm.restframework.domain.CommonErrors.NO_MAPPING_FOUND_CODE;
import static com.erac.dlm.restframework.domain.CommonErrors.RENTAL_BRANCH_REQUIRED_CODE;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.erac.dlm.locator.LocatorService;
import com.erac.rentalclients.rest.client.api.exception.BadRequestException;
import com.erac.rentalclients.rest.client.api.exception.NotFoundException;

@Service(value = "retrieveMappingService")
public class RetrieveMappingServiceImpl implements RetrieveMappingService {
  public static final String CONTROLLING = "controlling";
  public static final String PHYSICAL = "physical";
  public static final long CONTROLLING_LOCATION_TYPE_ID = 1L;
  public static final long PHYSICAL_LOCATION_TYPE_ID = 2L;

  @Autowired
  private LocatorService locatorService;
  @Autowired
  private MappingDao mappingDao;
  @Autowired
  private MessageSource messageSource;

  @Override
  public Location retrieveControllingMapping(String branchPeopleSoftOrgId, Locale locale)
      throws BadRequestException, NotFoundException {
    return getLocation(branchPeopleSoftOrgId, CONTROLLING, CONTROLLING_LOCATION_TYPE_ID, locale);
  }

  @Override
  public Location retrievePhysicalMapping(String branchPeopleSoftOrgId, Locale locale)
      throws BadRequestException, NotFoundException {
    return getLocation(branchPeopleSoftOrgId, PHYSICAL, PHYSICAL_LOCATION_TYPE_ID, locale);
  }

  private Location getLocation(String branchPeopleSoftOrgId, String mappingType, Long mappingTypeId, Locale locale)
      throws BadRequestException, NotFoundException {
    validateInputExists(branchPeopleSoftOrgId, mappingType, locale);

    Mapping mapping = mappingDao.getByRentalLocationPsIorgIdAndType(branchPeopleSoftOrgId, mappingTypeId);

    Location location;
    if (mapping == null) {
      return handleMappingNotFound(branchPeopleSoftOrgId, mappingType, locale);
    }
    else {
      location = locatorService.getLocationByPsIorgId(mapping.getControllingLocationPsIorgId());
    }

    if(location == null) {
      return handleLocatorFailure(mapping, mappingType, locale);
    }

    return location;
  }

  private void validateInputExists(String branchPeopleSoftOrgId, String mappingType, Locale locale)
      throws BadRequestException {
    if(StringUtils.isBlank(branchPeopleSoftOrgId)) {
      throw new BadRequestException(messageSource, RENTAL_BRANCH_REQUIRED_CODE, "Rental branch is required when searching for {0} mapping.",
          locale, mappingType);
    }
  }

  private Location handleMappingNotFound(String branchPeopleSoftOrgId, String mappingType, Locale locale)
      throws NotFoundException {
    throw new NotFoundException(messageSource, NO_MAPPING_FOUND_CODE, "No {0} mapping found for rental branch {1}",
        locale, mappingType, branchPeopleSoftOrgId);
  }

  private Location handleLocatorFailure(Mapping mapping, String mappingType, Locale locale)
      throws NotFoundException {
    throw new NotFoundException(messageSource, NO_ACTIVE_LOCATION_CODE, "The {0} branch for rental branch {1} could not be found in locator.",
        locale, mappingType, mapping.getControllingLocationPsIorgId());
  }
}
