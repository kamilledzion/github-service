package pl.allegro.service;

import java.util.Optional;

public interface RepositoryService<T> {

  Optional<T> getRepositoryDetails(String owner, String repositoryName);
}