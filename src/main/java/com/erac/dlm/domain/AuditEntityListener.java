package com.erac.dlm.domain;

import java.util.Calendar;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditEntityListener {

  private static final String MODULE_NAME = "DLM";

  @PrePersist
  public void prePersist(AuditableEntity audit) {

    audit.setCreatedBy(MODULE_NAME);
    audit.setCreateModule(MODULE_NAME);
    audit.setCreateTimestamp(Calendar.getInstance());
  }

  @PreUpdate
  public void preUpdate(AuditableEntity audit) {

    audit.setUpdatedBy(MODULE_NAME);
    audit.setUpdateModule(MODULE_NAME);
    audit.setUpdateTimestamp(Calendar.getInstance());
  }
}
