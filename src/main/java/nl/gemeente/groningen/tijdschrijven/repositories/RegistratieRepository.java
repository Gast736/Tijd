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
	private String periode;
	private Project project;
	private String opdrachtgever;


	public int getAantal() {
	    return aantal;
	}
	public Medewerker getMedewerker() {
	    return medewerker;
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
	public void setPeriode(String periode) {
	    this.periode = periode;
	}
	public void setProject(Project project) {
	    this.project = project;
	}
	public String getOpdrachtgever() {
	    return opdrachtgever;
	}
	public void setOpdrachtgever(String opdrachtgever) {
	    this.opdrachtgever = opdrachtgever;
	}

    }
    private static final String CATEGORIE = "categorie";
    private static final String CONTRACTUREN = "contracturen";
    private static final String DIRECTIE = "directie";
    private static final String EINDDATUM = "einddatum";
    private static final String EMAILADRES = "emailadres";
    private static final String IDMEDEWERKER = "idmedewerker";
    private static final String IDPROJECT = "idproject";
    private static final Logger logger = Logger.getLogger(RegistratieRepository.class);
    private static final String NAAM = "naam";
    private static final String OPDRACHTGEVER = "opdrachtgever";
    private static final String ROL = "rol";
    private static final String STARTDATUM = "startdatum";
    private static final String TEAM = "team";
    private static final String UREN = "uren";

    private static final String WACHTWOORD = "wachtwoord";

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
		medewerker.setIdmedewerker(result.getInt(IDMEDEWERKER));
		medewerker.setEmailadres(result.getString(EMAILADRES));
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
		medewerker.setIdmedewerker(result.getInt(IDMEDEWERKER));
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
		medewerker.setIdmedewerker(result.getInt(IDMEDEWERKER));
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
		medewerker.setIdmedewerker(result.getInt(IDMEDEWERKER));
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

		RegistratieJSON j = new RegistratieJSON();
		j.setidMedewerker(result.getInt(IDMEDEWERKER));
		j.setidProject(result.getInt(IDPROJECT));
		j.setStartdatum(result.getDate(STARTDATUM));
		j.setUren(result.getDouble(UREN));

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

    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerJaar(String idmedewerker, String begindatum, String einddatum) throws SQLException {
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
	    stmt.setString(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		t.setPeriode(result.getString("jaar"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();
    }

    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerKwartaal(String idmedewerker, String begindatum, String einddatum) throws SQLException {
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
	    stmt.setString(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		t.setPeriode(result.getString("jaarkwart"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();
    }

    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerMaand(String idmedewerker, String begindatum, String einddatum) throws SQLException {
	List<Totaaloverzicht> totalen = new ArrayList<>();
	String sql = "SELECT " + 
		"	m.naam medewerker" + 
		",	p.naam project" + 
		",	date_format(r.startdatum, \"%Y%m\") jaarmaand" + 
		",	sum(uren) uren" + 
		"FROM tijd.tblregistratie r" + 
		"	join tblmedewerker m using (idmedewerker)" + 
		"    join tblproject p using (idproject)" + 
		"where" + 
		"	r.idmedewerker = ?" + 
		"and" + 
		"	r.startdatum between ? and ?" + 
		"group by " + 
		"	m.naam " + 
		",	p.naam " + 
		",	date_format(r.startdatum, \"%Y%m\")";
	try (PreparedStatement stmt = ConnectionManager.getConnection().prepareStatement(sql);) {
	    stmt.setString(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		t.setPeriode(result.getString("jaarmaand"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();
    }
    public static List<Totaaloverzicht> getTotaalUrenPerMedewerkerPerProjectPerWeek(String idmedewerker, String begindatum, String einddatum) throws SQLException {
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
	    stmt.setString(1, idmedewerker);
	    stmt.setString(2, begindatum);
	    stmt.setString(3, einddatum);

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		t.setPeriode(result.getString("jaarweek"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
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

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		t.setPeriode(result.getString("jaarmaand"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    result.close();
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

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setOpdrachtgever(result.getString("opdrachtgever"));
		t.setPeriode(result.getString("jaarmaand"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    result.close();
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();

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

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		t.setOpdrachtgever(result.getString("opdrachtgever"));
		t.setPeriode(result.getString("jaarmaand"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    result.close();
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

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		t.setPeriode(result.getString("jaarmaand"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    result.close();
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

	    ResultSet result = stmt.executeQuery();
	    while (result.next()) {
		Totaaloverzicht t = new Totaaloverzicht();
		t.setMedewerker(MedewerkerRepository.getMedewerkerByNaam(result.getString("medewerker")));
		t.setProject(ProjectRepository.getProjectByNaam(result.getString("project")));
		t.setPeriode(result.getString("jaarmaand"));
		t.setAantal(result.getInt("uren"));

		totalen.add(t);
	    }
	    result.close();
	    return totalen;
	} catch (SQLException e) {
	    logger.error(e.getErrorCode() + " - " + e.getMessage());
	}
	return Collections.emptyList();

    }
}
