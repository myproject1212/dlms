package com.erac.dlm.restframework.domain;

/**
 * All errors and warnings within the Delete Logistics Mapping (DLM) environment are designated using
 * a common naming structure.  This allows every error to be isolated to the service or component
 * of the application.
 * <p>
 * Each designator contains the following:
 * <ul>
 * <li><strong>SYSTEM</strong> - A three (3) character identifier designating the portion of the
 * system in which the error or warning was generated.  This is the highest level division within
 * the error designator and should indicate the application or framework within which the message
 * is generated.</li>
 * <li><strong>CATEGORY</strong> - A three (3) character identifier designating the message
 * category.  This should be used to indicate the service or processing layer within which the
 * message is generated.</li>
 * <li><strong>ID</strong> - A five (5) digit positive, zero prefixed integer value uniquely
 * identifying the message within the combination of SYSTEM and CATEGORY.</li>
 * </ul>
 * <p>
 * An additional value indicating the message severity is included in message designators but is
 * not part of the unique identification for the message
 * <ul>
 * <li><strong>SEVERITY</strong> - A one (1) character identifier designating the importance of
 * the error message to the action being performed.  Allowable values are:
 * <ul>
 * <li><strong>I</strong> - Informational message.  These are generated to provide additional
 * information without indicating an issue during processing.</li>
 * <li><strong>W</strong> - Warning message.  Warning messages are generated to indicate an
 * abnormal condition existed during processing but the request could be successfully processed.
 * </li>
 * <li><strong>E</strong> - Error message. These messages indicate a severe condition existed
 * during processing which prohibited the service from properly completing the requested
 * action.</li>
 * </ul>
 * </li>
 * </ul>
 * <p>
 * Message designator examples:
 * <p>
 * <strong>DLMCMN00000I</strong> is used to identify successful processing
 * <ul>
 * <li><strong>SYSTEM => DLM</strong> - The message was generated within the DLM Service Framework</li>
 * <li><strong>CATEGORY => CMN</strong> - The message was generated within the common layers of
 * the Framework or is a general purpose message shared across applications and services.</li>
 * <li><strong>ID => 00000</strong> - The message id is 0 (positive, zero prefixed)</li>
 * <li><strong>SEVERITY => I</strong> - The message is an informational message.</li>
 * </ul>
 * <p>
 * <strong>DLMCMN99999E</strong> is used to identify an unknown error message.  This is a special
 * error message used when a message designator cannot be found within the message resources.
 * <ul>
 * <li><strong>SYSTEM => DLM</strong> - The message was generated within the DLM Service Framework</li>
 * <li><strong>CATEGORY => CMN</strong> - The message was generated within the common layers of
 * the Framework or is a general purpose message shared across applications and services.</li>
 * <li><strong>ID => 99999</strong> - The message id is 99999</li>
 * <li><strong>SEVERITY => E</strong> - The message is an Error message.</li>
 * </ul>
 */
public interface CommonErrors {

  /**
   * Special error message used when an attempt to load a resource message by designator fails.
   * This is a special indicator identifying missing messages.
   */
  public static String UNKNOWN_ERROR = "DLMCMN99999E";

  /* Standard Error Messages */
  public static String REQUEST_SUCCESSFUL = "DLMCMN00000I";

  /* Header validation */
  /**
   * Ehi-Caller-Id is required
   */
  public static final String EHI_CALLER_ID_HEADER_INVALID = "DLMCMN00001E";

  /**
   * Ehi-Locale is required
   */
  public static final String EHI_LOCALE_HEADER_INVALID = "DLMCMN00002E";

  /**
   * Ehi-Calling-Application is required
   */
  public static final String EHI_CALLING_APP_HEADER_INVALID = "DLMCMN00003E";

  /**
   * Authorization is required
   */
  public static final String AUTHORIZATION_HEADER_INVALID = "DLMCMN00004E";

  /**
   * Accept is required
   */
  public static final String ACCEPT_HEADER_INVALID = "DLMCMN00005E";

  /**
   * Ehi-Span-Id is required
   */
  public static final String EHI_SPAN_ID_HEADER_INVALID = "DLMCMN00006E";

  /**
   * Ehi-Trace-Id is required
   */
  public static final String EHI_TRACE_ID_HEADER_INVALID = "DLMCMN00007E";

  /**
   * Max length exceeded for Ehi-Calling-Application
   */
  public static final String EHI_CALLING_APP_HEADER_LENGTH_INVALID = "DLMCMN00008E";

  /**
   * Max length exceeded for Ehi-Caller-Id
   */
  public static final String EHI_CALLER_ID_HEADER_LENGTH_INVALID = "DLMCMN00009E";

  /**
   * Security credentials failed validation.
   */
  public static final String SECURITY_CREDENTIALS_INVALID = "DLMCMN00010E";

  /**
   * No record found
   */
  public static final String NO_RECORD_FOUND = "DLMCMN00011E";

  /**
   * Field contains invalid characters
   */
  public static final String INVALID_CHARACTERS = "DLMCMN00012E";

  /**
   * Security credentials failed validation
   */
  public static final String SECURITY_FAILED = "DLMCMN00013E";

  /**
   * Malformed request
   */
  public static final String MALFORMED_REQUEST = "DLMCMN00014E";

  /**
   * Internal error
   */
  public static final String INTERNAL_ERROR = "DLMCMN00015E";

  /**
   * Malformed URL
   */
  public static final String MALFORMED_URL = "DLMCMN00016E";

  /**
   * Rental branch is required
   */
  public static final String RENTAL_BRANCH_REQUIRED_CODE = "DLMCMN00017E";

  /**
   * No Mapping found
   */
  public static final String NO_MAPPING_FOUND_CODE = "DLMCMN00018E";

  /**
   * Locator did not return a location
   */
  public static final String NO_ACTIVE_LOCATION_CODE = "DLMCMN00019E";
}