package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_path", length = 500)
    private String imagePath;

    /**
     * Comma-separated list of technologies, e.g. "Spring Boot, React, PostgreSQL"
     */
    @Column(nullable = false, length = 500)
    private String technologies;

    @Column(name = "project_url", length = 500)
    private String projectUrl;

    /**
     * Convenience method — splits the comma-separated technologies string
     * into a trimmed list for use in Thymeleaf templates.
     */
    @Transient
    public List<String> getTechList() {
        if (technologies == null || technologies.isBlank()) {
            return List.of();
        }
        return Arrays.stream(technologies.split(","))
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .toList();
    }

    
}
