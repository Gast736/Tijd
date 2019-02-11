package nl.gemeente.groningen.tijdschrijven;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedewerkerController {

    @GetMapping("/medewerkers")
    public List<Medewerker> getAllMedewerkers() throws SQLException {
	return DatabaseManager.getAlleMedewerkers();

    }

    @GetMapping("/medewerker")
    public Medewerker getMedewerkerByNaam(@RequestParam(name = "naam") String naam) throws SQLException {
	return DatabaseManager.getMedewerkerByNaam(naam);

    }

    @GetMapping("/medewerker/wachtwoord")
    public boolean isWachtwoordCorrect(@RequestParam(name = "naam") String naam,
	    @RequestParam(name = "wachtwoord") String wachtwoord) {
	return  DatabaseManager.isWachtwoordCorrect(naam, wachtwoord);
    }
}
