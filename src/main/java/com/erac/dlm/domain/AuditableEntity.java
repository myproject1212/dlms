package com.erac.dlm.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
@EntityListeners(value = { AuditEntityListener.class })
public class AuditableEntity {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  public static final String RECORD_STATUS_DELETED = "D";
  public static final String RECORD_STATUS_ACTIVE = "A";

  @Column(nullable = false, columnDefinition = "VARCHAR(30) default 'USER'", updatable = false)
  private String createdBy;

  @Column(nullable = false, columnDefinition = "VARCHAR(256) default 'DLM'", updatable = false)
  private String createModule;

  @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar createTimestamp;

  @Column(nullable = false, columnDefinition = "VARCHAR(1) default 'A'")
  private String recordStatus = RECORD_STATUS_ACTIVE;

  @Column(columnDefinition = "VARCHAR(30)")
  private String updatedBy;

  @Column(columnDefinition = "VARCHAR(256)")
  private String updateModule;

  @Column(columnDefinition = "TIMESTAMP NULL DEFAULT NULL")
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar updateTimestamp;

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreateModule() {
    return createModule;
  }

  public void setCreateModule(String createModule) {
    this.createModule = createModule;
  }

  public Calendar getCreateTimestamp() {
    return createTimestamp;
  }

  public void setCreateTimestamp(Calendar createTimestamp) {
    this.createTimestamp = createTimestamp;
  }

  public String getRecordStatus() {
    return recordStatus;
  }

  public void setRecordStatus(String recordStatus) {
    this.recordStatus = recordStatus;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public String getUpdateModule() {
    return updateModule;
  }

  public void setUpdateModule(String updateModule) {
    this.updateModule = updateModule;
  }

  public Calendar getUpdateTimestamp() {
    return updateTimestamp;
  }

  public void setUpdateTimestamp(Calendar updateTimestamp) {
    this.updateTimestamp = updateTimestamp;
  }
}
