package nl.gemeente.groningen.tijdschrijven;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zomaareentabel")
public class Test {
	
	public Test() {
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tabelId")
	private int id;
	@Column(name="kolom_naam", nullable=false, length=10)
	private String naam;
	@Column(name="kolomGenaamdUren", precision=1)
	private double uren;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public double getUren() {
		return uren;
	}
	@Override
	public String toString() {
		return "Test [id=" + id + ", naam=" + naam + ", uren=" + uren + "]";
	}
}
