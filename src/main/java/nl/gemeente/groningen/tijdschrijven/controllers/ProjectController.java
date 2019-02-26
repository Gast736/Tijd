package nl.gemeente.groningen.tijdschrijven.controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import nl.gemeente.groningen.tijdschrijven.model.Project;
import nl.gemeente.groningen.tijdschrijven.repositories.ProjectRepository;

@RestController
public class ProjectController {

    @GetMapping("/projecten")
    public List<Project> getAlleProjecten() throws SQLException {
	return ProjectRepository.getAlleProjecten();
    }

    @PostMapping("/nieuwProject")
    public int nieuwProject(
	    @RequestParam(name = "naam") String naam,
	    @RequestParam(name = "categorie") String categorie,
	    @RequestParam(name = "opdrachtgever") String opdrachtgever,
	    @RequestParam(name = "directie") String directie, 
	    @RequestParam(name = "startdatum") Date startdatum,
	    @RequestParam(name = "einddatum") Date einddatum) throws SQLException {

	return ProjectRepository.insertProject(naam, categorie, opdrachtgever, directie, startdatum, einddatum);
    }

    @PutMapping("/changeProject")
    public int updateMedewerker(
	    @RequestParam(name="idproject") int idproject,
	    @RequestParam(name = "naam") String naam,
	    @RequestParam(name = "categorie") String categorie,
	    @RequestParam(name = "opdrachtgever") String opdrachtgever,
	    @RequestParam(name = "directie") String directie, 
	    @RequestParam(name = "startdatum") Date startdatum,
	    @RequestParam(name = "einddatum") Date einddatum) throws SQLException {

	return ProjectRepository.updateProject(idproject, naam, categorie, opdrachtgever, directie, startdatum, einddatum);
    }

}
