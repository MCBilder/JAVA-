import java.time.LocalTime;

public abstract class Clock {
    private City city;
    private LocalTime time;

    public Clock(City city) {
        this.city = city;
        // Nie wywołujemy setCurrentTime() tutaj, bo może być nadpisane w podklasach
    }

    public void setCurrentTime() {
        this.time = LocalTime.now();
    }

    public void setTime(int hour, int minute, int second) {
        if (hour < 0 || hour > 23)
            throw new IllegalArgumentException("Godzina poza zakresem (0-23): " + hour);
        if (minute < 0 || minute > 59)
            throw new IllegalArgumentException("Minuta poza zakresem (0-59): " + minute);
        if (second < 0 || second > 59)
            throw new IllegalArgumentException("Sekunda poza zakresem (0-59): " + second);

        this.time = LocalTime.of(hour, minute, second);
    }

    public LocalTime getTime() {
        return time;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        int diff = city.getTimezoneOffset() - this.city.getTimezoneOffset();
        this.city = city;
        if (time != null) {
            LocalTime newTime = time.plusHours(diff);
            setTime(newTime.getHour(), newTime.getMinute(), newTime.getSecond());
        }
    }

    @Override
    public String toString() {
        if (time == null) return "czas nieustawiony";
        return String.format("%02d:%02d:%02d", time.getHour(), time.getMinute(), time.getSecond());
    }
}