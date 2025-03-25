package com.github_repo_search.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "repositories")
public class RepositoryEntity {

	@Id
	private Long id;

	private String name;
	@Column(length = 1000)
	private String description;
	private String owner;
	private String language;
	private int stars;
	private int forks;

	@Column(name = "last_updated")
	private Instant lastUpdated;
}
