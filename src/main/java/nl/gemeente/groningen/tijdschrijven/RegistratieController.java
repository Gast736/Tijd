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
public class RegistratieController {

    @Autowired
    private DataSource dataSource;

    private Connection getConnection() throws SQLException {
	return dataSource.getConnection();
    }

    @GetMapping("/registraties")
    public ArrayList<Registratie> getAlleRegistraties() {
	ArrayList<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement sql = getConnection().prepareStatement("select * from tblregistratie join tblmedewerker using(idmedewerker) join tblproject using(idproject)")) {

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

		Project project = new Project();
		project.setNaam(result.getString("naam"));
		project.setCategorie(result.getString("categorie"));
		project.setOpdrachtgever(result.getString("opdrachtgever"));
		project.setDirectie(result.getString("directie"));
		project.setStartdatum(result.getDate("startdatum"));
		project.setEinddatum(result.getDate("einddatum"));

		Registratie registratie = new Registratie();
		registratie.setMedewerker(medewerker);
		registratie.setProject(project);
		registratie.setStartdatum(result.getDate("startdatum"));
		registratie.setUren(result.getLong("uren"));

		registraties.add(registratie);

	    }
	    return registraties;
	} catch (SQLException e) {
	    System.out.println(e.getErrorCode() + ": " + e.getMessage());
	    return null;
	}
    }

    @GetMapping("/registraties/{idmedewerker}")
    public ArrayList<Registratie> getRegistratiesByMedewerker(@RequestParam(name = "medewerker") Medewerker medewerker) {
	ArrayList<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement sql = getConnection().prepareStatement("select * from tblregistratie join tblmedewerker using(idmedewerker) join tblproject using(idproject) where idmedewerker = ?")) {

	    sql.setObject(1, medewerker);
	    ResultSet result = sql.executeQuery();

	    while (result.next()) {
		Registratie registratie = new Registratie();

		registratie.setMedewerker((Medewerker)result.getObject("idmedewerker"));
		registratie.setProject((Project)result.getObject("idproject"));
		registratie.setStartdatum(result.getDate("startdatum"));
		registratie.setUren(result.getLong("uren"));

		registraties.add(registratie);

	    }
	    return registraties;
	} catch (SQLException e) {
	    System.out.println(e.getErrorCode() + ": " + e.getMessage());
	    return null;
	}

    }

    @GetMapping("/registraties/{idproject}")
    public ArrayList<Registratie> getRegistratiesByProject(@RequestParam(name = "project") Project project) {
	ArrayList<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement sql = getConnection().prepareStatement("select * from tblregistratie join tblmedewerker using(idmedewerker) join tblproject using(idproject) where idproject = ?")) {

	    sql.setObject(1, project);
	    ResultSet result = sql.executeQuery();

	    while (result.next()) {
		Registratie registratie = new Registratie();

		registratie.setMedewerker((Medewerker)result.getObject("idmedewerker"));
		registratie.setProject((Project)result.getObject("idproject"));
		registratie.setStartdatum(result.getDate("startdatum"));
		registratie.setUren(result.getLong("uren"));

		registraties.add(registratie);

	    }
	    return registraties;
	} catch (SQLException e) {
	    System.out.println(e.getErrorCode() + ": " + e.getMessage());
	    return null;
	}

    }

}