package pl.allegro.controller;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.allegro.TestGithubRepositoryGenerator.OWNER;
import static pl.allegro.TestGithubRepositoryGenerator.PATH;
import static pl.allegro.TestGithubRepositoryGenerator.REPOSITORY_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.allegro.TestGithubRepositoryGenerator;
import pl.allegro.model.ErrorResponse;
import pl.allegro.service.GithubRepositoryService;

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
        .thenReturn(of(githubRepositoryGenerator.getGithubRepository()));

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
        .thenReturn(empty());

    String errorResponse = objectMapper.writeValueAsString(
        new ErrorResponse(NOT_FOUND.value(),
            "Repository: " + REPOSITORY_NAME + " for owner: " + OWNER + " not found."));

    this.mockMvc
        .perform(get(PATH)
            .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound())
        .andExpect(content().string(is(errorResponse)));
  }

  @Test
  public void getRepositoryDetailsShouldReturnInternalServerErrorForUnexpectedError() throws
      Exception {

    when(githubRepositoryService.getRepositoryDetails(OWNER, REPOSITORY_NAME))
        .thenThrow(new NullPointerException());

    String errorResponse = objectMapper.writeValueAsString(
        new ErrorResponse(INTERNAL_SERVER_ERROR.value(),
            "Unexpected error while accessing repository: " + REPOSITORY_NAME
                + " for owner: " + OWNER + "."));

    this.mockMvc
        .perform(
            get(PATH)
                .accept(APPLICATION_JSON_UTF8))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string(is(errorResponse)));
  }
}