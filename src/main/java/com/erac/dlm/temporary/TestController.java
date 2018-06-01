package com.erac.dlm.temporary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class TestController {

  private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

  @ApiOperation(value = "${swagger.apiDocs.hello.description}")
  @RequestMapping(value = "api/hello", method = RequestMethod.GET)
  public ResponseEntity<String> hello() {
    LOG.warn("testing log functionality.");
    return new ResponseEntity<>("The application is running.", HttpStatus.OK);
  }
}
