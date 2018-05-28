package pl.github.service;

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static pl.github.TestGithubRepositoryGenerator.OWNER;
import static pl.github.TestGithubRepositoryGenerator.REPOSITORY_NAME;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.github.TestGithubRepositoryGenerator;
import pl.github.model.GithubRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GithubRepositoryServiceTest {

  @Autowired
  private TestGithubRepositoryGenerator githubRepositoryGenerator;
  @Mock
  private RestTemplate restTemplate;
  @InjectMocks
  private GithubRepositoryService githubRepositoryService;

  @Test
  public void getRepositoryDetailsShouldReturnData() {
    GithubRepository githubRepository = githubRepositoryGenerator.getGithubRepository();

    when(restTemplate.getForObject(anyString(), any())).thenReturn(githubRepository);

    Optional<GithubRepository> response = githubRepositoryService
        .getRepositoryDetails(OWNER, REPOSITORY_NAME);

    assertThat(response.get(), is(githubRepository));
  }

  @Test
  public void getRepositoryDetailsShouldReturnEmptyOptionalForNotExistingRepo() {
    when(restTemplate.getForObject(anyString(), any()))
        .thenThrow(new HttpClientErrorException(NOT_FOUND));

    Optional<GithubRepository> response = githubRepositoryService.getRepositoryDetails(OWNER,
        REPOSITORY_NAME);

    assertThat(response, is(empty()));
  }
}