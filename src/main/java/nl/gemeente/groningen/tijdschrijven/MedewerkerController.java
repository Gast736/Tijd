package nl.gemeente.groningen.tijdschrijven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedewerkerController {

	@Autowired
	private DataSource dataSource;

	private Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@GetMapping("/medewerker") 
	public Medewerker getMedewerkerByNaam(@RequestParam(name="naam") String naam) throws SQLException {
		try (PreparedStatement sql = getConnection().prepareStatement("select * from tblmedewerkers where naam = ?")) {
			sql.setString(1, naam);

			ResultSet result = sql.executeQuery();

			result.next();

			Medewerker medewerker = new Medewerker();
			medewerker.setIdMedewerker(result.getInt("idmedewerker"));
			medewerker.setNaam(result.getString("naam"));
			medewerker.setWachtwoord(result.getString("wachtwoord"));
			medewerker.setTeam(result.getString("team"));
			medewerker.setRol(result.getString("rol"));
			medewerker.setContracturen(result.getDouble("contracturen"));
			medewerker.setStartdatum(result.getDate("startdatum"));
			medewerker.setEinddatum(result.getDate("einddatum"));
		} catch (SQLException e) {
			System.out.println(e.getErrorCode() + ": " + e.getMessage());
		}
		return null;

	}
	@GetMapping("/medewerkers") 
	public ArrayList<Medewerker> getAllMedewerkers() throws SQLException {
		ArrayList<Medewerker> medewerkers = new ArrayList<>();
		try (PreparedStatement sql = getConnection().prepareStatement("select * from tblmedewerker")) {

			ResultSet result = sql.executeQuery();

			while (result.next()) {

				Medewerker medewerker = new Medewerker();
				medewerker.setIdMedewerker(result.getInt("idmedewerker"));
				medewerker.setNaam(result.getString("naam"));
				medewerker.setWachtwoord(result.getString("wachtwoord"));
				medewerker.setTeam(result.getString("team"));
				medewerker.setRol(result.getString("rol"));
				medewerker.setContracturen(result.getDouble("contracturen"));
				medewerker.setStartdatum(result.getDate("startdatum"));
				medewerker.setEinddatum(result.getDate("einddatum"));

				medewerkers.add(medewerker);

			}
			return medewerkers;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode() + ": " + e.getMessage());
		}
		return null;

	}

	@GetMapping("/medewerker/wachtwoord")
	public boolean isWachtwoordCorrect(@RequestParam(name="naam") String naam, @RequestParam(name="wachtwoord") String wachtwoord) {
		try (PreparedStatement sql = getConnection().prepareStatement("select naam, wachtwoord from tblmedewerker where naam = ?")) {
			sql.setString(1, naam);

			ResultSet result = sql.executeQuery();

			result.next();

			Medewerker medewerker = new Medewerker();
			medewerker.setWachtwoord(result.getString("wachtwoord"));

			String wachtwoordCheck = medewerker.getWachtwoord();
			System.out.println("We vergelijken het ingevoerde wachtwoord: "+wachtwoord+", met het wachtwoord in de database: "+wachtwoordCheck);
			if (wachtwoordCheck.equals(wachtwoord)) {
				System.out.println("Het wachtwoord komt overeen, returnvalue is true");
				return true;
			} else {
				System.out.println("Het wachtwoord komt niet overeen, returnvalue is false");
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;

		}

	}
}
