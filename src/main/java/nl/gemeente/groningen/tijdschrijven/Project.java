package nl.gemeente.groningen.tijdschrijven;

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
@Table(name="tblproject")
public class Project {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idproject")
    private int idProject;

    @Column(length=45)
    private String naam;

    @Column(length=45)
    private String categorie;

    @Column(length=45)
    private String opdrachtgever;

    @Column(length=45)
    private String directie;

    @Temporal(TemporalType.DATE)
    private Date startdatum;

    @Temporal(TemporalType.DATE)
    private Date einddatum;

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getOpdrachtgever() {
        return opdrachtgever;
    }

    public void setOpdrachtgever(String opdrachtgever) {
        this.opdrachtgever = opdrachtgever;
    }

    public String getDirectie() {
        return directie;
    }

    public void setDirectie(String directie) {
        this.directie = directie;
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
	return "Project [idProject=" + idProject + ", naam=" + naam + ", categorie=" + categorie + ", opdrachtgever="
		+ opdrachtgever + ", directie=" + directie + ", startdatum=" + startdatum + ", einddatum=" + einddatum
		+ "]";
    }
}
