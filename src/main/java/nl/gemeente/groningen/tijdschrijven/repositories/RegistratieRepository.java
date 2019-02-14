package nl.gemeente.groningen.tijdschrijven.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import nl.gemeente.groningen.tijdschrijven.connectionmanager.ConnectionManager;
import nl.gemeente.groningen.tijdschrijven.model.Medewerker;
import nl.gemeente.groningen.tijdschrijven.model.Project;
import nl.gemeente.groningen.tijdschrijven.model.ProjectDTO;
import nl.gemeente.groningen.tijdschrijven.model.Registratie;

public class RegistratieRepository {

    private static final Logger logger = Logger.getLogger(RegistratieRepository.class);
    private static final String CATEGORIE = "categorie";
    private static final String CONTRACTUREN = "contracturen";
    private static final String DIRECTIE = "directie";
    private static final String EINDDATUM = "einddatum";
    private static final String IDMEDEWERKER = "idmedewerker";
    private static final String IDPROJECT = "idproject";
    private static final String NAAM = "naam";
    private static final String OPDRACHTGEVER = "opdrachtgever";
    private static final String ROL = "rol";
    private static final String STARTDATUM = "startdatum";
    private static final String TEAM = "team";
    private static final String UREN = "uren";
    private static final String WACHTWOORD = "wachtwoord";

