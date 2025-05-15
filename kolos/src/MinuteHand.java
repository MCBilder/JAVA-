import java.time.LocalTime;

public class MinuteHand extends ClockHand {

    @Override
    public void setTime(LocalTime time) {
        double minutes = time.getMinute();
        double seconds = time.getSecond();
        angle = (minutes + seconds / 60.0) * 6; // 360° / 60min = 6° na minutę
    }

    @Override
    public String toSvg(int centerX, int centerY) {
        double length = 70;
        double rad = Math.toRadians(angle - 90);

        int x2 = (int) (centerX + length * Math.cos(rad));
        int y2 = (int) (centerY + length * Math.sin(rad));

        return String.format(
                "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"black\" stroke-width=\"3\" />\n",
                centerX, centerY, x2, y2);
    }
}