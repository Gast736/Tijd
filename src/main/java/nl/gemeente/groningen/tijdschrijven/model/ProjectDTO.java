package nl.gemeente.groningen.tijdschrijven.model;

import java.io.Serializable;
import java.util.Date;

public class ProjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String categorie;
    private String directie;
    private Date einddatum;
    private int idProject;
    private String naam;
    private String opdrachtgever;
    private Date startdatum;

    public String getCategorie() {
	return categorie;
    }

    public String getDirectie() {
	return directie;
    }

    public Date getEinddatum() {
	return einddatum;
    }

    public int getIdProject() {
	return idProject;
    }

    public String getNaam() {
	return naam;
    }

    public String getOpdrachtgever() {
	return opdrachtgever;
    }

    public Date getStartdatum() {
	return startdatum;
    }

    public void setCategorie(String categorie) {
	this.categorie = categorie;
    }

    public void setDirectie(String directie) {
	this.directie = directie;
    }

    public void setEinddatum(Date einddatum) {
	this.einddatum = einddatum;
    }

    public void setIdProject(int idProject) {
	this.idProject = idProject;
    }

    public void setNaam(String naam) {
	this.naam = naam;
    }

    public void setOpdrachtgever(String opdrachtgever) {
	this.opdrachtgever = opdrachtgever;
    }

    public void setStartdatum(Date startdatum) {
	this.startdatum = startdatum;
    }

    @Override
    public String toString() {
	return "Project [idProject=" + idProject + ", naam=" + naam + ", categorie=" + categorie + ", opdrachtgever="
		+ opdrachtgever + ", directie=" + directie + ", startdatum=" + startdatum + ", einddatum=" + einddatum
		+ "]";
    }
}
