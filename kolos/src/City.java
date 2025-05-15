import java.io.*;
import java.util.*;
import java.time.LocalTime;
import java.time.Duration;

public class City {
    private String name;
    private int timezoneOffset; // przesunięcie strefy w godzinach
    private double longitude;
    private double latitude;

    public City(String name, int timezoneOffset, double longitude, double latitude) {
        this.name = name;
        this.timezoneOffset = timezoneOffset;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    private static City parseLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(",");

        if (parts.length < 4) {
            System.err.println("Niepoprawny format linii w CSV: " + line);
            return null;
        }

        try {
            String name = parts[0].trim();

            int offset = Integer.parseInt(parts[1].trim());

            String latStr = parts[2].trim();
            double latitude = parseCoordinate(latStr);

            String lonStr = parts[3].trim();
            double longitude = parseCoordinate(lonStr);

            return new City(name, offset, longitude, latitude);

        } catch (NumberFormatException e) {
            System.err.println("Błąd parsowania liczb w linii CSV: " + line);
            return null;
        }
    }

    private static double parseCoordinate(String coord) {
        String[] parts = coord.split(" ");
        if (parts.length != 2) {
            throw new NumberFormatException("Niepoprawny format współrzędnej: " + coord);
        }
        double value = Double.parseDouble(parts[0]);
        String direction = parts[1].toUpperCase();

        if ("S".equals(direction) || "W".equals(direction)) {
            value = -value;
        }
        return value;
    }

    public static Map<String, City> parseFile(String path) {
        Map<String, City> cityMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine(); // pomijamy nagłówek

            while ((line = reader.readLine()) != null) {
                City city = parseLine(line);
                if (city != null) {
                    cityMap.put(city.getName(), city);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityMap;
    }

    public LocalTime localMeanTime(LocalTime zonalTime) {
        double offsetFromZone = (longitude / 15.0) - timezoneOffset;
        int totalSeconds = (int) Math.round(offsetFromZone * 3600);
        return zonalTime.plusSeconds(totalSeconds);
    }

    public static Comparator<City> worstTimezoneFit() {
        return (c1, c2) -> {
            LocalTime ref = LocalTime.NOON;
            long diff1 = Math.abs(Duration.between(ref, c1.localMeanTime(ref)).toSeconds());
            long diff2 = Math.abs(Duration.between(ref, c2.localMeanTime(ref)).toSeconds());
            return Long.compare(diff2, diff1);
        };
    }

    public static void generateAnalogClocksSvg(List<City> cities, AnalogClock clock) {
        String dirName = clock.toString();
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }

        for (City city : cities) {
            clock.setCity(city);
            String filePath = dirName + "/" + city.getName().replace(" ", "_") + ".svg";
            clock.toSvg(filePath);
        }
    }
}