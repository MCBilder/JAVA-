import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class AnalogClock extends Clock {
    private final List<ClockHand> hands;

    public AnalogClock(City city) {
        super(city);
        this.hands = List.of(new HourHand(), new MinuteHand(), new SecondHand());
        setCurrentTime();  // wywołanie po inicjalizacji hands
    }

    private void updateHands() {
        for (ClockHand hand : hands) {
            hand.setTime(getTime());
        }
    }

    @Override
    public void setCurrentTime() {
        super.setCurrentTime();
        updateHands();
    }

    @Override
    public void setTime(int h, int m, int s) {
        super.setTime(h, m, s);
        updateHands();
    }

    @Override
    public void setCity(City newCity) {
        super.setCity(newCity);
        updateHands();
    }

    public void toSvg(String filePath) {
        int size = 200;
        int center = size / 2;

        StringBuilder svg = new StringBuilder();
        svg.append(String.format(
                "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\">\n", size, size));
        svg.append(String.format(
                "<circle cx=\"%d\" cy=\"%d\" r=\"%d\" stroke=\"black\" stroke-width=\"4\" fill=\"white\" />\n",
                center, center, center - 5));

        for (ClockHand hand : hands) {
            svg.append(hand.toSvg(center, center));
        }

        svg.append("</svg>");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(svg.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return super.toString().replace(":", "-");
    }
}