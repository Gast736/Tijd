package nl.gemeente.groningen.tijdschrijven.controllers;

import java.sql.SQLException;
import java.util.List;
import java.sql.Date;

import org.springframework.web.bind.annotation.*;

import nl.gemeente.groningen.tijdschrijven.model.*;
import nl.gemeente.groningen.tijdschrijven.repositories.RegistratieRepository;
import nl.gemeente.groningen.tijdschrijven.repositories.RegistratieRepository.Totaaloverzicht;

@RestController
public class RegistratieController {

    @GetMapping("/registraties")
    public List<Registratie> getAlleRegistraties() {
	return RegistratieRepository.getAlleRegistraties();
    }
    @GetMapping("/registraties/dezeweek")
    public List<RegistratieJSON> getAlleRegistratiesDezeMedewerkerDezeWeek(
	    @RequestParam(name = "idmedewerker") int idmedewerker, @RequestParam(name = "datum") Date datum)
	    throws SQLException {
	return RegistratieRepository.getAlleRegistratiesDezeMedewerkerDezeWeek(idmedewerker, datum);
    }
    
    @GetMapping("/registraties/permedewerker")
    public List<Registratie> getAlleRegistratiesByMedewerker(@RequestParam(name = "idmedewerker") int idMedewerker)
	    throws SQLException {
	return RegistratieRepository.getAlleRegistratiesByMedewerker(idMedewerker);
    }

    @GetMapping("/registraties/EersteOnvolledigeWeekPerMedewerker")
    public String getEersteOnvolledigeWeekPerMedewerker(
	    @RequestParam(name="idmedewerker") int idmedewerker){
	return RegistratieRepository.getEersteOnvolledigeWeekPerMedewerker(idmedewerker);
    }

    @GetMapping("/registraties/TotaalUrenPerMedewerkerPerOpdrachtgeverPerMaand")
    public List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerOpdrachtgeverPerMaand(
	    @RequestParam(name="idmedewerker") int idmedewerker){
	return RegistratieRepository.getTotaalUrenPerMedewerkerPerOpdrachtgeverPerMaand(idmedewerker);
    }

    @GetMapping("/registraties/TotaalUrenPerMedewerkerPerProjectPerJaar")
    public List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerJaar(
	    @RequestParam(name="idmedewerker") int idmedewerker, 
	    @RequestParam(name="begindatum") String begindatum, 
	    @RequestParam(name="einddatum") String einddatum)
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenPerMedewerkerPerProjectPerJaar(idmedewerker, begindatum, einddatum);
    }

    @GetMapping("/registraties/TotaalUrenPerMedewerkerPerProjectPerKwartaal")
    public List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerKwartaal(
	    @RequestParam(name="idmedewerker") int idmedewerker, 
	    @RequestParam(name="begindatum") String begindatum, 
	    @RequestParam(name="einddatum") String einddatum)
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenPerMedewerkerPerProjectPerKwartaal(idmedewerker, begindatum, einddatum);
    }

    @GetMapping("/registraties/TotaalUrenPerMedewerkerPerProjectPerMaand")
    public List<Response> getTotaalUrenPerMedewerkerPerProjectPerMaand(
	    @RequestParam(name="idmedewerker") int idmedewerker, 
	    @RequestParam(name="begindatum") String begindatum, 
	    @RequestParam(name="einddatum") String einddatum)
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenPerMedewerkerPerProjectPerMaand(idmedewerker, begindatum, einddatum);
    }

    @GetMapping("/registraties/TotaalUrenPerMedewerkerPerProjectPerWeek")
    public List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerWeek(
	    @RequestParam(name="idmedewerker") int idmedewerker, 
	    @RequestParam(name="begindatum") String begindatum, 
	    @RequestParam(name="einddatum") String einddatum)
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenPerMedewerkerPerProjectPerWeek(idmedewerker, begindatum, einddatum);
    }

    @GetMapping("/registraties/TotaalUrenPerOpdrachtgeverPerMaand")
    public List<Totaaloverzicht> getTotaalUrenPerOpdrachtgeverPerMaand(
	    @RequestParam(name="opdrachtgever") String opdrachtgever) {
	return RegistratieRepository.getTotaalUrenPerOpdrachtgeverPerMaand(opdrachtgever);
    }

    @GetMapping("/registraties/TotaalUrenPerProjectPerMaand")
    public List<Totaaloverzicht> getTotaalUrenPerProjectPerMaand(
	    @RequestParam(name="idproject") int idproject) {
	return RegistratieRepository.getTotaalUrenPerProjectPerMaand(idproject);
    }

    @GetMapping("/registraties/TotaalUrenVerlofPerMaand")
    public List<Totaaloverzicht> getTotaalUrenVerlofPerMaand() {
	return RegistratieRepository.getTotaalUrenVerlofPerMaand();
    }

    @GetMapping("/registraties/TotaalUrenVerlofPerMedewerkerPerMaand")
    public List<Totaaloverzicht> getTotaalUrenVerlofPerMedewerkerPerMaand(
	    @RequestParam(name="idmedewerker") int idmedewerker) {
	return RegistratieRepository.getTotaalUrenVerlofPerMedewerkerPerMaand(idmedewerker);
    }

    @PostMapping("/registratieUpdate")
    public boolean registratieUpdate(@RequestBody List<RegistratieJSON> registraties) throws SQLException {
    	return RegistratieRepository.registratieUpdate(registraties);

    }
}