package nl.gemeente.groningen.tijdschrijven.model;

import java.io.Serializable;
import java.util.Date;

public class RegistratieDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MedewerkerDTO medewerker;
    private ProjectDTO project;
    private Date startdatum;
    private double uren;

    public MedewerkerDTO getMedewerker() {
	return medewerker;
    }

    public ProjectDTO getProject() {
	return project;
    }

    public Date getStartdatum() {
	return startdatum;
    }

    public double getUren() {
	return uren;
    }

    public void setMedewerker(MedewerkerDTO medewerker) {
	this.medewerker = medewerker;
    }

    public void setProject(ProjectDTO project) {
	this.project = project;
    }

    public void setStartdatum(Date startdatum) {
	this.startdatum = startdatum;
    }

    public void setUren(double uren) {
	this.uren = uren;
    }

    @Override
    public String toString() {
	return "Registratie [medewerker=" + medewerker + ", project=" + project + ", startdatum=" + startdatum
		+ ", uren=" + uren + "]";
    }

}
