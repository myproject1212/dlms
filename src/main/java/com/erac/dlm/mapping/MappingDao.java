package com.erac.dlm.mapping;

public interface MappingDao {
  Mapping getByRentalLocationPsIorgIdAndType(String rentalLocationPsIorgId, long locationTypeId);
}
