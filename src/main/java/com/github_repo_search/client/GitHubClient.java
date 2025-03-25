package com.github_repo_search.client;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.github_repo_search.entity.RepositoryEntity;

@Component
public class GitHubClient {

	private static final String GITHUB_API_URL = "https://api.github.com/search/repositories?q=%s+language:%s&sort=%s";

	public List<RepositoryEntity> searchRepositories(String query, String language, String sort) {
		String url = String.format(GITHUB_API_URL, query, language, sort);

		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> response = restTemplate.getForObject(url, Map.class);

		List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

		return items.stream().map(this::mapToEntity).collect(Collectors.toList());
	}

	private RepositoryEntity mapToEntity(Map<String, Object> item) {
		return RepositoryEntity.builder().id(Long.parseLong(item.get("id").toString())).name((String) item.get("name"))
				.description((String) item.get("description"))
				.owner(((Map<String, String>) item.get("owner")).get("login")).language((String) item.get("language"))
				.stars((Integer) item.get("stargazers_count")).forks((Integer) item.get("forks_count"))
				.lastUpdated(java.time.Instant.parse((String) item.get("updated_at"))).build();
	}
}
