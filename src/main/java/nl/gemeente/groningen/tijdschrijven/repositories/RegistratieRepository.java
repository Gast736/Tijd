package nl.gemeente.groningen.tijdschrijven.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import nl.gemeente.groningen.tijdschrijven.connectionmanager.ConnectionManager;
import nl.gemeente.groningen.tijdschrijven.model.Medewerker;
import nl.gemeente.groningen.tijdschrijven.model.Project;
import nl.gemeente.groningen.tijdschrijven.model.Registratie;
import nl.gemeente.groningen.tijdschrijven.model.RegistratieJSON;

public class RegistratieRepository {

    public static final class Totaaloverzicht {

	private int aantal;
	private Medewerker medewerker;
	private String opdrachtgever;
	private String periode;
	private Project project;


	public int getAantal() {
	    return aantal;
	}
	public Medewerker getMedewerker() {
	    return medewerker;
	}
	public String getOpdrachtgever() {
	    return opdrachtgever;
	}
	public String getPeriode() {
	    return periode;
	}
	public Project getProject() {
	    return project;
	}
	public void setAantal(int aantal) {
	    this.aantal = aantal;
	}
	public void setMedewerker(Medewerker medewerker) {
	    this.medewerker = medewerker;
	}
	public void setOpdrachtgever(String opdrachtgever) {
	    this.opdrachtgever = opdrachtgever;
	}
	public void setPeriode(String periode) {
	    this.periode = periode;
	}
	public void setProject(Project project) {
	    this.project = project;
	}

    }
    private static final Logger logger = Logger.getLogger(RegistratieRepository.class);

