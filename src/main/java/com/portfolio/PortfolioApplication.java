package com.portfolio;

import com.portfolio.entity.Project;
import com.portfolio.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class PortfolioApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortfolioApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(ProjectRepository repository) {
        return args -> {
            Optional<Project> existingPortfolio = repository.findByTitle("Portfolio Website");
            if (existingPortfolio.isPresent()) {
                Project project = existingPortfolio.get();
                project.setTitle("Photography Portfolio Website");
                project.setDescription("A photography portfolio website with a dedicated photo workflow showcase.");
                project.setProjectUrl("/photo-workflow");
                repository.save(project);
            }

            Optional<Project> existingWebApp = repository.findByTitle("Web Application");
            if (existingWebApp.isPresent()) {
                Project project = existingWebApp.get();
                project.setTitle("Church Appointment");
                project.setDescription("A church appointment scheduling app with booking workflows and member coordination.");
                project.setProjectUrl("/church-appointment");
                repository.save(project);
            }

            if (repository.count() == 0) {
                repository.save(new Project(null, "Photography Portfolio Website", "A photography portfolio website with a dedicated photo workflow showcase.", "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=800&q=80", "Spring Boot, Thymeleaf, H2", "/photo-workflow"));
                repository.save(new Project(null, "Church Appointment", "A church appointment scheduling app with booking workflows and member coordination.", "https://images.unsplash.com/photo-1555949963-aa79dcee981c?w=800&q=80", "HTML, CSS, JavaScript", "/church-appointment"));
                repository.save(new Project(null, "Mobile App", "A cross-platform mobile application.", "https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?w=800&q=80", "React Native, Firebase", "https://github.com/Junekrisluhhhh"));
            }
        };
    }
}
