package pl.github;

import static java.time.LocalDateTime.of;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.github.model.GithubRepository;

@Component
public final class TestGithubRepositoryGenerator {

  public static final String OWNER = "kamil";
  public static final String REPOSITORY_NAME = "repo";
  public static final String PATH = "/repositories/" + OWNER + "/" + REPOSITORY_NAME;

  @Autowired
  private ObjectMapper objectMapper;

  public GithubRepository getGithubRepository() {
    return new GithubRepository(
        "repo",
        "kamil/repo",
        null,
        "https://github.com/kamil/repo.git",
        0,
        of(2018, 1, 2, 1, 2, 3));
  }

  public String getGithubRepositoryAsString() throws JsonProcessingException {
    return objectMapper.writeValueAsString(getGithubRepository());
  }
}