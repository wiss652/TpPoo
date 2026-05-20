public class GPSSensor extends Sensor {

    public GPSSensor(String uniqueCode, ThresholdRange thresholds ,Zone zone) {
        super(uniqueCode, thresholds, zone);
    }

    @Override
    public GPSReading sendReading() {
        return new GPSReading(this, 0.0, 0.0);
    }
}