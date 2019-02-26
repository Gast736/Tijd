package nl.gemeente.groningen.tijdschrijven.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nl.gemeente.groningen.tijdschrijven.connectionmanager.ConnectionManager;
import nl.gemeente.groningen.tijdschrijven.model.Project;

public class ProjectRepository {

    private static final Logger logger = Logger.getLogger(ProjectRepository.class);

    public static List<Project> getAlleProjecten() throws SQLException {
	String sql = "select * " + "from tblproject";
	List<Project> projecten = new ArrayList<>();
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
		ResultSet result = stmt.executeQuery();) {

	    while (result.next()) {

		Project project = new Project();

		project.setIdProject(result.getInt("idproject"));
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

    public static Project getProjectById(int idproject) throws SQLException {
	return getAlleProjecten().stream()
		.filter(p -> idproject == p.getIdProject())
		.findAny()
		.orElse(null);
    }

    public static int insertProject(String naam, String categorie, String opdrachtgever, String directie, Date startdatum, Date einddatum) throws SQLException {
	String sql = "insert into tblProject(naam, categorie, opdrachtgever, directie, startdatum, einddatum) "
		+ "values(?, ?, ?, ?, ?, ?)";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql,
		Statement.RETURN_GENERATED_KEYS)) {
	    stmt.setString(1, naam);
	    stmt.setString(2, categorie);
	    stmt.setString(3, opdrachtgever);
	    stmt.setString(4, directie);
	    stmt.setDate(5, startdatum);
	    stmt.setDate(6, einddatum);

	    stmt.executeUpdate();

	    try (ResultSet key = stmt.getGeneratedKeys()) {

		key.next();
		return key.getInt(1);
	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return -1;
    }

    public static int updateProject(int idproject, String naam, String categorie, String opdrachtgever, String directie,
	    Date startdatum, Date einddatum) {
	String sql = "update tblProject " + "set naam = ?" + ", categorie = ?" + ", opdrachtgever = ?"
		+ ", directie = ?" + ", startdatum = ?" + ", einddatum = ? where idproject = ?";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
	    stmt.setString(1, naam);
	    stmt.setString(2, categorie);
	    stmt.setString(3, opdrachtgever);
	    stmt.setString(4, directie);
	    stmt.setDate(5, startdatum);
	    stmt.setDate(6, einddatum);
	    stmt.setInt(7, idproject);

	    stmt.executeUpdate();
	    
	    return idproject;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return -1;
	
    }

    private ProjectRepository() {
    }

}
