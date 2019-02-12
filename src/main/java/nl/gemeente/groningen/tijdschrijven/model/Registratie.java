package nl.gemeente.groningen.tijdschrijven.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tblregistratie")
@Embeddable
public class Registratie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "idmedewerker")
    private Medewerker medewerker;

    @Id
    @ManyToOne
    @JoinColumn(name = "idproject")
    private Project project;

    @Id
    @Temporal(TemporalType.DATE)
    private Date startdatum;

    @Column(scale = 4, precision = 2)
    private double uren;

    public Medewerker getMedewerker() {
	return medewerker;
    }

    public Project getProject() {
	return project;
    }

    public Date getStartdatum() {
	return startdatum;
    }

    public double getUren() {
	return uren;
    }

    public void setMedewerker(Medewerker medewerker) {
	this.medewerker = medewerker;
    }

    public void setProject(Project project) {
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
