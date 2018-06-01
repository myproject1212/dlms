package com.erac.dlm.mapping;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.erac.testing.utils.generator.generic.GenericClassGenerator;
import com.erac.testing.utils.random.RandomValues;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class UT_BranchMappingController {
  @Autowired
  private WebApplicationContext wac;
  @Autowired
  private RetrieveMappingService retrieveMappingService;

  private MockMvc mockMvc;
  private GenericClassGenerator generator;
  private ObjectMapper mapper = new ObjectMapper();

  @Configuration
  @ComponentScan("com.erac.dlm.mapping")
  @EnableWebMvc
  public static class TestConfiguration extends WebMvcConfigurationSupport {
    @Bean
    public RetrieveMappingService retrieveMappingService() {
      return mock(RetrieveMappingService.class);
    }
  }

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    reset(this.retrieveMappingService);

    generator = new GenericClassGenerator(true);
    generator.addCustomGenerator(byte[].class, () -> {
      final byte[] byteArray = new byte[RandomValues.INTEGER.randomPositiveInclusive(10)];
      for (int i = 0; i < byteArray.length; i++) {
        byteArray[i] = RandomValues.BYTE.randomPrimitive();
      }
      return byteArray;
    });
  }

  @Test
  public void getControllingMapping()
      throws Exception {
    String branchPeopleSoftOrgId = RandomValues.STRING.randomAlphanumeric();
    Location controllingLocation = generator.generate(Location.class);

    when(retrieveMappingService.retrieveControllingMapping(branchPeopleSoftOrgId, Locale.US)).thenReturn(controllingLocation);

    // @formatter:off
    final MvcResult result =
      mockMvc
          .perform(
              get("/api/mapping/{br_ps_org_id}/controlling", branchPeopleSoftOrgId).accept("application/json").header("Ehi-Locale", Locale.US))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
          .andReturn();
    // @formatter:on

    final String expected = mapper.writeValueAsString(controllingLocation);
    final String actual = result.getResponse().getContentAsString();
    assertEquals(expected, actual);
  }

  @Test
  public void getPhysicalMapping()
      throws Exception {
    String branchPeopleSoftOrgId = RandomValues.STRING.randomAlphanumeric();
    Location physicalLocation = generator.generate(Location.class);

    when(retrieveMappingService.retrievePhysicalMapping(branchPeopleSoftOrgId, Locale.US)).thenReturn(physicalLocation);

    // @formatter:off
    final MvcResult result =
      mockMvc
          .perform(
              get("/api/mapping/{br_ps_org_id}/physical", branchPeopleSoftOrgId).accept("application/json").header("Ehi-Locale", Locale.US))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
          .andReturn();
    // @formatter:on

    final String expected = mapper.writeValueAsString(physicalLocation);
    final String actual = result.getResponse().getContentAsString();
    assertEquals(expected, actual);
  }

}
