package pl.allegro.model;

import static java.util.Objects.hash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Objects;

public final class GithubRepository {

  private final String name;
  private final String fullName;
  private final String description;
  private final String cloneUrl;
  private final int stars;
  private final LocalDateTime createdAt;

  public GithubRepository(@JsonProperty("name") String name,
      @JsonProperty("full_name") String fullName, @JsonProperty("description") String description,
      @JsonProperty("clone_url") String cloneUrl, @JsonProperty("stargazers_count") int stars,
      @JsonProperty("created_at") LocalDateTime createdAt) {
    this.name = name;
    this.fullName = fullName;
    this.description = description;
    this.cloneUrl = cloneUrl;
    this.stars = stars;
    this.createdAt = createdAt;
  }

  @JsonIgnore
  public String getName() {
    return name;
  }

  @ApiModelProperty("Full name of repository")
  public String getFullName() {
    return fullName;
  }

  @ApiModelProperty("Repository description")
  public String getDescription() {
    return description;
  }

  @ApiModelProperty("Repository clone URL")
  public String getCloneUrl() {
    return cloneUrl;
  }

  @ApiModelProperty("Stars amount")
  public int getStars() {
    return stars;
  }

  @ApiModelProperty("Date and time of creation")
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GithubRepository that = (GithubRepository) o;
    return stars == that.stars &&
        Objects.equals(name, that.name) &&
        Objects.equals(fullName, that.fullName) &&
        Objects.equals(description, that.description) &&
        Objects.equals(cloneUrl, that.cloneUrl) &&
        Objects.equals(createdAt, that.createdAt);
  }

  @Override
  public int hashCode() {

    return hash(name, fullName, description, cloneUrl, stars, createdAt);
  }
}