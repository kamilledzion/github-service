package pl.allegro.service;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;

import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.allegro.model.GithubRepository;

@Service
public class GithubRepositoryService implements RepositoryService<GithubRepository> {

  private final Logger logger = LoggerFactory.getLogger(GithubRepositoryService.class);

  private static final String USERS_PATH = "users";
  private static final String REPOS_PATH = "repos";
  private static final String PATH_SEPARATOR = "/";

  @Value("${githubURL}")
  private String githubURL;
  private RestTemplate restTemplate;

  public GithubRepositoryService() {
    this.restTemplate = new RestTemplate();
  }

  public Optional<GithubRepository> getRepositoryDetails(String owner, String repositoryName) {
    logger
        .debug("Getting repository details for owner: {} and repositoryName: {} from Github", owner,
            repositoryName);

    GithubRepository[] result;
    try {
      result = restTemplate
          .getForObject(githubURL.concat(PATH_SEPARATOR)
                  .concat(USERS_PATH).concat(PATH_SEPARATOR)
                  .concat(owner).concat(PATH_SEPARATOR)
                  .concat(REPOS_PATH),
              GithubRepository[].class);
    } catch (Exception e) {
      logger.error("Error while accessing github repository, owner: {}", owner, e);

      return empty();
    }

    return asList(result)
        .parallelStream()
        .filter(Objects::nonNull)
        .filter(repo -> repositoryName.equals(repo.getName()))
        .findAny();
  }
}