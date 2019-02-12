package nl.gemeente.groningen.tijdschrijven.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.gemeente.groningen.tijdschrijven.model.Project;
import nl.gemeente.groningen.tijdschrijven.repositories.ProjectRepository;

@RestController
public class ProjectController {
    private static final Logger logger = Logger.getLogger(ProjectController.class);

    @GetMapping("/projecten")
    public List<Project> getAlleProjecten() {
	return ProjectRepository.getAlleProjecten();
    }

    @GetMapping("/project")
    public Project getProjectByNaam(@RequestParam(name = "naam") String naam) {
	return ProjectRepository.getProjectByNaam(naam);
    }

}
