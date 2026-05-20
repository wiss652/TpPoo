import java.time.LocalDateTime;

public abstract class Reading {
    // Attributes
    private LocalDateTime timestamp;
    private Sensor sensor;

    // Constructor
    public Reading(Sensor sensor) {
        this.timestamp = LocalDateTime.now();
        this.sensor = sensor;
    }

    // Getters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Sensor getSensor() {
        return sensor;
    }

    // Setters
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}