package pl.github.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.github.model.GithubRepository;

@Service
public class GithubRepositoryService implements RepositoryService {

  private final Logger logger = LoggerFactory.getLogger(GithubRepositoryService.class);

  private static final String REPOS_PATH = "repos";
  private static final String PATH_SEPARATOR = "/";

  private RestTemplate restTemplate;
  private String githubURL;

  @Autowired
  public GithubRepositoryService(RestTemplate restTemplate,
      @Value("${githubURL}") String githubURL) {

    this.restTemplate = restTemplate;
    this.githubURL = githubURL;
  }

  public GithubRepository getRepositoryDetails(String owner, String repositoryName) {
    logger.debug("Getting repository: {} details for owner: {} from github: {}",
        repositoryName, owner, githubURL);

    return restTemplate.getForObject(createURL(owner, repositoryName), GithubRepository.class);
  }

  private String createURL(String owner, String repositoryName) {
    return githubURL + PATH_SEPARATOR
        + REPOS_PATH + PATH_SEPARATOR
        + owner + PATH_SEPARATOR
        + repositoryName;
  }
}