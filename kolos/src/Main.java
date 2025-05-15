import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. Wczytaj dane miast z pliku strefy.csv
        Map<String, City> cityMap = City.parseFile("strefy.csv");
        List<City> cities = new ArrayList<>(cityMap.values());

        // 2. Test DigitalClock – tryb 24h i 12h
        City warszawa = cityMap.get("Warszawa");
        DigitalClock clock24 = new DigitalClock(DigitalClock.TimeFormat.H24, warszawa);
        clock24.setTime(13, 5, 0);
        System.out.println("Zegar 24h: " + clock24);

        DigitalClock clock12 = new DigitalClock(DigitalClock.TimeFormat.H12, warszawa);
        clock12.setTime(13, 5, 0);
        System.out.println("Zegar 12h: " + clock12);

        // 3. Test localMeanTime
        LocalTime sampleTime = LocalTime.of(12, 0, 0);
        LocalTime meanTime = warszawa.localMeanTime(sampleTime);
        System.out.println("Czas miejscowy w Warszawie: " + meanTime);

        // 4. Sortowanie wg niedopasowania strefy
        cities.sort(City.worstTimezoneFit());
        System.out.println("Miasta wg rozbieżności czasu miejscowego:");
        for (int i = 0; i < 10 && i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i).getName());
        }

        // 5. AnalogClock – generowanie SVG
        AnalogClock analogClock = new AnalogClock(warszawa);
        analogClock.setTime(10, 15, 30);
        analogClock.toSvg("zegar_warszawa.svg");

        // 6. Generowanie SVG dla wszystkich miast
        City.generateAnalogClocksSvg(cities, analogClock);
        System.out.println("Wygenerowano pliki SVG w katalogu: " + analogClock.toString());
    }
}