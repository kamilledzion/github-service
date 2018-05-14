package pl.allegro.controller;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.model.GithubRepository;
import pl.allegro.service.RepositoryService;

@Api(value = "github-controller", description = "Operations available on Github repository.")
@ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully retrieved details."),
    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found.")}
)
@RestController
@RequestMapping("repositories")
public class GithubController {

  private final Logger logger = LoggerFactory.getLogger(GithubController.class);

  private RepositoryService repositoryService;

  @Autowired
  public GithubController(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

  @ApiOperation(value = "Get Github repository details.")
  @RequestMapping(value = "/{owner}/{repository-name}", method = GET)
  public ResponseEntity<GithubRepository> getRepositoryDetails(
      @PathVariable("owner") String owner,
      @PathVariable("repository-name") String repositoryName) {

    logger.debug("Getting repository details for owner: {} and repositoryName: {}", owner,
        repositoryName);

    Optional<GithubRepository> result = repositoryService
        .getRepositoryDetails(owner, repositoryName);

    return result.isPresent() ?
        ok(result.get())
        : notFound().build();
  }
}