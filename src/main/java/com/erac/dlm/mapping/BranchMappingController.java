package com.erac.dlm.mapping;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.erac.rentalclients.rest.client.api.exception.BadRequestException;
import com.erac.rentalclients.rest.client.api.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;

@RestController
public class BranchMappingController {

  @Autowired
  private RetrieveMappingService retrieveMappingService;

  @ApiOperation(value = "Returns the controlling location of a given DR branch")
  @GetMapping(path = "api/mapping/{br_ps_org_id}/controlling", produces = "application/json")
  public Location getControllingMapping(@PathVariable(value = "br_ps_org_id") final String branchPeopleSoftOrgId, @RequestHeader(value = "Ehi-Locale") final Locale locale)
      throws BadRequestException, NotFoundException {

    return retrieveMappingService.retrieveControllingMapping(branchPeopleSoftOrgId, locale);
  }

  @ApiOperation(value = "Returns the physical location for a given DR branch")
  @GetMapping(path = "api/mapping/{br_ps_org_id}/physical", produces = "application/json")
  public Location getPhysicalMapping(@PathVariable(value = "br_ps_org_id") final String branchPeopleSoftOrgId, @RequestHeader(value = "Ehi-Locale") final Locale locale)
      throws BadRequestException, NotFoundException {
    return retrieveMappingService.retrievePhysicalMapping(branchPeopleSoftOrgId, locale);
  }
}
