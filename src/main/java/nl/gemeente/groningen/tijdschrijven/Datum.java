package nl.gemeente.groningen.tijdschrijven;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Datum {
    private Calendar eersteDagVanDeMaand;
    private Calendar eersteDagVanDeWeek;
    private Calendar eersteDagVanJaar;
    private Calendar eersteDagVanKwartaal;
    private int kwartaalNummer;
    private Calendar laatsteDagVanDeMaand;
    private Calendar laatsteDagVanDeWeek;
    private Calendar laatsteDagVanJaar;
    private Calendar laatsteDagVanKwartaal;
    private int maandNummer;
    private int weekNummer;

    public Datum(Calendar datum) {
	Calendar cal = datum;

	new SimpleDateFormat("EEEEE dd-MM-yyyy");

	cal.setFirstDayOfWeek(Calendar.MONDAY);
	cal.setMinimalDaysInFirstWeek(4);

	// WEEK
	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	weekNummer = cal.get(Calendar.WEEK_OF_YEAR);
	eersteDagVanDeWeek = cal;

	cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	laatsteDagVanDeWeek = cal;

	// MAAND
	cal.set(Calendar.DAY_OF_MONTH, 1);
	maandNummer = cal.get(Calendar.MONTH) + 1;
	eersteDagVanDeMaand = cal;

	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanDeMaand = cal;

	// KWARTAAL
	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	kwartaalNummer = (cal.get(Calendar.MONTH) / 3) + 1;
	eersteDagVanKwartaal = cal;

	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanKwartaal = cal;

	// JAAR
	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
	eersteDagVanJaar = cal;

	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
	laatsteDagVanJaar = cal;

    }

    public Calendar getEersteDagVanDeMaand() {
	return eersteDagVanDeMaand;
    }

    public Calendar getEersteDagVanDeWeek() {
	return eersteDagVanDeWeek;
    }

    public Calendar getEersteDagVanJaar() {
	return eersteDagVanJaar;
    }

    public Calendar getEersteDagVanKwartaal() {
	return eersteDagVanKwartaal;
    }

    public int getKwartaalNummer() {
	return kwartaalNummer;
    }

    public Calendar getLaatsteDagVanDeMaand() {
	return laatsteDagVanDeMaand;
    }

    public Calendar getLaatsteDagVanDeWeek() {
	return laatsteDagVanDeWeek;
    }

    public Calendar getLaatsteDagVanJaar() {
	return laatsteDagVanJaar;
    }

    public Calendar getLaatsteDagVanKwartaal() {
	return laatsteDagVanKwartaal;
    }

    public int getMaandNummer() {
	return maandNummer;
    }

    public int getWeekNummer() {
	return weekNummer;
    }

    public void setEersteDagVanDeMaand(Calendar eersteDagVanDeMaand) {
	this.eersteDagVanDeMaand = eersteDagVanDeMaand;
    }

    public void setEersteDagVanDeWeek(Calendar eersteDagVanDeWeek) {
	this.eersteDagVanDeWeek = eersteDagVanDeWeek;
    }

    public void setEersteDagVanJaar(Calendar eersteDagVanJaar) {
	this.eersteDagVanJaar = eersteDagVanJaar;
    }

    public void setEersteDagVanKwartaal(Calendar eersteDagVanKwartaal) {
	this.eersteDagVanKwartaal = eersteDagVanKwartaal;
    }

    public void setKwartaalNummer(int kwartaalNummer) {
	this.kwartaalNummer = kwartaalNummer;
    }

    public void setLaatsteDagVanDeMaand(Calendar laatsteDagVanDeMaand) {
	this.laatsteDagVanDeMaand = laatsteDagVanDeMaand;
    }

    public void setLaatsteDagVanDeWeek(Calendar laatsteDagVanDeWeek) {
	this.laatsteDagVanDeWeek = laatsteDagVanDeWeek;
    }

    public void setLaatsteDagVanJaar(Calendar laatsteDagVanJaar) {
	this.laatsteDagVanJaar = laatsteDagVanJaar;
    }

    public void setLaatsteDagVanKwartaal(Calendar laatsteDagVanKwartaal) {
	this.laatsteDagVanKwartaal = laatsteDagVanKwartaal;
    }

    public void setMaandNummer(int maandNummer) {
	this.maandNummer = maandNummer;
    }

    public void setWeekNummer(int weekNummer) {
	this.weekNummer = weekNummer;
    }

    @Override
    public String toString() {
	return "Datum [eersteDagVanDeMaand=" + eersteDagVanDeMaand + ", eersteDagVanDeWeek=" + eersteDagVanDeWeek
		+ ", eersteDagVanJaar=" + eersteDagVanJaar + ", eersteDagVanKwartaal=" + eersteDagVanKwartaal
		+ ", kwartaalNummer=" + kwartaalNummer + ", laatsteDagVanDeMaand=" + laatsteDagVanDeMaand
		+ ", laatsteDagVanDeWeek=" + laatsteDagVanDeWeek + ", laatsteDagVanJaar=" + laatsteDagVanJaar
		+ ", laatsteDagVanKwartaal=" + laatsteDagVanKwartaal + ", maandNummer=" + maandNummer + ", weekNummer="
		+ weekNummer + "]";
    }

}
