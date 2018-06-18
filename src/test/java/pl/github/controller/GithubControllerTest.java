package pl.github.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.github.TestGithubRepositoryGenerator.OWNER;
import static pl.github.TestGithubRepositoryGenerator.PATH;
import static pl.github.TestGithubRepositoryGenerator.REPOSITORY_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import pl.github.TestGithubRepositoryGenerator;
import pl.github.model.ErrorResponse;
import pl.github.service.GithubRepositoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {

  @Autowired
  private TestGithubRepositoryGenerator githubRepositoryGenerator;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private GithubRepositoryService githubRepositoryService;

  @Test
  public void getRepositoryDetailsShouldReturnData() throws Exception {
    when(githubRepositoryService.getRepositoryDetails(OWNER, REPOSITORY_NAME))
        .thenReturn(githubRepositoryGenerator.getGithubRepository());

    this.mockMvc
        .perform(get(PATH)
            .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().string(is(githubRepositoryGenerator.getGithubRepositoryAsString())));
  }

  @Test
  public void getRepositoryDetailsShouldReturnNotFoundForIncorrectRepositoryName() throws
      Exception {

    when(githubRepositoryService.getRepositoryDetails(OWNER, REPOSITORY_NAME))
        .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

    this.mockMvc
        .perform(get(PATH)
            .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getRepositoryDetailsShouldReturnInternalServerErrorForUnexpectedError() throws
      Exception {

    when(githubRepositoryService.getRepositoryDetails(OWNER, REPOSITORY_NAME))
        .thenThrow(new NullPointerException());

    String errorResponse = objectMapper.writeValueAsString(
        new ErrorResponse(INTERNAL_SERVER_ERROR.value(),
            "Unexpected error!"));

    this.mockMvc
        .perform(
            get(PATH)
                .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string(is(errorResponse)));
  }
}