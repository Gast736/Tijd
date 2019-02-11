package nl.gemeente.groningen.tijdschrijven;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatumController {
    private SimpleDateFormat dateFormat;
    private String eersteDagVanDeMaand;
    private String eersteDagVanDeWeek;
    private String eersteDagVanJaar;
    private String eersteDagVanKwartaal;
    private int kwartaalNummer;
    private String laatsteDagVanDeMaand;
    private String laatsteDagVanDeWeek;
    private String laatsteDagVanJaar;
    private String laatsteDagVanKwartaal;
    private int maandNummer;
    private int weekNummer;

    @GetMapping("/datum")
    public Map<String, Object> getDatums(@RequestParam(name = "datum") String datum) {
	Map<String, Object> datums = new HashMap<>();
	Calendar cal = stringToDate(datum);

	// WEEK
	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	weekNummer = cal.get(Calendar.WEEK_OF_YEAR);
	eersteDagVanDeWeek = dateFormat.format(cal.getTime());

	cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	laatsteDagVanDeWeek = dateFormat.format(cal.getTime());

	// MAAND
	cal.set(Calendar.DAY_OF_MONTH, 1);
	maandNummer = cal.get(Calendar.MONTH) + 1;
	eersteDagVanDeMaand = dateFormat.format(cal.getTime());

	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanDeMaand = dateFormat.format(cal.getTime());

	// KWARTAAL
	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	kwartaalNummer = (cal.get(Calendar.MONTH) / 3) + 1;
	eersteDagVanKwartaal = dateFormat.format(cal.getTime());

	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanKwartaal = dateFormat.format(cal.getTime());

	// JAAR
	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
	eersteDagVanJaar = dateFormat.format(cal.getTime());

	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
	laatsteDagVanJaar = dateFormat.format(cal.getTime());

	datums.put("weekNummer", weekNummer);
	datums.put("eersteDagVanDeWeek", eersteDagVanDeWeek);
	datums.put("laatsteDagVanDeWeek", laatsteDagVanDeWeek);
	datums.put("maandNummer", maandNummer);
	datums.put("eersteDagVanDeMaand", eersteDagVanDeMaand);
	datums.put("laatsteDagVanDeMaand", laatsteDagVanDeMaand);
	datums.put("kwartaalNummer", kwartaalNummer);
	datums.put("eersteDagVanKwartaal", eersteDagVanKwartaal);
	datums.put("laatsteDagVanKwartaal", laatsteDagVanKwartaal);
	datums.put("eersteDagVanJaar", eersteDagVanJaar);
	datums.put("laatsteDagVanJaar", laatsteDagVanJaar);
	return datums;

    }

    public Calendar stringToDate(String datum) {
	String[] datumdeel = datum.split("-");

	dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Calendar cal = Calendar.getInstance();
	cal.setFirstDayOfWeek(Calendar.MONDAY);
	cal.setMinimalDaysInFirstWeek(4);
	cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(datumdeel[0]));
	cal.set(Calendar.MONTH, Integer.parseInt(datumdeel[1]) - 1);
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