    public static List<Registratie> getAlleRegistraties() {
	String sql = "select * "
		+ "from tblregistratie "
		+ "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject)";
	List<Registratie> registraties = new ArrayList<>();
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
		medewerker.setIdmedewerker(result.getInt("idmedewerker"));
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
		medewerker.setIdmedewerker(result.getInt("idmedewerker"));
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
		result.close();

		Registratie registratie = new Registratie();
		registratie.setMedewerker(medewerker);
		registratie.setProject(project);
		registratie.setStartdatum(result.getDate("startdatum"));
		registratie.setUren(result.getLong("uren"));

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
		medewerker.setIdmedewerker(result.getInt("idmedewerker"));
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
	} finally {
	    if (result != null) {
		result.close();
	    }
	}
	return registraties;
    }


    public static List<RegistratieJSON> getAlleRegistratiesDezeMedewerkerDezeWeek(int idmedewerker, Date eerstedatum)
	    throws SQLException {
	String sql = "select * from tblregistratie r join tblmedewerker m using(idmedewerker) "
		+ "join tblproject p using(idproject) where r.idmedewerker = ? "
		+ "and r.startdatum between ? and date_add(?, interval 4 day)";
	List<RegistratieJSON> registraties = new ArrayList<>();
	ResultSet result = null;
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setInt(1, idmedewerker);
	    stmt.setDate(2, eerstedatum);
	    stmt.setDate(3, eerstedatum);
	    result = stmt.executeQuery();

	    while (result.next()) {

		RegistratieJSON j = new RegistratieJSON(
			result.getInt("idmedewerker"), 
			result.getInt("idproject"),
			result.getDate("startdatum"),
			result.getDouble("uren"));

		registraties.add(j);

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

    public static String getEersteOnvolledigeWeekPerMedewerker(int idmedewerker) {
	String sql = "SELECT min(jaarweek) as jaarweek " + 
		"FROM ( " + 
		"SELECT	m.idmedewerker " + 
		",	date_format(r.startdatum, '%x-%v') as jaarweek " + 
		",	sum(r.uren) as uren " + 
		"FROM	tblregistratie r " + 
		"join	tblmedewerker m using (idmedewerker) " + 
		"group by m.idmedewerker " + 
		",	m.contracturen " + 
		",	date_format(r.startdatum, '%x-%v') " + 
		"having sum(r.uren) < m.contracturen " + 
		") a "
		+ "where idmedewerker = ? " + 
		"group by idmedewerker";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idmedewerker);

	    try (ResultSet result = stmt.executeQuery()) {
		if (result.next()) {
		return result.getString("jaarweek");
		}
	    }
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}

	return "";

    }

    public static List<Registratie> getRegistratiesByProject(Project project) throws SQLException {
	String sql = "select * " + "from tblregistratie " + "join tblmedewerker using(idmedewerker) "
		+ "join tblproject using(idproject) " + "where idproject = ?";
	List<Registratie> registraties = new ArrayList<>();
	ResultSet result = null;
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    stmt.setObject(1, project);
	    result = stmt.executeQuery();

	    while (result.next()) {
		Registratie registratie = new Registratie();

		registratie.setMedewerker((Medewerker) result.getObject("idmedewerker"));
		registratie.setProject((Project) result.getObject("idproject"));
		registratie.setStartdatum(result.getDate("startdatum"));
		registratie.setUren(result.getLong("uren"));

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

    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerOpdrachtgeverPerMaand(int idmedewerker) {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "select m.naam as medewerker"
		+ ", p.opdrachtgever"
		+ ", date_format(r.startdatum, '%Y-%m') as jaarmaand"
		+ ", sum(r.uren) as uren "
		+ "from tblregistratie r "
		+ "join tblproject p using (idproject) "
		+ "join tblmedewerker m using (idmedewerker) "
		+ "where r.idmedewerker = ? "
		+ "group by m.naam"
		+ ", p.opdrachtgever"
		+ ", date_format(r.startdatum, '%Y-%m')";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idmedewerker);

	    try (ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		    t.setOpdrachtgever(result.getString("opdrachtgever"));
		    t.setPeriode(result.getString("jaarmaand"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();

    }

    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerJaar(int idmedewerker, String begindatum, String einddatum) throws SQLException {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "SELECT " + 
		"	m.naam medewerker" + 
		",	p.naam project" + 
		",	date_format(r.startdatum, \"%Y\") jaar" + 
		",	sum(uren) uren" + 
		"FROM tijd.tblregistratie r" + 
		"	join tblmedewerker m using (idmedewerker)" + 
		"    join tblproject p using (idproject)" + 
		"where" + 
		"	r.idmedewerker = ?" + 
		"and" + 
		"	startdatum between ? and ?" + 
		"group by " + 
		"	m.naam " + 
		",	p.naam " + 
		",	date_format(r.startdatum, \"%Y\")";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    try (ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		    t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		    t.setPeriode(result.getString("jaar"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();
    }
    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerKwartaal(int idmedewerker, String begindatum, String einddatum) throws SQLException {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "SELECT " + 
		"	m.naam medewerker" + 
		",	p.naam project" + 
		",	concat(date_format(r.startdatum, \"%Y\"), '-Q', quarter(r.startdatum)) jaarkwart" + 
		",	sum(uren) uren" + 
		"FROM tijd.tblregistratie r" + 
		"	join tblmedewerker m using (idmedewerker)" + 
		"    join tblproject p using (idproject)" + 
		"where" + 
		"	r.idmedewerker = ?" + 
		"and" + 
		"	startdatum between ? and ?" + 
		"group by " + 
		"	m.naam " + 
		",	p.naam " + 
		",	concat(date_format(r.startdatum, \"%Y\"), quarter(r.startdatum))";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    try (ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		    t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		    t.setPeriode(result.getString("jaarkwart"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();
    }

    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerMaand(int idmedewerker, String begindatum, String einddatum) throws SQLException {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "SELECT " + 
		"	m.naam medewerker " + 
		",	p.naam project " + 
		",	date_format(r.startdatum, \"%Y%m\") as jaarmaand " + 
		",	sum(uren) as uren " + 
		"FROM tijd.tblregistratie r " + 
		"	join tblmedewerker m using (idmedewerker) " + 
		"    join tblproject p using (idproject) " + 
		"where " + 
		"	r.idmedewerker = ? " + 
		"and" + 
		"	r.startdatum between ? and ? " + 
		"group by " + 
		"	m.naam " + 
		",	p.naam " + 
		",	date_format(r.startdatum, \"%Y%m\")";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    try (ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		    t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		    t.setPeriode(result.getString("jaarmaand"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();
    }
    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerWeek(int idmedewerker, String begindatum, String einddatum) throws SQLException {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "SELECT " + 
		"	m.naam medewerker" + 
		",	p.naam project" + 
		",	date_format(r.startdatum, \"%Y%u\") jaarweek" + 
		",	sum(uren) uren" + 
		"FROM tijd.tblregistratie r" + 
		"	join tblmedewerker m using (idmedewerker)" + 
		"	join tblproject p using (idproject)" + 
		"where" + 
		"	r.idmedewerker = ?" + 
		"and" + 
		"	r.startdatum between ? and ?" + 
		"group by " + 
		"	m.naam " + 
		",	p.naam " + 
		",	date_format(r.startdatum, \"%Y%u\")";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    try (ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		    t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		    t.setPeriode(result.getString("jaarweek"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}

	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();
    }

    public static List<Totaaloverzicht> getTotaalUrenPerOpdrachtgeverPerMaand(String opdrachtgever) {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "select p.opdrachtgever"
		+ ", date_format(r.startdatum, '%Y-%m') as jaarmaand"
		+ ", sum(r.uren) as uren "
		+ "from tblregistratie r "
		+ "join tblproject p using (idproject) "
		+ "where opdrachtgever = ?"
		+ "group by p.opdrachtgever"
		+ ", date_format(r.startdatum, '%Y%m')";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setString(1, opdrachtgever);

	    try (ResultSet result = stmt.executeQuery()) {

		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setOpdrachtgever(result.getString("opdrachtgever"));
		    t.setPeriode(result.getString("jaarmaand"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();

    }

    public static List<Totaaloverzicht> getTotaalUrenPerProjectPerMaand(int idproject) {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "select p.naam as project"
		+ ", date_format(r.startdatum, '%Y-%m') as jaarmaand"
		+ ", sum(r.uren) as uren "
		+ "from tblregistratie r "
		+ "join tblproject p using (idproject) "
		+ "where idproject = ? "
		+ "group by p.naam"
		+ ", date_format(r.startdatum, '%Y%m')";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idproject);

	    try (ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		    t.setPeriode(result.getString("jaarmaand"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();

    }

    public static List<Totaaloverzicht> getTotaalUrenVerlofPerMaand() {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "select p.naam as project"
		+ ", date_format(r.startdatum, '%Y-%m') as jaarmaand"
		+ ", sum(r.uren) as uren "
		+ "from tblregistratie r "
		+ "join tblproject p using (idproject) "
		+ "where p.naam = 'verlof' "
		+ "group by p.naam"
		+ ", date_format(r.startdatum, '%Y%m')";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

	    try (ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		    t.setPeriode(result.getString("jaarmaand"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();

    }

    public static List<Totaaloverzicht> getTotaalUrenVerlofPerMedewerkerPerMaand(int idmedewerker) {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "select m.naam as medewerker"
		+ ", p.naam as project"
		+ ", date_format(r.startdatum, '%Y-%m') as jaarmaand"
		+ ", sum(r.uren) as uren "
		+ "from tblregistratie r "
		+ "join tblproject p using (idproject) "
		+ "join tblmedewerker m using (idmedewerker) "
		+ "where p.naam = 'verlof' "
		+ "and m.idmedewerker = ? "
		+ "group by m.naam"
		+ ", date_format(r.startdatum, '%Y%m')";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setInt(1, idmedewerker);

	    try( ResultSet result = stmt.executeQuery()) {
		while (result.next()) {
		    Totaaloverzicht t = new Totaaloverzicht();
		    t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		    t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		    t.setPeriode(result.getString("jaarmaand"));
		    t.setAantal(result.getInt("uren"));

		    totalen.add(t);
		}
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();

    }

    /**
     * Deze methode verwerkt de JSON input data van het urenregistratieformulier. De
     * data moet bestaan uit: idmedewerker, idproject, datum en uren. De data van
     * een volledige week wordt geleverd. Om deze goed te verwerken wordt eventueel
     * aanwezige data van die medewerker in week verwijderd en weer gevuld met de
     * nieuwe data van de medewerker in die week.
     **/
    public static boolean registratieUpdate(List<RegistratieJSON> registraties) throws SQLException {
	String sql = "delete from tblregistratie where (idmedewerker=? and startdatum=?)";

	for (RegistratieJSON d : registraties) {
	    try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
		stmt.setInt(1, d.getidMedewerker());
		stmt.setDate(2, d.getStartdatum());
		stmt.execute();
	    } catch (SQLException e) {
		logger.error(e.getErrorCode() + ": " + e.getMessage());
		return false;
	    }
	}

	sql = "insert into tblregistratie (idmedewerker, idproject, startdatum, uren) values (?, ?, ?, ?) ";

	for (RegistratieJSON r : registraties) {

	    try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {

		stmt.setInt(1, r.getidMedewerker());
		stmt.setInt(2, r.getidProject());
		stmt.setDate(3, r.getStartdatum());
		stmt.setDouble(4, r.getUren());
		stmt.execute();
	    } catch (SQLException e) {
		logger.error(e.getErrorCode() + ": " + e.getMessage());
		return false;
	    }
	}
	return true;
    }

    private RegistratieRepository() {
    }
}
