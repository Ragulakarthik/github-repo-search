package com.github_repo_search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github_repo_search.entity.RepositoryEntity;
import com.github_repo_search.repository.RepositoryRepo;

@Service
public class RepositoryService {

	@Autowired
	private RepositoryRepo repositoryRepo;

	public List<RepositoryEntity> getRepositories(String language, int minStars, String sort) {
		Sort sortBy = Sort.by(Sort.Direction.ASC, sort);
		return repositoryRepo.findByLanguageAndStarsGreaterThanEqual(language, minStars, sortBy);
	}

	public void saveRepositories(List<RepositoryEntity> repositories) {
		for (RepositoryEntity repo : repositories) {
			repositoryRepo.save(repo);
		}
	}
}
