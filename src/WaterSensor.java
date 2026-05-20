public class WaterSensor extends NumericSensor {

    public WaterSensor(String uniqueCode, ThresholdRange thresholds, Zone zone,
                       String unitOfMeasure, MeasurementType measurementType) {
        super(uniqueCode, thresholds,zone, unitOfMeasure, measurementType);
    }

    @Override
    public NumericReading sendReading() {
        // In real implementation: read from actual water sensor hardware
        // For now: return 0.0 as placeholder
        return new NumericReading(this, 0.0, getUnitOfMeasure());
    }
}