package nl.gemeente.groningen.tijdschrijven.repositories;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DatumRepository {

    public static Map<String, Object> getDatums(String jaar, String week) {
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

	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, Integer.valueOf(jaar));
	cal.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(week));

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

    private DatumRepository() {
    }

    public static Map<Integer, Object> getDaysInWeek(String jaar, String week) {
	Map<Integer, Object> dagen = new HashMap<>();

	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, Integer.valueOf(jaar));
	cal.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(week));

	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

	for (int i = 0; i < 5; i++) {
	    dagen.put(i, cal.getTime());
	    cal.roll(Calendar.DATE, 1);
	}

	return dagen;

    }

    public static Map<Integer, Object> getDaysBetweenDates(String begindatum, String einddatum) {

	Map<Integer, Object> dagen = new HashMap<>();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMAN);
	LocalDate begin = LocalDate.parse(begindatum, formatter);
	LocalDate eind = LocalDate.parse((einddatum.isEmpty() ? "9999-12-31" : einddatum), formatter);

	int i = 0;
	while (!begin.isAfter(eind)) {
	    if (begin.getDayOfWeek().getValue() < 6) {
		dagen.put(i, begin);
	    }
	    i++;
	    begin = begin.plusDays(1);
	}
	return dagen;
    }
}
