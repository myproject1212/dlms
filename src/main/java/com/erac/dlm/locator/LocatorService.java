package com.erac.dlm.locator;

import com.erac.dlm.mapping.Location;

public interface LocatorService {
  Location getLocationByPsIorgId(String psIorgId);
}
