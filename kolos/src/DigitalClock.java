import java.time.LocalTime;

public class DigitalClock extends Clock {

    public enum TimeFormat {
        H12, H24
    }

    private TimeFormat format;

    public DigitalClock(TimeFormat format, City city) {
        super(city);
        this.format = format;
    }

    @Override
    public String toString() {
        LocalTime time = getTime();
        if (time == null) return "czas nieustawiony";

        if (format == TimeFormat.H24) {
            return super.toString();
        } else {
            int hour = time.getHour();
            int minute = time.getMinute();
            int second = time.getSecond();

            String amPm = (hour < 12) ? "AM" : "PM";
            hour = hour % 12;
            if (hour == 0) hour = 12;

            return String.format("%d:%02d:%02d %s", hour, minute, second, amPm);
        }
    }
}