package nl.gemeente.groningen.tijdschrijven.controllers;

import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.gemeente.groningen.tijdschrijven.model.Project;
import nl.gemeente.groningen.tijdschrijven.model.Registratie;
import nl.gemeente.groningen.tijdschrijven.repositories.RegistratieRepository;

@RestController
public class RegistratieController {

    private static final Logger logger = Logger.getLogger(RegistratieController.class);

    @GetMapping("/registraties")
    public List<Registratie> getAlleRegistraties() {
	return RegistratieRepository.getAlleRegistraties();
    }

    @GetMapping("/registraties/permedewerker")
    public List<Registratie> getAlleRegistratiesByMedewerker(@RequestParam(name = "idmedewerker") int idMedewerker) {
	return RegistratieRepository.getAlleRegistratiesByMedewerker(idMedewerker);
    }

    @GetMapping("/registraties/permaand")
    public List<Registratie> getAlleRegistratiesByMedewerkerByMaand(
	    @RequestParam(name = "idmedewerker") int idMedewerker, @RequestParam(name = "datum") Date datum) {
	return RegistratieRepository.getAlleRegistratiesByMedewerkerByMaand(idMedewerker, datum);
    }

    @GetMapping("/registraties/perweek")
    public List<Registratie> getAlleRegistratiesByMedewerkerByWeek(
	    @RequestParam(name = "idmedewerker") int idMedewerker, @RequestParam(name = "datum") Date datum) {
	return RegistratieRepository.getAlleRegistratiesByMedewerkerByWeek(idMedewerker, datum);
    }

    @GetMapping("/registraties/perproject")
    public List<Registratie> getRegistratiesByProject(@RequestParam(name = "project") Project project) {
	return RegistratieRepository.getRegistratiesByProject(project);

    }

}