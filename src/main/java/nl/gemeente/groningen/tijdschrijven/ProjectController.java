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
public class ProjectController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/projecten")
    public ArrayList<Project> getAlleProjecten() {
	ArrayList<Project> projecten = new ArrayList<>();
	try (PreparedStatement sql = getConnection().prepareStatement("select * from tblproject")) {

	    ResultSet result = sql.executeQuery();

	    while (result.next()) {
		Project project = new Project();

		project.setNaam(result.getString("naam"));
		project.setCategorie(result.getString("categorie"));
		project.setOpdrachtgever(result.getString("opdrachtgever"));
		project.setDirectie(result.getString("directie"));
		project.setStartdatum(result.getDate("startdatum"));
		project.setEinddatum(result.getDate("einddatum"));

		projecten.add(project);

	    }
	    return projecten;
	} catch (SQLException e) {
	    System.out.println(e.getErrorCode() + ": " + e.getMessage());
	    return null;
	}
    }

    private Connection getConnection() throws SQLException {
	return dataSource.getConnection();
    }

    @GetMapping("/project")
    public Project getProjectByNaam(@RequestParam(name = "naam") String naam) {
	try (PreparedStatement sql = getConnection().prepareStatement("select * from tblproject where naam = ?")) {

	    sql.setString(1, naam);

	    ResultSet result = sql.executeQuery();

	    result.next();
	    Project project = new Project();

	    project.setNaam(result.getString("naam"));
	    project.setCategorie(result.getString("categorie"));
	    project.setOpdrachtgever(result.getString("opdrachtgever"));
	    project.setDirectie(result.getString("directie"));
	    project.setStartdatum(result.getDate("startdatum"));
	    project.setEinddatum(result.getDate("einddatum"));

	    return project;
	} catch (SQLException e) {
	    System.out.println(e.getErrorCode() + ": " + e.getMessage());
	    return null;
	}

    }
}