    public static List<Registratie> getAlleRegistraties() {
	String sql = "select * " + "from tblregistratie " + "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject)";
	List<Registratie> registraties = new ArrayList<>();
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
		ResultSet result = stmt.executeQuery();) {

	    while (result.next()) {

		Medewerker medewerker = new Medewerker();
		medewerker.setIdMedewerker(result.getInt(IDMEDEWERKER));
		medewerker.setNaam(result.getString(NAAM));
		medewerker.setWachtwoord(result.getString(WACHTWOORD));
		medewerker.setTeam(result.getString(TEAM));
		medewerker.setRol(result.getString(ROL));
		medewerker.setContracturen(result.getDouble(CONTRACTUREN));
		medewerker.setStartdatum(result.getDate(STARTDATUM));
		medewerker.setEinddatum(result.getDate(EINDDATUM));

		Project project = new Project();
		project.setNaam(result.getString(NAAM));
		project.setCategorie(result.getString(CATEGORIE));
		project.setOpdrachtgever(result.getString(OPDRACHTGEVER));
		project.setDirectie(result.getString(DIRECTIE));
		project.setStartdatum(result.getDate(STARTDATUM));
		project.setEinddatum(result.getDate(EINDDATUM));

		Registratie registratie = new Registratie();
		registratie.setMedewerker(medewerker);
		registratie.setProject(project);
		registratie.setStartdatum(result.getDate(STARTDATUM));
		registratie.setUren(result.getLong(UREN));

		registraties.add(registratie);

	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return registraties;
    }

    public static List<Registratie> getAlleRegistratiesByMedewerker(int idMedewerker) throws SQLException {
	String sql = "select * " + "from tblregistratie " + "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject) " + "where idmedewerker = ?";
	List<Registratie> registraties = new ArrayList<>();
	ResultSet result = null;
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setInt(1, idMedewerker);
	    result = stmt.executeQuery();

	    while (result.next()) {

		Medewerker medewerker = new Medewerker();
		medewerker.setIdMedewerker(result.getInt(IDMEDEWERKER));
		medewerker.setNaam(result.getString(NAAM));
		medewerker.setWachtwoord(result.getString(WACHTWOORD));
		medewerker.setTeam(result.getString(TEAM));
		medewerker.setRol(result.getString(ROL));
		medewerker.setContracturen(result.getDouble(CONTRACTUREN));
		medewerker.setStartdatum(result.getDate(STARTDATUM));
		medewerker.setEinddatum(result.getDate(EINDDATUM));

		Project project = new Project();
		project.setNaam(result.getString(NAAM));
		project.setCategorie(result.getString(CATEGORIE));
		project.setOpdrachtgever(result.getString(OPDRACHTGEVER));
		project.setDirectie(result.getString(DIRECTIE));
		project.setStartdatum(result.getDate(STARTDATUM));
		project.setEinddatum(result.getDate(EINDDATUM));

		Registratie registratie = new Registratie();
		registratie.setMedewerker(medewerker);
		registratie.setProject(project);
		registratie.setStartdatum(result.getDate(STARTDATUM));
		registratie.setUren(result.getLong(UREN));

		registraties.add(registratie);

	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return registraties;
    }

    public static List<Registratie> getAlleRegistratiesByMedewerkerByMaand(int idMedewerker, Date datum)
	    throws SQLException {
	String sql = "select * " + "from tblregistratie r " + "join tblmedewerker m using(idmedewerker) "
		+ "join tblproject p using(idproject) " + "where r.idmedewerker = ? "
		+ "and date_format(r.startdatum, '%Y%m') = date_format(?, '%Y%m')";
	List<Registratie> registraties = new ArrayList<>();
	ResultSet result = null;

	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setInt(1, idMedewerker);
	    stmt.setDate(2, datum);
	    result = stmt.executeQuery();

	    while (result.next()) {

		Medewerker medewerker = new Medewerker();
		medewerker.setIdMedewerker(result.getInt(IDMEDEWERKER));
		medewerker.setNaam(result.getString(NAAM));
		medewerker.setWachtwoord(result.getString(WACHTWOORD));
		medewerker.setTeam(result.getString(TEAM));
		medewerker.setRol(result.getString(ROL));
		medewerker.setContracturen(result.getDouble(CONTRACTUREN));
		medewerker.setStartdatum(result.getDate(STARTDATUM));
		medewerker.setEinddatum(result.getDate(EINDDATUM));

		Project project = new Project();
		project.setNaam(result.getString(NAAM));
		project.setCategorie(result.getString(CATEGORIE));
		project.setOpdrachtgever(result.getString(OPDRACHTGEVER));
		project.setDirectie(result.getString(DIRECTIE));
		project.setStartdatum(result.getDate(STARTDATUM));
		project.setEinddatum(result.getDate(EINDDATUM));
		result.close();

		Registratie registratie = new Registratie();
		registratie.setMedewerker(medewerker);
		registratie.setProject(project);
		registratie.setStartdatum(result.getDate(STARTDATUM));
		registratie.setUren(result.getLong(UREN));

		registraties.add(registratie);

	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return registraties;
    }

    public static List<Registratie> getAlleRegistratiesByMedewerkerByWeek(int idMedewerker, Date datum)
	    throws SQLException {
	String sql = "select * " + "from tblregistratie r " + "join tblmedewerker m using(idmedewerker) "
		+ "join tblproject p using(idproject) " + "where r.idmedewerker = ? "
		+ "and r.startdatum between date_add(?, interval -(weekday(?)) day) "
		+ "and date_add(?, interval 6-(weekday(?)) day)";
	List<Registratie> registraties = new ArrayList<>();
	ResultSet result = null;
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setInt(1, idMedewerker);
	    stmt.setDate(2, datum);
	    stmt.setDate(3, datum);
	    stmt.setDate(4, datum);
	    stmt.setDate(5, datum);
	    result = stmt.executeQuery();

	    while (result.next()) {

		Medewerker medewerker = new Medewerker();
		medewerker.setIdMedewerker(result.getInt(IDMEDEWERKER));
		medewerker.setNaam(result.getString(NAAM));
		medewerker.setWachtwoord(result.getString(WACHTWOORD));
		medewerker.setTeam(result.getString(TEAM));
		medewerker.setRol(result.getString(ROL));
		medewerker.setContracturen(result.getDouble(CONTRACTUREN));
		medewerker.setStartdatum(result.getDate(STARTDATUM));
		medewerker.setEinddatum(result.getDate(EINDDATUM));

		Project project = new Project();
		project.setNaam(result.getString(NAAM));
		project.setCategorie(result.getString(CATEGORIE));
		project.setOpdrachtgever(result.getString(OPDRACHTGEVER));
		project.setDirectie(result.getString(DIRECTIE));
		project.setStartdatum(result.getDate(STARTDATUM));
		project.setEinddatum(result.getDate(EINDDATUM));

		Registratie registratie = new Registratie();
		registratie.setMedewerker(medewerker);
		registratie.setProject(project);
		registratie.setStartdatum(result.getDate(STARTDATUM));
		registratie.setUren(result.getLong(UREN));

		registraties.add(registratie);

	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return registraties;
    }

    public static List<Registratie> getRegistratiesByProject(ProjectDTO project) throws SQLException {
	String sql = "select * " + "from tblregistratie " + "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject) " + "where idproject = ?";
	List<Registratie> registraties = new ArrayList<>();
	ResultSet result = null;
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setObject(1, project);
	    result = stmt.executeQuery();

	    while (result.next()) {
		Registratie registratie = new Registratie();

		registratie.setMedewerker((Medewerker) result.getObject(IDMEDEWERKER));
		registratie.setProject((Project) result.getObject(IDPROJECT));
		registratie.setStartdatum(result.getDate(STARTDATUM));
		registratie.setUren(result.getLong(UREN));

		registraties.add(registratie);

	    }

	    result.close();
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return registraties;
    }

    private RegistratieRepository() {
    }

}
