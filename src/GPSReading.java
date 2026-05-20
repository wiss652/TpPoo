public class GPSReading extends Reading {
    // Attributes
    private double latitude;
    private double longitude;

    // Constructor
    public GPSReading(Sensor sensor, double latitude, double longitude) {
        super(sensor);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // Business method
    public String getCoordinates() {
        return "(" + latitude + ", " + longitude + ")";
    }
}