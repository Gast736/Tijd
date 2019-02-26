package nl.gemeente.groningen.tijdschrijven.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import nl.gemeente.groningen.tijdschrijven.model.Registratie;
import nl.gemeente.groningen.tijdschrijven.model.RegistratieJSON;
import nl.gemeente.groningen.tijdschrijven.repositories.RegistratieRepository;
import nl.gemeente.groningen.tijdschrijven.repositories.RegistratieRepository.Totaaloverzicht;

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

    @GetMapping("/registraties/EersteOnvolledigeWeekPerMedewerker")
    public String getEersteOnvolledigeWeekPerMedewerker(
	    @RequestParam(name="idmedewerker") int idmedewerker)
	    throws SQLException {
	return RegistratieRepository.getEersteOnvolledigeWeekPerMedewerker(idmedewerker);
    }

    @GetMapping("/registraties/TotaalUrenPerMedewerkerPerOpdrachtgeverPerMaand")
    public List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerOpdrachtgeverPerMaand(
	    @RequestParam(name="idmedewerker") int idmedewerker)
	    throws SQLException {
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
    public List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerMaand(
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
	    @RequestParam(name="opdrachtgever") String opdrachtgever)
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenPerOpdrachtgeverPerMaand(opdrachtgever);
    }

    @GetMapping("/registraties/TotaalUrenPerProjectPerMaand")
    public List<Totaaloverzicht> getTotaalUrenPerProjectPerMaand(
	    @RequestParam(name="idproject") int idproject)
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenPerProjectPerMaand(idproject);
    }

    @GetMapping("/registraties/TotaalUrenVerlofPerMaand")
    public List<Totaaloverzicht> getTotaalUrenVerlofPerMaand()
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenVerlofPerMaand();
    }

    @GetMapping("/registraties/TotaalUrenVerlofPerMedewerkerPerMaand")
    public List<Totaaloverzicht> getTotaalUrenVerlofPerMedewerkerPerMaand(
	    @RequestParam(name="idmedewerker") int idmedewerker)
	    throws SQLException {
	return RegistratieRepository.getTotaalUrenVerlofPerMedewerkerPerMaand(idmedewerker);
    }

    @PostMapping("/registratieUpdate")
    public boolean registratieUpdate(@RequestBody List<RegistratieJSON> registraties) throws SQLException {
    	return RegistratieRepository.registratieUpdate(registraties);

    }
}