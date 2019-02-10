package nl.gemeente.groningen.tijdschrijven;

import java.util.Calendar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatumController {
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

    @GetMapping("/eersteDagVanDeMaand")
    public Calendar getEersteDagVanDeMaand(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	eersteDagVanDeMaand = cal;

	return eersteDagVanDeMaand;
    }

    @GetMapping("/eersteDagVanDeWeek")
    public Calendar getEersteDagVanDeWeek(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	eersteDagVanDeWeek = cal;
	return eersteDagVanDeWeek;
    }

    @GetMapping("/eersteDagVanJaar")
    public Calendar getEersteDagVanJaar(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
	eersteDagVanJaar = cal;

	return eersteDagVanJaar;
    }

    @GetMapping("/eersteDagVanKwartaal")
    public Calendar getEersteDagVanKwartaal(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	eersteDagVanKwartaal = cal;

	return eersteDagVanKwartaal;
    }

    @GetMapping("/kwartaalNummer")
    public int getKwartaalNummer(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	kwartaalNummer = (cal.get(Calendar.MONTH) / 3) + 1;
	return kwartaalNummer;
    }

    @GetMapping("/laatsteDagVanDeMaand")
    public Calendar getLaatsteDagVanDeMaand(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanDeMaand = cal;
	return laatsteDagVanDeMaand;
    }

    @GetMapping("/laatsteDagVanDeWeek")
    public Calendar getLaatsteDagVanDeWeek(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	laatsteDagVanDeWeek = cal;
	return laatsteDagVanDeWeek;
    }

    @GetMapping("/laatsteDagVanJaar")
    public Calendar getLaatsteDagVanJaar(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
	laatsteDagVanJaar = cal;
	return laatsteDagVanJaar;
    }

    @GetMapping("/laatsteDagVanKwartaal")
    public Calendar getLaatsteDagVanKwartaal(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanKwartaal = cal;
	return laatsteDagVanKwartaal;
    }

    @GetMapping("/maandNummer")
    public int getMaandNummer(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	maandNummer = cal.get(Calendar.MONTH) + 1;
	return maandNummer;
    }

    @GetMapping("/weeknummer")
    public int getWeekNummer(@RequestParam(name = "datum") String datum) {
	Calendar cal = StringToDate(datum);
	weekNummer = cal.get(Calendar.WEEK_OF_YEAR);
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

    public Calendar StringToDate(String datum) {
	String[] datumdeel = datum.split("-");
	Calendar cal = Calendar.getInstance();
	cal.setFirstDayOfWeek(Calendar.MONDAY);
	cal.setMinimalDaysInFirstWeek(4);
	cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(datumdeel[0]));
	cal.set(Calendar.MONTH, Integer.parseInt(datumdeel[1]));
	cal.set(Calendar.YEAR, Integer.parseInt(datumdeel[2]));

	return cal;
    }

    @Override
    public String toString() {
	return "Datum [weekNummer=" + weekNummer + ", eersteDagVanDeWeek=" + eersteDagVanDeWeek
		+ ", laatsteDagVanDeWeek=" + laatsteDagVanDeWeek + ", maandNummer=" + maandNummer
		+ ", eersteDagVanDeMaand=" + eersteDagVanDeMaand + ", laatsteDagVanDeMaand=" + laatsteDagVanDeMaand
		+ ", kwartaalNummer=" + kwartaalNummer + ", eersteDagVanKwartaal=" + eersteDagVanKwartaal
		+ ", laatsteDagVanKwartaal=" + laatsteDagVanKwartaal + ", eersteDagVanJaar=" + eersteDagVanJaar
		+ ", laatsteDagVanJaar=" + laatsteDagVanJaar + "]";
    }
}
