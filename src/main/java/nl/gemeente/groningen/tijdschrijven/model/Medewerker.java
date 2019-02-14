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

    @Column(scale = 4, precision = 2)
    public double contracturen;

    @Temporal(TemporalType.DATE)
    public Date einddatum;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmedewerker")
    public int idmedewerker;

    @Column(length = 45)
    public String naam;

    @Column(length = 45)
    public String rol;

    @Temporal(TemporalType.DATE)
    public Date startdatum;

    @Column(length = 45)
    public String team;

    @Column(length = 10)
    public String wachtwoord;

    public Medewerker() {

    }

    public Medewerker(String naam, String wachtwoord, String team, String rol, double contracturen, Date startdatum) {
	this.idmedewerker = idmedewerker;
	this.naam = naam;
	this.wachtwoord = wachtwoord;
	this.team = team;
	this.rol = rol;
	this.contracturen = contracturen;
	this.startdatum = startdatum;
	this.einddatum = einddatum;
    }

    public double getContracturen() {
	return contracturen;
    }

    public Date getEinddatum() {
	return einddatum;
    }

    public int getIdMedewerker() {
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

    public void setIdMedewerker(int idmedewerker) {
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

    @Override
    public String toString() {
	return "Medewerker [idmedewerker=" + idmedewerker + ", naam=" + naam + ", wachtwoord=" + wachtwoord + ", team="
		+ team + ", rol=" + rol + ", contracturen=" + contracturen + ", startdatum=" + startdatum
		+ ", einddatum=" + einddatum + "]";
    }

}
