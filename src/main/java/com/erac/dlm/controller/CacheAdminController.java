package com.erac.dlm.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

@Controller
public class CacheAdminController {
  @Autowired
  private CacheManager cacheManager;

  @RequestMapping(value = "/admincontroller", method = RequestMethod.GET)
  public String getCacheDetails(@RequestParam(name = "cacheName", required = false) String cacheName, Model model) {
    String escapeCacheName = HtmlUtils.htmlEscape(StringUtils.stripToEmpty(cacheName));
    cacheManager.getCache(escapeCacheName);
    List<String> cacheNames = new ArrayList<>(cacheManager.getCacheNames());
    model.addAttribute("cacheNames", cacheNames);
    return "cacheMgmt";
  }

}
