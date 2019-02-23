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
@Table(name = "tblproject")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(length = 45)
    private String categorie;
    
    @Column(length = 45)
    private String directie;

    @Temporal(TemporalType.DATE)
    private Date einddatum;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproject")
    private int idproject;

    @Column(length = 45)
    private String naam;

    @Column(length = 45)
    private String opdrachtgever;

    @Temporal(TemporalType.DATE)
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
	return idproject;
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

    public void setIdProject(int idproject) {
	this.idproject = idproject;
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
}
