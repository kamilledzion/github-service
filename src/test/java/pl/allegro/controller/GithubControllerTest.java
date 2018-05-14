package pl.allegro.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {

  private static final String DEFAULT_PATH = "/repositories";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getRepositoryDetailsShouldReturnData() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(DEFAULT_PATH.concat("/limak2/github-service"))
            .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.fullName", is("limak2/github-service")))
        .andExpect(jsonPath("$.description ", nullValue()))
        .andExpect(jsonPath("$.cloneUrl ", is("https://github.com/limak2/github-service.git")))
        .andExpect(jsonPath("$.starts ", is(0)))
        .andExpect(jsonPath("$.createdAt ", is("2018-05-14T07:00:54")));
  }

  @Test
  public void getRepositoryDetailsShouldReturnNotFoundForIncorrectOwner() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(DEFAULT_PATH.concat("/limak2asdf123/github-service"))
            .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getRepositoryDetailsShouldReturnNotFoundForIncorrectRepositoryName() throws
      Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get(DEFAULT_PATH.concat("/limak2/github-service123"))
            .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());
  }
}