package vttp.batch5.sdf.task01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import vttp.batch5.sdf.task01.models.BikeEntry;

public class Main {
	private static HashMap<String, Integer> csvdata = new HashMap<>();

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Error: CSV filename not provided. ");
			System.exit(0);
		}
		String csvfilename = args[0];

	}

	public static void fileprocessing(String csvfilename) throws FileNotFoundException, IOException {
		List<BikeEntry> bikeentries = new ArrayList<>();

		FileReader fr = new FileReader(csvfilename);
		BufferedReader br = new BufferedReader(fr);

		String line;
		br.readLine();

		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			BikeEntry entry = new BikeEntry();
			entry.setSeason(Integer.parseInt(values[0].trim()));
			entry.setMonth(Integer.parseInt(values[1].trim()));
			entry.setWeekday(Integer.parseInt(values[2].trim()));
			entry.setWeather(Integer.parseInt(values[3].trim()));
			entry.setTemperature(Float.parseFloat(values[4].trim()));
			entry.setHumidity(Float.parseFloat(values[5].trim()));
			entry.setWindspeed(Float.parseFloat(values[6].trim()));
			entry.setHoliday(Boolean.parseBoolean(values[7].trim()));
			entry.setCasual(Integer.parseInt(values[8].trim()));
			entry.setRegistered(Integer.parseInt(values[9].trim()));

			bikeentries.add(entry);
		}

		bikeentries.sort((a, b) -> {
			int totalA = a.getCasual() + a.getRegistered();
			int totalB = b.getCasual() + b.getRegistered();
			return Integer.compare(totalB, totalA);
		});

		for (int i = 0; i < Math.min(5, bikeentries.size()); i++) {
			BikeEntry entry = bikeentries.get(i);
			int total = entry.getCasual() + entry.getRegistered();
			String season = Utilities.toSeason(entry.getSeason());
			String weekday = Utilities.toWeekday(entry.getWeekday());
			String month = Utilities.MONTH[entry.getMonth() - 1];
			String weather = getWeatherDescription(entry.getWeather());

			System.out.printf(
					"The %s (position) recorded number of cyclist was in %s (season), on %s (day) in the month of %s (month).\n",
					getPosition(i + 1), season, weekday, month);
			System.out.printf("There were a total of %d cyclists. The weather was %s.\n",
					total, weather);
			System.out.printf("%s was %s.\n\n",
					weekday, entry.isHoliday() ? "a holiday" : "not a holiday");
		}

	}

	private static String getPosition(int pos) {
		switch (pos) {
			case 1:
				return "highest";
			case 2:
				return "second highest";
			case 3:
				return "third highest";
			case 4:
				return "fourth highest";
			case 5:
				return "fifth highest";
			default:
				return "";
		}
	}

	private static String getWeatherDescription(int weather) {
		switch (weather) {
			case 1:
				return "Clear, Few clouds, Partly cloudy, Partly cloudy";
			case 2:
				return "Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist";
			case 3:
				return "Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds";
			case 4:
				return "Heavy Rain + Ice Pallets + Thunderstorm + Mist, Snow + Fog";
			default:
				return "Unknown weather";
		}
	}

}
