public class NumericReading extends Reading {
    // Attributes
    private double value;
    private String unit;

    // Constructor
    public NumericReading(Sensor sensor, double value, String unit) {
        super(sensor);
        this.value = value;
        this.unit = unit;
    }

    // Getters and Setters
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Business method
    public ReadingLevel getLevel() {
        ThresholdRange thresholds = getSensor().getThresholds();
        return thresholds.getReadingLevel(value);
    }
    @Override
    public String toString() {
        return String.format("%.2f %s (%s)", value, unit, getLevel());
    }
}