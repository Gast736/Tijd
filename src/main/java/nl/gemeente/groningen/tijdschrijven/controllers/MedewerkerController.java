package nl.gemeente.groningen.tijdschrijven.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.gemeente.groningen.tijdschrijven.model.Medewerker;
import nl.gemeente.groningen.tijdschrijven.repositories.MedewerkerRepository;

@RestController
public class MedewerkerController {

    @GetMapping("/medewerkers")
    public List<Medewerker> getAllMedewerkers() throws SQLException {
	return MedewerkerRepository.getAlleMedewerkers();

    }

    @GetMapping("/medewerker")
    public Medewerker getMedewerkerByNaam(@RequestParam(name = "naam") String naam) throws SQLException {
	return MedewerkerRepository.getMedewerkerByNaam(naam);

    }

    @GetMapping("/medewerker/wachtwoord")
    public int isWachtwoordCorrect(@RequestParam(name = "naam") String naam,
	    @RequestParam(name = "wachtwoord") String wachtwoord) throws SQLException {
	return MedewerkerRepository.isWachtwoordCorrect(naam, wachtwoord);
    }
}
