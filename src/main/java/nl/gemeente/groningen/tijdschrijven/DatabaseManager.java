package nl.gemeente.groningen.tijdschrijven;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DatabaseManager {

    private static Connection conn = null;

    private static final Logger logger = Logger.getLogger(DatabaseManager.class);

    protected static List<Medewerker> getAlleMedewerkers() throws SQLException {
	String sql = "select * "
		+ "from tblmedewerker";
	List<Medewerker> medewerkers = new ArrayList<>();
	try (PreparedStatement stmt = getConnection().prepareStatement(sql);
		ResultSet result = stmt.executeQuery();) {

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
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}

	return medewerkers;

    }


    protected static List<Project> getAlleProjecten() {
	String sql = "select * "
		+ "from tblproject";
	List<Project> projecten = new ArrayList<>();
	try (PreparedStatement stmt = getConnection().prepareStatement(sql);
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

    protected static List<Registratie> getAlleRegistraties() {
	String sql = "select * "
		+ "from tblregistratie "
		+ "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject)";
	List<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement stmt = getConnection().prepareStatement(
		sql);
		ResultSet result = stmt.executeQuery();) {

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
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return registraties;
    }

    protected static List<Registratie> getAlleRegistratiesByMedewerker(int idMedewerker) {
	String sql = "select * "
		+ "from tblregistratie "
		+ "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject) "
		+ "where idmedewerker = ?";
	List<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement stmt = getConnection().prepareStatement(
		sql);) {

	    stmt.setInt(1, idMedewerker);
	    ResultSet result = stmt.executeQuery();

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
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return registraties;
    }

    protected static List<Registratie> getAlleRegistratiesByMedewerkerByMaand(
	    int idMedewerker, Date datum) {
	String sql = "select * "
		+ "from tblregistratie r "
		+ "join tblmedewerker m using(idmedewerker) " 
		+ "join tblproject p using(idproject) "
		+ "where r.idmedewerker = ? " 
		+ "and date_format(r.startdatum, '%Y%m') = date_format(?, '%Y%m')";
	List<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {

	    stmt.setInt(1, idMedewerker);
	    stmt.setDate(2, datum);
	    ResultSet result = stmt.executeQuery();

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
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return registraties;
    }

    protected static List<Registratie> getAlleRegistratiesByMedewerkerByWeek(
	    int idMedewerker, Date datum) {
	String sql = "select * " + "from tblregistratie r "
		+ "join tblmedewerker m using(idmedewerker) " 
		+ "join tblproject p using(idproject) "
		+ "where r.idmedewerker = ? "
		+ "and r.startdatum between date_add(?, interval -(weekday(?)) day) "
		+ "and date_add(?, interval 6-(weekday(?)) day)";
	List<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {

	    stmt.setInt(1, idMedewerker);
	    stmt.setDate(2, datum);
	    stmt.setDate(3, datum);
	    stmt.setDate(4, datum);
	    stmt.setDate(5, datum);
	    ResultSet result = stmt.executeQuery();

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
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return registraties;
    }

    protected static Medewerker getMedewerkerByNaam(String naam) throws SQLException {
	String sql = "select * "
		+ "from tblmedewerkers "
		+ "where naam = ?";
	try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {
	    stmt.setString(1, naam);
	    ResultSet result = stmt.executeQuery();

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

	    return medewerker;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return null;

    }

    protected static Project getProjectByNaam(String naam) {
	String sql = "select * "
		+ "from tblproject "
		+ "where naam = ?";
	try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {

	    stmt.setString(1, naam);
	    ResultSet result = stmt.executeQuery();

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
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return null;

    }

    protected static List<Registratie> getRegistratiesByProject(Project project) {
	String sql = "select * "
		+ "from tblregistratie "
		+ "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject) "
		+ "where idproject = ?";
	List<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {

	    stmt.setObject(1, project);
	    ResultSet result = stmt.executeQuery();

	    while (result.next()) {
		Registratie registratie = new Registratie();

		registratie.setMedewerker((Medewerker) result.getObject("idmedewerker"));
		registratie.setProject((Project) result.getObject("idproject"));
		registratie.setStartdatum(result.getDate("startdatum"));
		registratie.setUren(result.getLong("uren"));

		registraties.add(registratie);

	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return registraties;

    }

    protected static boolean isWachtwoordCorrect(String naam, String wachtwoord) {
	String sql = "select naam"
		+ ", wachtwoord "
		+ "from tblmedewerker "
		+ "where naam = ?";
	try (PreparedStatement stmt = getConnection()
		.prepareStatement(sql);) {

	    stmt.setString(1, naam);
	    ResultSet result = stmt.executeQuery();

	    result.next();

	    Medewerker medewerker = new Medewerker();
	    medewerker.setWachtwoord(result.getString("wachtwoord"));

	    String wachtwoordCheck = medewerker.getWachtwoord();
	    logger.info("We vergelijken het ingevoerde wachtwoord: " + wachtwoord
		    + ", met het wachtwoord in de database: " + wachtwoordCheck);
	    if (wachtwoordCheck.equals(wachtwoord)) {
		logger.info("Het wachtwoord komt overeen, returnvalue is true");
		return true;
	    } else {
		logger.info("Het wachtwoord komt niet overeen, returnvalue is false");
		return false;
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return false;
    }

    private static Connection getConnection() throws SQLException {
	if (conn == null) {
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tijd?createDatabaseIfNotExist=true&serverTimezone=Europe/Amsterdam", "tijdschrijven", "T1jd5chr1jven");
	}
	return conn;
    }
}
