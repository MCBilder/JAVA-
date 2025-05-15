import java.time.LocalTime;

public abstract class ClockHand {
    protected double angle;

    /**
     * Ustawia kąt wskazówki na podstawie czasu.
     */
    public abstract void setTime(LocalTime time);

    /**
     * Zwraca znacznik SVG dla wskazówki, przesuniętej do punktu (centerX, centerY).
     */
    public abstract String toSvg(int centerX, int centerY);
}