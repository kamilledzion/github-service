package pl.allegro.service;

import java.util.Optional;

public interface RepositoryService {

  Optional getRepositoryDetails(String owner, String repositoryName);
}