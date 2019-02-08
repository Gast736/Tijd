package nl.gemeente.groningen.tijdschrijven;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TijdController {

    Datasource ds = new Datasource();

    @GetMapping("/medewerkers")
    public ArrayList<Medewerker> getMedewerkers() throws SQLException {
        ArrayList<Medewerker> mdw = new ArrayList<>(); 
    	try (PreparedStatement stmt = ds.makeConnection().prepareStatement("SELECT * FROM tblMedewerker")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
               Medewerker m = new Medewerker();
                	m.idmedewerker = rs.getInt("idmedewerker");
                	m.naam = rs.getString("naam");
                	m.wachtwoord = rs.getString("wachtwoord");
                	m.team = rs.getString("team");
                	m.rol = rs.getString("rol");
                	m.contracturen = rs.getDouble("contracturen");
                	m.startdatum = rs.getDate("startdatum");
                	m.einddatum = rs.getDate("einddatum");
                	mdw.add(m);
                }
    	}
    	return mdw;
    }
    
    @GetMapping("/Medewerker")
    public Medewerker getMedewerker(@RequestParam(name="id") int id) throws SQLException {
        try (PreparedStatement stmt = ds.makeConnection().prepareStatement("SELECT * FROM tblMedewerker WHERE idmedewerker = ?")) {
        	stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return new Medewerker(rs.getInt("idmedewerker"), rs.getString("naam"),rs.getString("wachtwoord"), 
                		rs.getString("team"), rs.getString("rol"), rs.getDouble("contracturen"), rs.getDate("startdatum"),
                		rs.getDate("einddatum"));
            }
        }
    }
}
