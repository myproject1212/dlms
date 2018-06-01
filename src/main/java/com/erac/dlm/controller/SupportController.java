package com.erac.dlm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SupportController {

  private String RETURN_SUPPORT = "support";

  @RequestMapping(value = "/support", method = { RequestMethod.GET })
  public String supportPage() {
    return RETURN_SUPPORT;
  }

}
