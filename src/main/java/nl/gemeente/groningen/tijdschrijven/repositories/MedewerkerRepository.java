package nl.gemeente.groningen.tijdschrijven.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
		medewerker.setIdmedewerker(result.getInt("idmedewerker"));
		medewerker.setEmailadres(result.getString("emailadres"));
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

    public static Medewerker getMedewerkerById(int idMedewerker) throws SQLException {
	return getAlleMedewerkers().stream()
		.filter(m -> m.getIdmedewerker() == idMedewerker)
		.findAny()
		.orElse(null);
    }

    public static int insertMedewerker(String emailadres, String naam, String wachtwoord, String team, String rol,
	    double contracturen, String startdatum, String einddatum) {
	String sql = "insert into tblMedewerker(emailadres, naam, wachtwoord, team, rol, contracturen, startdatum, einddatum) values (?, ?, ?, ?, ?, ?, ?, ?)";
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMAN);
	LocalDate start = LocalDate.parse(startdatum, formatter);
	LocalDate eind = LocalDate.parse((einddatum.isEmpty() ? "9999-12-31" : einddatum), formatter);
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql,
		java.sql.Statement.RETURN_GENERATED_KEYS)) {
	    stmt.setString(1, emailadres);
	    stmt.setString(2, naam);
	    stmt.setString(3, wachtwoord);
	    stmt.setString(4, team);
	    stmt.setString(5, rol);
	    stmt.setDouble(6, contracturen);
	    stmt.setDate(7, Date.valueOf(start));
	    stmt.setDate(8, Date.valueOf(eind));

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

    public static Medewerker isWachtwoordCorrect(String emailadres, String wachtwoord) throws SQLException {
	String sql = "select idmedewerker, emailadres, contracturen, rol, wachtwoord from tblmedewerker where emailadres = ?";
	ResultSet result = null;

	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setString(1, emailadres);
	    result = stmt.executeQuery();

	    result.next();

	    Medewerker medewerker = new Medewerker();
	    Medewerker medewerkerReturn = new Medewerker();
	    medewerker.setWachtwoord(result.getString("wachtwoord"));
	    medewerkerReturn.setIdmedewerker(result.getInt("idmedewerker"));
	    medewerkerReturn.setEmailadres(result.getString("emailadres"));
	    medewerkerReturn.setRol(result.getString("rol"));
	    medewerkerReturn.setContracturen(result.getDouble("contracturen"));

	    result.close();

	    String wachtwoordCheck = medewerker.getWachtwoord();
	    if (wachtwoordCheck.equals(wachtwoord)) {
		logger.info("wachtwoord is correct en het object dat teruggegeven wordt = " + medewerkerReturn);
		return medewerkerReturn;
	    } else {
		return null;
	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return null;
    }

    public static int updateMedewerker(int idmedewerker, String emailadres, String naam, String wachtwoord,
	    String team, String rol, double contracturen, Date startdatum, Date einddatum) {
	String sql = "update tblMedewerker set emailadres=?, naam=?, wachtwoord=?, team=?, rol=?, contracturen=?, startdatum=?, einddatum=? where idmedewerker=?";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql)) {
	    stmt.setString(1, emailadres);
	    stmt.setString(2, naam);
	    stmt.setString(3, wachtwoord);
	    stmt.setString(4, team);
	    stmt.setString(5, rol);
	    stmt.setDouble(6, contracturen);
	    stmt.setDate(7, startdatum);
	    stmt.setDate(8, einddatum);
	    stmt.setInt(9, idmedewerker);

	    stmt.executeUpdate();

	    return idmedewerker;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + ": " + e.getMessage());
	}

	return -1;
    }

}
