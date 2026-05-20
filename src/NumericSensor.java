public abstract class NumericSensor extends Sensor {
    // Attributes
    private String unitOfMeasure;
    private MeasurementType measurementType;

    // Constructor
    public NumericSensor(String uniqueCode, ThresholdRange thresholds,Zone zone ,
                         String unitOfMeasure, MeasurementType measurementType) {
        super(uniqueCode, thresholds, zone);
        this.unitOfMeasure = unitOfMeasure;
        this.measurementType = measurementType;
    }

    // Getters and Setters
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    // Override abstract method - returns NumericReading
    @Override
    public abstract NumericReading sendReading();
}