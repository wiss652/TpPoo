import java.time.LocalDateTime;

public class AlertFilter {
    // Attributes (all optional)
    private Zone zone;              // From Student 1
    private Class<? extends Sensor> sensorType;
    private SeverityLevel severity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // Constructor
    public AlertFilter() {
        // Empty constructor - all filters optional
    }

    // Getters and Setters
    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Class<? extends Sensor> getSensorType() {
        return sensorType;
    }

    public void setSensorType(Class<? extends Sensor> sensorType) {
        this.sensorType = sensorType;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    // Business method - checks if an alert matches all filter criteria
    public boolean matches(Alert alert) {
        // Check zone (if zone filter is set)
        if (zone != null) {
            // Need to get zone from sensor through reading
            // This requires coordination with Student 1
            Sensor sensor = alert.getReading().getSensor();
            // if (sensor.getZone() != zone) return false;
        }

        // Check sensor type
        if (sensorType != null) {
            if (!sensorType.isInstance(alert.getReading().getSensor())) {
                return false;
            }
        }

        // Check severity
        if (severity != null) {
            if (alert.getSeverity() != severity) {
                return false;
            }
        }

        // Check date range
        if (startDate != null) {
            if (alert.getCreationDate().isBefore(startDate)) {
                return false;
            }
        }

        if (endDate != null) {
            if (alert.getCreationDate().isAfter(endDate)) {
                return false;
            }
        }

        return true;
    }
}