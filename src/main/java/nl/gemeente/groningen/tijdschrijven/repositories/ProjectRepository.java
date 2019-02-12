package nl.gemeente.groningen.tijdschrijven.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nl.gemeente.groningen.tijdschrijven.connectionmanager.ConnectionManager;
import nl.gemeente.groningen.tijdschrijven.model.Project;

public class ProjectRepository {

    private static final Logger logger = Logger.getLogger(ProjectRepository.class);

    public static List<Project> getAlleProjecten() {
	String sql = "select * " + "from tblproject";
	List<Project> projecten = new ArrayList<>();
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
		ResultSet result = stmt.executeQuery();) {

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
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return projecten;
    }

    public static Project getProjectByNaam(String naam) {
	String sql = "select * " + "from tblproject " + "where naam = ?";
	Project project = new Project();
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setString(1, naam);
	    ResultSet result = stmt.executeQuery();

	    result.next();

	    project.setNaam(result.getString("naam"));
	    project.setCategorie(result.getString("categorie"));
	    project.setOpdrachtgever(result.getString("opdrachtgever"));
	    project.setDirectie(result.getString("directie"));
	    project.setStartdatum(result.getDate("startdatum"));
	    project.setEinddatum(result.getDate("einddatum"));

	    result.close();
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return project;
    }

    private ProjectRepository() {
    }

}
