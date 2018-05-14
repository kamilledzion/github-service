package pl.allegro.model;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

public class GithubRepository {

  private String name;
  private String fullName;
  private String description;
  private String cloneUrl;
  private int starts;
  private LocalDateTime createdAt;

  public GithubRepository(@JsonProperty("name") String name,
      @JsonProperty("full_name") String fullName, @JsonProperty("description") String description,
      @JsonProperty("clone_url") String cloneUrl, @JsonProperty("stargazers_count") int starts,
      @JsonProperty("created_at") String createdAt) {
    this.name = name;
    this.fullName = fullName;
    this.description = description;
    this.cloneUrl = cloneUrl;
    this.starts = starts;
    this.createdAt = parse(createdAt, ISO_DATE_TIME);
  }

  @JsonIgnore
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ApiModelProperty("Full name of repository")
  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  @ApiModelProperty("Repository description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @ApiModelProperty("Repository clone URL")
  @JsonProperty("cloneUrl")
  public String getCloneUrl() {
    return cloneUrl;
  }

  public void setCloneUrl(String cloneUrl) {
    this.cloneUrl = cloneUrl;
  }

  @ApiModelProperty("Stars amount")
  public int getStarts() {
    return starts;
  }

  public void setStarts(int starts) {
    this.starts = starts;
  }

  @JsonIgnore
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @ApiModelProperty("Date and time of creation")
  @JsonProperty("createdAt")
  public String getCreatedAtAsString() {
    return nonNull(createdAt) ? createdAt.format(ISO_DATE_TIME) : null;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}