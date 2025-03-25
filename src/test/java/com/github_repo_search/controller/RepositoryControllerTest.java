package com.github_repo_search.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github_repo_search.client.GitHubClient;
import com.github_repo_search.entity.RepositoryEntity;
import com.github_repo_search.service.RepositoryService;

@ExtendWith(MockitoExtension.class)
class RepositoryControllerTest {

	private MockMvc mockMvc;

	@Mock
	private GitHubClient gitHubClient;

	@Mock
	private RepositoryService repositoryService;

	@InjectMocks
	private RepositoryController repositoryController;

	private RepositoryEntity repo1;
	private RepositoryEntity repo2;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(repositoryController).build();

		repo1 = new RepositoryEntity();
		repo1.setId(1L);
		repo1.setName("Repo A");
		repo1.setLanguage("Java");
		repo1.setStars(150);
		repo1.setForks(60);

		repo2 = new RepositoryEntity();
		repo2.setId(2L);
		repo2.setName("Repo B");
		repo2.setLanguage("Python");
		repo2.setStars(250);
		repo2.setForks(80);
	}

	@Test
	void testGetRepositories() throws Exception {
		List<RepositoryEntity> repoList = Arrays.asList(repo1, repo2);
		when(repositoryService.getRepositories("Java", 100, "stars")).thenReturn(repoList);

		mockMvc.perform(get("/api/github/repositories").param("language", "Java").param("minStars", "100").param("sort",
				"stars")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("Repo A")).andExpect(jsonPath("$[1].name").value("Repo B"));

		verify(repositoryService, times(1)).getRepositories("Java", 100, "stars");
	}

	@Test
	void testSearchRepositories() throws Exception {
		List<RepositoryEntity> repoList = Arrays.asList(repo1, repo2);
		Map<String, String> request = Map.of("query", "Spring Boot", "language", "Java", "sort", "stars");

		when(gitHubClient.searchRepositories("Spring Boot", "Java", "stars")).thenReturn(repoList);
		doNothing().when(repositoryService).saveRepositories(repoList);

		mockMvc.perform(post("/api/github/search").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Repositories fetched and saved successfully"))
				.andExpect(jsonPath("$.repositories.length()").value(2))
				.andExpect(jsonPath("$.repositories[0].name").value("Repo A"));

		verify(gitHubClient, times(1)).searchRepositories("Spring Boot", "Java", "stars");
		verify(repositoryService, times(1)).saveRepositories(repoList);
	}

	@Test
	void testGetRepositories_EmptyResult() throws Exception {
		when(repositoryService.getRepositories("Go", 50, "forks")).thenReturn(List.of());

		mockMvc.perform(
				get("/api/github/repositories").param("language", "Go").param("minStars", "50").param("sort", "forks"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(0));

		verify(repositoryService, times(1)).getRepositories("Go", 50, "forks");
	}
}
