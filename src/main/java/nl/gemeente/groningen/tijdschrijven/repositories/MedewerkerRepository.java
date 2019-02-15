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

    public static boolean insertMedewerker(Medewerker medewerker) throws SQLException {
	String sql = "insert into tblMedewerker(naam, wachtwoord, team, rol, contracturen, startdatum, einddatum) "
		+ "values(?, ?, ?, ?, ?, ?, ?,)";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
	    stmt.setString(1, medewerker.getNaam());
	    stmt.setString(2, medewerker.getWachtwoord());
	    stmt.setString(3, medewerker.getTeam());
	    stmt.setString(4, medewerker.getRol());
	    stmt.setDouble(5, medewerker.getContracturen());
	    stmt.setDate(6, (Date) medewerker.getStartdatum());
	    stmt.setDate(7, (Date) medewerker.getEinddatum());

	    stmt.executeQuery();
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return false;
    }

    public static int isWachtwoordCorrect(String naam, String wachtwoord) throws SQLException {
	String sql = "select idmedewerker, naam, wachtwoord from tblmedewerker where naam = ?";
	ResultSet result = null;

	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setString(1, naam);
	    result = stmt.executeQuery();

	    result.next();

	    Medewerker medewerker = new Medewerker();
	    medewerker.setWachtwoord(result.getString("wachtwoord"));
	    int id = result.getInt("idmedewerker");

	    result.close();

	    String wachtwoordCheck = medewerker.getWachtwoord();
	    if (wachtwoordCheck.equals(wachtwoord)) {
	    	System.out.println("wachtwoord is correct en het id dat teruggegeven wordt = "+id);
	    	return id;
	    } else {
		return 0;
	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return 0;
    }

    public static boolean updateMedewerker(Medewerker medewerker) throws SQLException {
	String sql = "update tblMedewerker " + "set naam = ?" + ", wachtwoord = ?" + ", team = ?" + ", rol = "
		+ ", contracturen = ?" + ", startdatum = ?" + ", einddatum = ?";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
	    stmt.setString(1, medewerker.getNaam());
	    stmt.setString(2, medewerker.getWachtwoord());
	    stmt.setString(3, medewerker.getTeam());
	    stmt.setString(4, medewerker.getRol());
	    stmt.setDouble(5, medewerker.getContracturen());
	    stmt.setDate(6, (Date) medewerker.getStartdatum());
	    stmt.setDate(7, (Date) medewerker.getEinddatum());

	    stmt.executeQuery();
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}
	return false;
    }

    private MedewerkerRepository() {
    }
}
