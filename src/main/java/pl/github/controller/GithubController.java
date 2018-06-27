package pl.github.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import pl.github.model.ErrorResponse;
import pl.github.service.RepositoryService;

@Api(value = "github-controller", description = "Operations available on Github repository.")
@ApiResponses(value = {
    @ApiResponse(code = 200, message = "Successfully retrieved details."),
    @ApiResponse(code = 400, message = "Details cannot be retrieved due to incorrect request."),
    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found."),
    @ApiResponse(code = 500, message = "The resource you were trying to reach is not available.")}
)
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("repositories")
public class GithubController {

  private final Logger logger = LoggerFactory.getLogger(GithubController.class);

  private final RepositoryService repositoryService;

  @Autowired
  public GithubController(RepositoryService repositoryService) {
    this.repositoryService = repositoryService;
  }

  @ApiOperation(value = "Get Github repository details.")
  @RequestMapping(value = "/{owner}/{repository-name}", method = GET,
      produces = APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity getRepositoryDetails(
      @PathVariable("owner") String owner,
      @PathVariable("repository-name") String repositoryName) {

    try {
      return ok(repositoryService.getRepositoryDetails(owner, repositoryName));

    } catch (HttpClientErrorException e) {
      logger.info("Repository: {} for owner: {} not found.", repositoryName, owner, e);

      return status(e.getStatusCode().value())
          .body(new ErrorResponse(e.getStatusCode().value(), e.getStatusText()));
    } catch (Exception e) {
      logger.error(
          "Unexpected error while accessing repository: {} for owner: {}.",
          repositoryName, owner, e);

      return status(INTERNAL_SERVER_ERROR)
          .body(new ErrorResponse(INTERNAL_SERVER_ERROR.value(), "Unexpected error!"));
    }
  }
}