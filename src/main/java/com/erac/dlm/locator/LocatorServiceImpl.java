package com.erac.dlm.locator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erac.dlm.mapping.Location;
import com.erac.integration.services.locator.commontypes.LocationType;
import com.erac.integration.services.locatorlocationsweb.GetLocationByIdRQ;
import com.erac.integration.services.locatorlocationsweb.GetLocationByIdRS;
import com.erac.rsi.spring.RsiServiceClient;

@Service(value = "locatorService")
public class LocatorServiceImpl implements LocatorService {
  @Autowired
  private RsiServiceClient rsiServiceClient;

  @Override
  public Location getLocationByPsIorgId(String psIorgId) {
    GetLocationByIdRQ getLocationByIdRQ = new GetLocationByIdRQ();
    getLocationByIdRQ.setLocationId(psIorgId);

    GetLocationByIdRS getLocationByIdRS = rsiServiceClient.executeAndCheckResponse(getLocationByIdRQ, GetLocationByIdRS.class, true)
        .getOutput();

    LocationType locationType = getLocationByIdRS.getLocation();

    return new Location(locationType.getLegacyHierarchy().getBranch().getId(), Integer.valueOf(locationType.getIorgId()), locationType.getPeopleSoftId());
  }
}
