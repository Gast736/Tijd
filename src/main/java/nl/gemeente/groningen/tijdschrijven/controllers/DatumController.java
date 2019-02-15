package nl.gemeente.groningen.tijdschrijven.controllers;

import java.util.Calendar;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.gemeente.groningen.tijdschrijven.repositories.DatumRepository;

@RestController
public class DatumController {
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
    public Map<String, Object> getDatums(@RequestParam(name = "jaar") String jaar, @RequestParam(name = "week") String week) {
	return DatumRepository.getDatums(jaar, week);
    }

    @GetMapping("/daysInWeek")
    public Map<Integer, Object> getDaysInWeek(@RequestParam(name = "jaar") String jaar, @RequestParam(name = "week") String week) {
	return DatumRepository.getDaysInWeek(jaar, week);
    }

    public Calendar stringToDate(String datum) {
	String[] datumdeel = datum.split("-");

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
