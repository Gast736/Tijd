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

public class MedewerkerRepository {

    private static final Logger logger = Logger.getLogger(MedewerkerRepository.class);

    public static List<Medewerker> getAlleMedewerkers() throws SQLException {
	String sql = "select * " + "from tblmedewerker";
	List<Medewerker> medewerkers = new ArrayList<>();
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);
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

    public static Medewerker getMedewerkerByNaam(String naam) throws SQLException {
	String sql = "select * " + "from tblmedewerker " + "where naam = ?";
	Medewerker medewerker = new Medewerker();
	ResultSet result = null;
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setString(1, naam);
	    result = stmt.executeQuery();

	    result.next();

	    medewerker.setIdMedewerker(result.getInt("idmedewerker"));
	    medewerker.setNaam(result.getString("naam"));
	    medewerker.setWachtwoord(result.getString("wachtwoord"));
	    medewerker.setTeam(result.getString("team"));
	    medewerker.setRol(result.getString("rol"));
	    medewerker.setContracturen(result.getDouble("contracturen"));
	    medewerker.setStartdatum(result.getDate("startdatum"));
	    medewerker.setEinddatum(result.getDate("einddatum"));

	    result.close();
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return medewerker;
    }

    public static boolean isWachtwoordCorrect(String naam, String wachtwoord) throws SQLException {
	String sql = "select naam, wachtwoord from tblmedewerker where naam = ?";
	ResultSet result = null;

	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setString(1, naam);
	    result = stmt.executeQuery();

	    result.next();

	    Medewerker medewerker = new Medewerker();
	    medewerker.setWachtwoord(result.getString("wachtwoord"));

	    result.close();

	    String wachtwoordCheck = medewerker.getWachtwoord();
//	    logger.info("We vergelijken het ingevoerde wachtwoord: " + wachtwoord
//		    + ", met het wachtwoord in de database: " + wachtwoordCheck);
	    if (wachtwoordCheck.equals(wachtwoord)) {
//		logger.info("Het wachtwoord komt overeen, returnvalue is true");
		return true;
	    } else {
//		logger.info("Het wachtwoord komt niet overeen, returnvalue is false");
		return false;
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return false;
    }
    
    public static boolean insertMedewerker(Medewerker medewerker) throws SQLException {
	String sql = "insert into tblMedewerker(naam, wachtwoord, team, rol, contracturen, startdatum, einddatum) "
		+ "values(?, ?, ?, ?, ?, ?, ?,)";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
	    stmt.setString(1, medewerker.naam);
	    stmt.setString(2, medewerker.wachtwoord);
	    stmt.setString(3, medewerker.team);
	    stmt.setString(4, medewerker.rol);
	    stmt.setDouble(5, medewerker.contracturen);
	    stmt.setDate(6, (Date) medewerker.startdatum);
	    stmt.setDate(7, (Date) medewerker.einddatum);
	    
	    stmt.executeQuery();
	}
	return false;
    }

    public static boolean updateMedewerker(Medewerker medewerker) throws SQLException {
	String sql = "update tblMedewerker "
		+ "set naam = ?"
		+ ", wachtwoord = ?"
		+ ", team = ?"
		+ ", rol = "
		+ ", contracturen = ?"
		+ ", startdatum = ?"
		+ ", einddatum = ?";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
	    stmt.setString(1, medewerker.naam);
	    stmt.setString(2, medewerker.wachtwoord);
	    stmt.setString(3, medewerker.team);
	    stmt.setString(4, medewerker.rol);
	    stmt.setDouble(5, medewerker.contracturen);
	    stmt.setDate(6, (Date) medewerker.startdatum);
	    stmt.setDate(7, (Date) medewerker.einddatum);
	    
	    stmt.executeQuery();
	}
	return false;
    }

    private MedewerkerRepository() {
    }

}
