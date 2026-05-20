public class ThresholdRange {
    private double minValue;
    private double maxValue;
    private double warningMin;
    private double warningMax;

    /** Critical range only – warning band equals the full range (no WARNING level possible). */
    public ThresholdRange(double minValue, double maxValue) {
        this(minValue, maxValue, minValue, maxValue);
    }

    /** Full constructor: [minValue, warningMin) and (warningMax, maxValue] → WARNING; outside → CRITICAL. */
    public ThresholdRange(double minValue, double maxValue, double warningMin, double warningMax) {
        if (minValue >= maxValue) {
            throw new IllegalArgumentException("minValue must be less than maxValue");
        }
        this.minValue   = minValue;
        this.maxValue   = maxValue;
        this.warningMin = warningMin;
        this.warningMax = warningMax;
    }

    public double getMinValue() { return minValue; }
    public void setMinValue(double minValue) { this.minValue = minValue; }

    public double getMaxValue() { return maxValue; }
    public void setMaxValue(double maxValue) { this.maxValue = maxValue; }

    public double getWarningMin() { return warningMin; }
    public void setWarningMin(double warningMin) { this.warningMin = warningMin; }

    public double getWarningMax() { return warningMax; }
    public void setWarningMax(double warningMax) { this.warningMax = warningMax; }

    public boolean isOutOfRange(double value) {
        return value < minValue || value > maxValue;
    }

    public ReadingLevel getReadingLevel(double value) {
        if (value < minValue || value > maxValue) {
            return ReadingLevel.CRITICAL;
        }
        if (value < warningMin || value > warningMax) {
            return ReadingLevel.WARNING;
        }
        return ReadingLevel.NORMAL;
    }

}