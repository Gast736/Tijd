package nl.gemeente.groningen.tijdschrijven.controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.gemeente.groningen.tijdschrijven.model.ProjectDTO;
import nl.gemeente.groningen.tijdschrijven.model.Registratie;
import nl.gemeente.groningen.tijdschrijven.model.RegistratieJSON;
import nl.gemeente.groningen.tijdschrijven.repositories.RegistratieRepository;

@RestController
public class RegistratieController {

    @GetMapping("/registraties")
    public List<Registratie> getAlleRegistraties() {
	return RegistratieRepository.getAlleRegistraties();
    }

    @GetMapping("/registraties/permedewerker")
    public List<Registratie> getAlleRegistratiesByMedewerker(@RequestParam(name = "idmedewerker") int idMedewerker)
	    throws SQLException {
	return RegistratieRepository.getAlleRegistratiesByMedewerker(idMedewerker);
    }

    @GetMapping("/registraties/permaand")
    public List<Registratie> getAlleRegistratiesByMedewerkerByMaand(
	    @RequestParam(name = "idmedewerker") int idMedewerker, @RequestParam(name = "datum") Date datum)
	    throws SQLException {
	return RegistratieRepository.getAlleRegistratiesByMedewerkerByMaand(idMedewerker, datum);
    }

    @GetMapping("/registraties/perweek")
    public List<Registratie> getAlleRegistratiesByMedewerkerByWeek(
	    @RequestParam(name = "idmedewerker") int idMedewerker, @RequestParam(name = "datum") Date datum)
	    throws SQLException {
	return RegistratieRepository.getAlleRegistratiesByMedewerkerByWeek(idMedewerker, datum);
    }

    @GetMapping("/registraties/perproject")
    public List<Registratie> getRegistratiesByProject(@RequestParam(name = "project") ProjectDTO project)
	    throws SQLException {
	return RegistratieRepository.getRegistratiesByProject(project);
    }
    
    @PostMapping("/registratieUpdate")
    public boolean registratieUpdate(@RequestBody ArrayList<RegistratieJSON> registraties) throws SQLException {
    	return RegistratieRepository.registratieUpdate(registraties);

    }
}