package com.github_repo_search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.github_repo_search.entity.RepositoryEntity;

@Repository
public interface RepositoryRepo extends JpaRepository<RepositoryEntity, Long> {
	List<RepositoryEntity> findByLanguageAndStarsGreaterThanEqual(String language, int stars, Sort sort);
}
