import java.time.LocalTime;

public class HourHand extends ClockHand {

    @Override
    public void setTime(LocalTime time) {
        double hours = time.getHour() % 12;
        double minutes = time.getMinute();
        double seconds = time.getSecond();
        angle = (hours + minutes / 60.0 + seconds / 3600.0) * 30; // 360° / 12h = 30° na godzinę
    }

    @Override
    public String toSvg(int centerX, int centerY) {
        double length = 50;
        double rad = Math.toRadians(angle - 90); // -90 by wskazówka była pionowo w górę

        int x2 = (int) (centerX + length * Math.cos(rad));
        int y2 = (int) (centerY + length * Math.sin(rad));

        return String.format(
                "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"black\" stroke-width=\"5\" />\n",
                centerX, centerY, x2, y2);
    }
}