import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Farm {
    private List<Zone> zones = new ArrayList<>();
    private List<Sensor> sensors = new ArrayList<>();
    private AlertSystem alertSystem = new AlertSystem();
    private SensorDashboard dashboard = new SensorDashboard();
    private List<NumericReading> allNumericReadings = new ArrayList<>();

    public void addSensor(Sensor s) {
        sensors.add(s);
    }

    public void addZone(Zone z) {
        zones.add(z);
    }

    // Functionality 1.1: add and configure a sensor (done via constructor)

    // Simulate one reading cycle
    public void simulateReading() {
        for (Sensor s : sensors) {
            if (s.isActive()) {
                Reading r = s.sendReading();
                if (r instanceof NumericReading) {
                    NumericReading nr = (NumericReading) r;
                    allNumericReadings.add(nr);
                    s.addReading(nr);                      // Fix: persist in sensor history
                    alertSystem.triggerAlertIfNeeded(nr);  // Functionality 2.1
                }
            }
        }
    }

    // Functionality 1.3: consult sensor history filtered by date
    public void showSensorHistory(Sensor sensor, LocalDateTime from, LocalDateTime to) {
        List<NumericReading> filtered = sensor.getReadingsBetween(from, to);
        System.out.println("History for " + sensor.getUniqueCode() + " between " + from + " and " + to);
        filtered.forEach(r -> System.out.println("   " + r));
    }

    // Functionality 1.2 and 1.5: dashboard per zone and graph
    public void showDashboardForZone(Zone zone) {
        dashboard.displayZoneDashboard(zone, allNumericReadings);
    }

    public void showGraphForSensor(Sensor sensor) {
        dashboard.displayReadingHistory(sensor, 20);
    }
    // Helper method: get all sensors belonging to a specific zone
    public List<Sensor> getSensorsByZone(Zone zone) {
        List<Sensor> result = new ArrayList<>();
        for (Sensor s : sensors) {
            if (s.getZone().equals(zone)) {
                result.add(s);
            }
        }
        return result;
    }

    // Display graph for all sensors in a zone
    public void displayZoneGraphs(Zone zone, int graphWidth) {
        List<Sensor> sensorsInZone = getSensorsByZone(zone);
        if (sensorsInZone.isEmpty()) {
            System.out.println("No sensors found in zone: " + zone.getName());
            return;
        }
        for (Sensor s : sensorsInZone) {
            // Only numeric sensors have reading history
            if (s instanceof NumericSensor) {
                dashboard.displayReadingHistory(s, graphWidth);
            } else {
                System.out.println("Sensor " + s.getUniqueCode() + " (GPS) has no numeric history.");
            }
        }
    }


    // Functionality 2.2, 2.3, 2.4: alert panel
    public void showActiveAlerts() {
        System.out.println("\n--- Active Alerts (sorted by severity) ---");
        alertSystem.getAlertsSortedBySeverity().forEach(System.out::println);
    }

    public void acknowledgeAlert(int id) {
        alertSystem.acknowledgeAlert(id);
    }

    public void deleteAlert(int id) {
        alertSystem.deleteAlert(id);
    }

    // Remove showAlertHistory(AlertFilter filter)

    // Instead, use the dedicated methods:
    public void showAlertsByZone(Zone zone) {
        alertSystem.getAlertsByZone(zone).forEach(System.out::println);
    }

    public void showAlertsBySensorType(Class<? extends Sensor> type) {
        alertSystem.getAlertsBySensorType(type).forEach(System.out::println);
    }

    public void showAlertsBySeverity(SeverityLevel severity) {
        alertSystem.getAlertsBySeverity(severity).forEach(System.out::println);
    }

    public void showAlertsByDateRange(LocalDateTime start, LocalDateTime end) {
        alertSystem.getAlertsByDateRange(start, end).forEach(System.out::println);
    }

    // Functionality 1.4: change sensor status
    public void changeSensorStatus(Sensor sensor, SensorStatus newStatus) {
        sensor.setStatus(newStatus);
        System.out.println("Sensor " + sensor.getUniqueCode() + " status changed to " + newStatus);
    }


    /** Expose alert triggering for manually-injected readings (used in Main demo). */
    public void triggerAlert(NumericReading r) {
        allNumericReadings.add(r);
        alertSystem.triggerAlertIfNeeded(r);
    }

    /** Returns currently active alerts (not acknowledged / deleted). */
    public List<Alert> getActiveAlerts() {
        return alertSystem.getActiveAlerts();
    }

    /** Returns all alerts ever created (all statuses). */
    public List<Alert> getAllAlerts() {
        return alertSystem.getAllAlerts();
    }
}