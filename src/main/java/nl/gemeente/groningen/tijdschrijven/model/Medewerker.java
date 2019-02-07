package nl.gemeente.groningen.tijdschrijven.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tblmedewerker")
public class Medewerker {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idmedewerker")
    private int idMedewerker;
    
    @Column(length=45)
    private String naam;
    
    @Column(length=10)
    private String wachtwoord;
    
    @Column(length=45)
    private String team;
    
    @Column(length=45)
    private String rol;
    
    @Column(scale=4, precision=2)
    private double contracturen;
    
    @Temporal(TemporalType.DATE)
    private Date startdatum;

    @Temporal(TemporalType.DATE)
    private Date einddatum;

    public int getIdMedewerker() {
        return idMedewerker;
    }

    public void setIdMedewerker(int idMedewerker) {
        this.idMedewerker = idMedewerker;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team1) {
        team = team1;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public double getContracturen() {
        return contracturen;
    }

    public void setContracturen(double contracturen) {
        this.contracturen = contracturen;
    }

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public Date getEinddatum() {
        return einddatum;
    }

    public void setEinddatum(Date einddatum) {
        this.einddatum = einddatum;
    }

    @Override
    public String toString() {
	return "Medewerker [idMedewerker=" + idMedewerker + ", naam=" + naam + ", wachtwoord=" + wachtwoord + ", team="
		+ team + ", rol=" + rol + ", contracturen=" + contracturen + ", startdatum=" + startdatum
		+ ", einddatum=" + einddatum + "]";
    }


}
