package nl.gemeente.groningen.tijdschrijven.model;


public class Response {

private int aantal;
private String medewerker;
private String opdrachtgever;
private String periode;
private String project;


public int getAantal() {
    return aantal;
}
public String getMedewerker() {
    return medewerker;
}
public String getOpdrachtgever() {
    return opdrachtgever;
}
public String getPeriode() {
    return periode;
}
public String getProject() {
    return project;
}
public void setAantal(int aantal) {
    this.aantal = aantal;
}
public void setMedewerker(String medewerker) {
    this.medewerker = medewerker;
}
public void setOpdrachtgever(String opdrachtgever) {
    this.opdrachtgever = opdrachtgever;
}
public void setPeriode(String periode) {
    this.periode = periode;
}
public void setProject(String project) {
    this.project = project;
}

}

