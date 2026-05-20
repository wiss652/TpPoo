public class SoilSensor extends NumericSensor {

    public SoilSensor(String uniqueCode, ThresholdRange thresholds,Zone zone ,String unitOfMeasure, MeasurementType measurementType) {
        super(uniqueCode, thresholds,zone, unitOfMeasure, measurementType);
    }

    @Override
    public NumericReading sendReading() {
        return new NumericReading(this, 0.0, getUnitOfMeasure());
    }
}