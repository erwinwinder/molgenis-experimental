package org.molgenis.database;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.molgenis.database.domain.Project;
import org.molgenis.database.repository.ProjectRepository;

import com.google.inject.persist.Transactional;

@Transactional
public class ProjectService {
	private ProjectRepository projectRepository;

	@Inject
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	public Optional<Project> findById(String id) {
		return projectRepository.findById(id);
	}

	public Optional<Project> findByName(String name) {
		return projectRepository.findByName(name);
	}

	public Project add(Project project) {
		return projectRepository.insert(project);
	}
}
