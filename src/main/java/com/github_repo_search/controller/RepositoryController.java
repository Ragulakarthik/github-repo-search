package com.github_repo_search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github_repo_search.client.GitHubClient;
import com.github_repo_search.entity.RepositoryEntity;
import com.github_repo_search.service.RepositoryService;

@RestController
@RequestMapping("/api/github")
public class RepositoryController {

	@Autowired
	private GitHubClient gitHubClient;

	@Autowired
	private RepositoryService repositoryService;

	@PostMapping("/search")
	public Map<String, Object> searchRepositories(@RequestBody Map<String, String> request) {
		String query = request.get("query");
		String language = request.get("language");
		String sort = request.get("sort");

		List<RepositoryEntity> repositories = gitHubClient.searchRepositories(query, language, sort);
		repositoryService.saveRepositories(repositories);

		return Map.of("message", "Repositories fetched and saved successfully", "repositories", repositories);
	}

	@GetMapping("/repositories")
	public List<RepositoryEntity> getRepositories(@RequestParam(required = false) String language,
			@RequestParam(required = false, defaultValue = "0") int minStars,
			@RequestParam(required = false, defaultValue = "stars") String sort) {
		return repositoryService.getRepositories(language, minStars, sort);
	}
}
