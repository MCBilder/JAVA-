import java.time.LocalTime;

public class SecondHand extends ClockHand {

    @Override
    public void setTime(LocalTime time) {
        int seconds = time.getSecond();
        angle = seconds * 6; // 360° / 60s = 6° na sekundę
    }

    @Override
    public String toSvg(int centerX, int centerY) {
        double length = 90;
        double rad = Math.toRadians(angle - 90);

        int x2 = (int) (centerX + length * Math.cos(rad));
        int y2 = (int) (centerY + length * Math.sin(rad));

        return String.format(
                "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"red\" stroke-width=\"1\" />\n",
                centerX, centerY, x2, y2);
    }
}