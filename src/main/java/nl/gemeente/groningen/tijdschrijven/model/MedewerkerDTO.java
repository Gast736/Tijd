package nl.gemeente.groningen.tijdschrijven.model;

import java.io.Serializable;
import java.util.Date;

public class MedewerkerDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private double contracturen;
    private Date einddatum;
    private int idmedewerker;
    private String naam;
    private String rol;
    private Date startdatum;
    private String team;
    private String wachtwoord;

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
