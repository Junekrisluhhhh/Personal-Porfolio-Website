package com.portfolio.repository;

import com.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Returns all projects ordered by ID ascending (insertion order).
     */
    List<Project> findAllByOrderByIdAsc();

    /**
     * Returns a project matching the given title.
     */
    Optional<Project> findByTitle(String title);
}
 