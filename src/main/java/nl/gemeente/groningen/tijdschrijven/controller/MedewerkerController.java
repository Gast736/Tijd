package nl.gemeente.groningen.tijdschrijven.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.gemeente.groningen.tijdschrijven.model.Medewerker;

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
	ArrayList<Medewerker> medewerkers = new ArrayList<Medewerker>();
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
}
