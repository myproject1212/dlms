package com.erac.dlm.mapping;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.erac.dlm.locator.LocatorService;
import com.erac.rentalclients.rest.client.api.exception.BadRequestException;
import com.erac.rentalclients.rest.client.api.exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UT_RetrieveMappingServiceImpl {
  private static final String MAPPED_PEOPLE_SOFT_ORG_ID = "7654321CBA";
  private static final String RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID = "1234567ABC";
  public static final String LEGACY_IORG_ID = "0101";

  @InjectMocks
  private RetrieveMappingServiceImpl underTest;

  @Mock
  private MappingDao mappingDao;
  @Mock
  private LocatorService locatorService;
  @Mock
  private MessageSource messageSource;

  private Mapping successfulMapping;
  private Location successLocation;

  @Before
  public void setUp()
      throws Exception {
    successfulMapping = new Mapping();
    successLocation = new Location(LEGACY_IORG_ID, 1, MAPPED_PEOPLE_SOFT_ORG_ID);
  }

  @Test
  public void retrieveControllingMappingHappyPath()
      throws BadRequestException, NotFoundException {
    when(mappingDao.getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.CONTROLLING_LOCATION_TYPE_ID))
        .thenReturn(successfulMapping);
    when(locatorService.getLocationByPsIorgId(successfulMapping.getControllingLocationPsIorgId())).thenReturn(successLocation);

    Location result = underTest.retrieveControllingMapping(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, Locale.US);

    assertEquals(MAPPED_PEOPLE_SOFT_ORG_ID, result.getPeopleSoftOrgId());
    verify(mappingDao).getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.CONTROLLING_LOCATION_TYPE_ID);
    verify(locatorService).getLocationByPsIorgId(successfulMapping.getControllingLocationPsIorgId());
  }

  @Test(expected = BadRequestException.class)
  public void retrieveControllingMappingNoRentalLocationSubmitted()
      throws BadRequestException, NotFoundException {
    underTest.retrieveControllingMapping(null, Locale.US);
  }

  @Test(expected = NotFoundException.class)
  public void retrieveControllingMappingNoMappingInDatabase()
      throws BadRequestException, NotFoundException {
    when(mappingDao.getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.CONTROLLING_LOCATION_TYPE_ID))
        .thenReturn(null);

    Location result = underTest.retrieveControllingMapping(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, Locale.US);

    verifyZeroInteractions(locatorService);
  }

  @Test(expected = NotFoundException.class)
  public void retrieveControllingMappingLocatorDoesNotHaveMappedLocation()
      throws BadRequestException, NotFoundException {
    when(mappingDao.getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.CONTROLLING_LOCATION_TYPE_ID))
        .thenReturn(successfulMapping);
    when(locatorService.getLocationByPsIorgId(successfulMapping.getControllingLocationPsIorgId())).thenReturn(null);

    Location result = underTest.retrieveControllingMapping(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, Locale.US);
  }

  @Test
  public void retrievePhysicalMappingHappyPath()
      throws BadRequestException, NotFoundException {
    when(mappingDao.getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.PHYSICAL_LOCATION_TYPE_ID))
        .thenReturn(successfulMapping);
    when(locatorService.getLocationByPsIorgId(successfulMapping.getControllingLocationPsIorgId())).thenReturn(successLocation);

    Location result = underTest.retrievePhysicalMapping(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, Locale.US);

    assertEquals(MAPPED_PEOPLE_SOFT_ORG_ID, result.getPeopleSoftOrgId());
    verify(mappingDao).getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.PHYSICAL_LOCATION_TYPE_ID);
    verify(locatorService).getLocationByPsIorgId(successfulMapping.getPhysicalLocationPsIorgId());
  }

  @Test(expected = BadRequestException.class)
  public void retrievePhysicalMappingNoRentalBranchSubmitted()
      throws BadRequestException, NotFoundException {
    underTest.retrievePhysicalMapping(StringUtils.SPACE, Locale.US);
  }

  @Test(expected = NotFoundException.class)
  public void retrievePhysicalMappingNoMappingInDatabase()
      throws BadRequestException, NotFoundException {
    when(mappingDao.getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.PHYSICAL_LOCATION_TYPE_ID))
        .thenReturn(null);

    Location result = underTest.retrievePhysicalMapping(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, Locale.US);

    verifyZeroInteractions(locatorService);
  }

  @Test(expected = NotFoundException.class)
  public void retrievePhysicalMappingLocatorDoesNotHaveMappedLocation()
      throws BadRequestException, NotFoundException {
    when(mappingDao.getByRentalLocationPsIorgIdAndType(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, RetrieveMappingServiceImpl.PHYSICAL_LOCATION_TYPE_ID))
        .thenReturn(successfulMapping);
    when(locatorService.getLocationByPsIorgId(successfulMapping.getPhysicalLocationPsIorgId())).thenReturn(null);

    Location result = underTest.retrievePhysicalMapping(RENTAL_BRANCH_PEOPLE_SOFT_ORG_ID, Locale.US);
  }

}