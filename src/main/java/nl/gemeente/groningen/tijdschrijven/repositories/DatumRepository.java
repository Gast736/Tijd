package nl.gemeente.groningen.tijdschrijven.repositories;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

public class DatumRepository {

    public static Map<String, Object> getDatums(@RequestParam(name = "datum") String datum) {
	Date eersteDagVanDeMaand;
	Date eersteDagVanDeWeek;
	Date eersteDagVanJaar;
	Date eersteDagVanKwartaal;
	int kwartaalNummer;
	Date laatsteDagVanDeMaand;
	Date laatsteDagVanDeWeek;
	Date laatsteDagVanJaar;
	Date laatsteDagVanKwartaal;
	int maandNummer;
	int weekNummer;
	Map<String, Object> datums = new HashMap<>();

	Calendar cal = stringToDate(datum);

	// WEEK
	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	weekNummer = cal.get(Calendar.WEEK_OF_YEAR);
	eersteDagVanDeWeek = cal.getTime();

	cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	laatsteDagVanDeWeek = cal.getTime();

	// MAAND
	cal.set(Calendar.DAY_OF_MONTH, 1);
	maandNummer = cal.get(Calendar.MONTH) + 1;
	eersteDagVanDeMaand = cal.getTime();

	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanDeMaand = cal.getTime();

	// KWARTAAL
	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3);
	cal.set(Calendar.DAY_OF_MONTH, 1);
	kwartaalNummer = (cal.get(Calendar.MONTH) / 3) + 1;
	eersteDagVanKwartaal = cal.getTime();

	cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) / 3 * 3 + 2);
	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	laatsteDagVanKwartaal = cal.getTime();

	// JAAR
	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
	eersteDagVanJaar = cal.getTime();

	cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
	cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
	laatsteDagVanJaar = cal.getTime();

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

    private static Calendar stringToDate(String datum) {
	String[] datumdeel = datum.split("-");

	Calendar cal = Calendar.getInstance();
	cal.setFirstDayOfWeek(Calendar.MONDAY);
	cal.setMinimalDaysInFirstWeek(4);
	cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(datumdeel[0]));
	cal.set(Calendar.MONTH, Integer.parseInt(datumdeel[1]) - 1);
	cal.set(Calendar.YEAR, Integer.parseInt(datumdeel[2]));

	return cal;
    }

    private DatumRepository() {
    }
}
