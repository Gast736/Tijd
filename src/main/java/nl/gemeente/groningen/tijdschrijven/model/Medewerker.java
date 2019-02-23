package nl.gemeente.groningen.tijdschrijven.model;

import java.io.Serializable;
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
@Table(name = "tblmedewerker")
public class Medewerker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(scale = 4, precision = 2, nullable = false)
    private double contracturen;

    @Temporal(TemporalType.DATE)
    private Date einddatum;

    @Column(name = "emailadres", length = 50, nullable = false, unique = true)
    private String emailadres;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmedewerker")
    private int idmedewerker;

    @Column(length = 45, nullable = false)
    private String naam;

    @Column(length = 45, nullable = false)
    private String rol;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startdatum;

    @Column(length = 45)
    private String team;

    @Column(length = 10)
    private String wachtwoord;

    public double getContracturen() {
	return contracturen;
    }

    public Date getEinddatum() {
	return einddatum;
    }

    public String getEmailadres() {
	return emailadres;
    }

    public int getIdmedewerker() {
	return idmedewerker;
    }

    public String getNaam() {
	return naam;
    }

    public String getRol() {
	return rol;
    }

    public Date getStartdatum() {
	return startdatum;
    }

    public String getTeam() {
	return team;
    }

    public String getWachtwoord() {
	return wachtwoord;
    }

    public void setContracturen(double contracturen) {
	this.contracturen = contracturen;
    }

    public void setEinddatum(Date einddatum) {
	this.einddatum = einddatum;
    }

    public void setEmailadres(String emailadres) {
	this.emailadres = emailadres;
    }

    public void setIdmedewerker(int idmedewerker) {
	this.idmedewerker = idmedewerker;
    }

    public void setNaam(String naam) {
	this.naam = naam;
    }

    public void setRol(String rol) {
	this.rol = rol;
    }

    public void setStartdatum(Date startdatum) {
	this.startdatum = startdatum;
    }

    public void setTeam(String team1) {
	team = team1;
    }

    public void setWachtwoord(String wachtwoord) {
	this.wachtwoord = wachtwoord;
    }

}
