import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class AlertSystem {
    // Attributes
    private List<Alert> alerts;

    // Constructor
    public AlertSystem() {
        this.alerts = new ArrayList<>();
    }

    // Business methods
    public void triggerAlertIfNeeded(Reading reading) {
        // Only numeric readings can trigger alerts
        if (reading instanceof NumericReading) {
            NumericReading numericReading = (NumericReading) reading;
            ReadingLevel level = numericReading.getLevel();

            if (level == ReadingLevel.CRITICAL) {
                Alert alert = new Alert(SeverityLevel.CRITICAL, reading);
                alerts.add(alert);
            }
            // Note: WARNING level could be added here if needed
        }
    }

    public List<Alert> getActiveAlerts() {
        return alerts.stream()
                .filter(Alert::isActive)
                .collect(Collectors.toList());
    }

    public List<Alert> getAlertsSortedBySeverity() {
        return alerts.stream()
                .sorted(Comparator.comparing(Alert::getSeverity))
                .collect(Collectors.toList());
    }

    public boolean acknowledgeAlert(int id) {
        for (Alert alert : alerts) {
            if (alert.getId() == id && alert.isActive()) {
                alert.acknowledge();
                return true;
            }
        }
        return false;
    }

    public boolean deleteAlert(int id) {
        for (Alert alert : alerts) {
            if (alert.getId() == id) {
                alert.delete();
                return true;
            }
        }
        return false;
    }
    public List<Alert> viewHistory(Zone zone, Class<? extends Sensor> sensorType,
                                   SeverityLevel severity, LocalDateTime start, LocalDateTime end) {
        return alerts.stream()
                .filter(alert -> (zone == null || alert.getReading().getSensor().getZone().equals(zone)))
                .filter(alert -> (sensorType == null || sensorType.isInstance(alert.getReading().getSensor())))
                .filter(alert -> (severity == null || alert.getSeverity() == severity))
                .filter(alert -> (start == null || !alert.getCreationDate().isBefore(start)))
                .filter(alert -> (end == null || !alert.getCreationDate().isAfter(end)))
                .collect(Collectors.toList());
    }

    public List<Alert> getAllAlerts() {
        return new ArrayList<>(alerts);
    }
    // ========== 4 FILTER METHODS ==========

    // 1. Filter by zone
    public List<Alert> getAlertsByZone(Zone zone) {
        return alerts.stream()
                .filter(alert -> alert.getReading().getSensor().getZone().equals(zone))
                .collect(Collectors.toList());
    }

    // 2. Filter by sensor type (e.g., WaterSensor.class, GPSSensor.class)
    public List<Alert> getAlertsBySensorType(Class<? extends Sensor> sensorType) {
        return alerts.stream()
                .filter(alert -> sensorType.isInstance(alert.getReading().getSensor()))
                .collect(Collectors.toList());
    }

    // 3. Filter by severity level (WARNING or CRITICAL)
    public List<Alert> getAlertsBySeverity(SeverityLevel severity) {
        return alerts.stream()
                .filter(alert -> alert.getSeverity() == severity)
                .collect(Collectors.toList());
    }

    // 4. Filter by date period (between start and end, inclusive)
    public List<Alert> getAlertsByDateRange(LocalDateTime start, LocalDateTime end) {
        return alerts.stream()
                .filter(alert -> !alert.getCreationDate().isBefore(start)
                        && !alert.getCreationDate().isAfter(end))
                .collect(Collectors.toList());
    }

    // Optional: combine multiple filters (teacher may allow this separately)
    public List<Alert> getAlertsByFilter(Zone zone, Class<? extends Sensor> sensorType,
                                         SeverityLevel severity, LocalDateTime start, LocalDateTime end) {
        return alerts.stream()
                .filter(alert -> (zone == null || alert.getReading().getSensor().getZone().equals(zone)))
                 .filter(alert -> (sensorType == null || sensorType.isInstance(alert.getReading().getSensor())))
                .filter(alert -> (severity == null || alert.getSeverity() == severity))
                .filter(alert -> (start == null || !alert.getCreationDate().isBefore(start)))
                .filter(alert -> (end == null || !alert.getCreationDate().isAfter(end)))
                .collect(Collectors.toList());
    }
}
