package com.github_repo_search.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.github_repo_search.entity.RepositoryEntity;
import com.github_repo_search.repository.RepositoryRepo;

@ExtendWith(MockitoExtension.class)
class RepositoryServiceTest {

	@Mock
	private RepositoryRepo repositoryRepo;

	@InjectMocks
	private RepositoryService repositoryService;

	private RepositoryEntity repo1, repo2;

	@BeforeEach
	void setUp() {
		repo1 = new RepositoryEntity();
		repo1.setId(1L);
		repo1.setName("Repo A");
		repo1.setLanguage("Java");
		repo1.setStars(100);
		repo1.setForks(50);

		repo2 = new RepositoryEntity();
		repo2.setId(2L);
		repo2.setName("Repo B");
		repo2.setLanguage("Python");
		repo2.setStars(200);
		repo2.setForks(80);
	}

	@Test
	void testGetRepositories_SortByStars() {
		when(repositoryRepo.findByLanguageAndStarsGreaterThanEqual("Java", 50, Sort.by(Sort.Direction.ASC, "stars")))
				.thenReturn(List.of(repo1));

		List<RepositoryEntity> result = repositoryService.getRepositories("Java", 50, "stars");

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Repo A", result.get(0).getName());

		verify(repositoryRepo, times(1)).findByLanguageAndStarsGreaterThanEqual("Java", 50,
				Sort.by(Sort.Direction.ASC, "stars"));
	}

	@Test
	void testGetRepositories_SortByForks() {
		when(repositoryRepo.findByLanguageAndStarsGreaterThanEqual("Python", 100, Sort.by(Sort.Direction.ASC, "forks")))
				.thenReturn(List.of(repo2));

		List<RepositoryEntity> result = repositoryService.getRepositories("Python", 100, "forks");

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Repo B", result.get(0).getName());

		verify(repositoryRepo, times(1)).findByLanguageAndStarsGreaterThanEqual("Python", 100,
				Sort.by(Sort.Direction.ASC, "forks"));
	}

	@Test
	void testGetRepositories_EmptyResult() {
		when(repositoryRepo.findByLanguageAndStarsGreaterThanEqual("C++", 300, Sort.by(Sort.Direction.ASC, "stars")))
				.thenReturn(List.of());

		List<RepositoryEntity> result = repositoryService.getRepositories("C++", 300, "stars");

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(repositoryRepo, times(1)).findByLanguageAndStarsGreaterThanEqual("C++", 300,
				Sort.by(Sort.Direction.ASC, "stars"));
	}

	@Test
	void testSaveRepositories() {
		List<RepositoryEntity> repoList = Arrays.asList(repo1, repo2);

		repositoryService.saveRepositories(repoList);

		verify(repositoryRepo, times(1)).save(repo1);
		verify(repositoryRepo, times(1)).save(repo2);
	}
}
