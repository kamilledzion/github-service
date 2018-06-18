package pl.github.service;

import pl.github.model.GithubRepository;

public interface RepositoryService {

  GithubRepository getRepositoryDetails(String owner, String repositoryName);
}