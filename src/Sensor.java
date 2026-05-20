import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Sensor implements Suspendable {
    // Attributes
    private String uniqueCode;
    private SensorStatus status;
    private ThresholdRange thresholds;
    private Zone zone;  // ← ADDED - from Student 1
    private List<NumericReading> readingHistory = new ArrayList<>();

    // Constructor
    public Sensor(String uniqueCode, ThresholdRange thresholds, Zone zone) {
        this.uniqueCode = uniqueCode;
        this.status = SensorStatus.ACTIVE;
        this.thresholds = thresholds;
        this.zone = zone;  // ← ADDED
    }

    // Getters and Setters
    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public SensorStatus getStatus() {
        return status;
    }

    public void setStatus(SensorStatus status) {
        this.status = status;
    }

    public ThresholdRange getThresholds() {
        return thresholds;
    }

    public void setThresholds(ThresholdRange thresholds) {
        this.thresholds = thresholds;
    }

    public Zone getZone() {  // ← ADDED
        return zone;
    }

    public void setZone(Zone zone) {  // ← ADDED
        this.zone = zone;
    }
    public List<NumericReading> getReadingHistory() { return new ArrayList<>(readingHistory); }

    // Business Methods
    public boolean isActive() {
        return status == SensorStatus.ACTIVE;
    }

    public void suspend() {
        this.status = SensorStatus.SUSPENDED;
    }

    public void reactivate() {
        this.status = SensorStatus.ACTIVE;
    }

    public void markAsFailing() {
        this.status = SensorStatus.FAILING;
    }

    // Add reading to history
    public void addReading(NumericReading reading) {
        readingHistory.add(reading);
    }

    // Consult reading history filtered by date range (functionality 1.3)
    public List<NumericReading> getReadingsBetween(LocalDateTime start, LocalDateTime end) {
        return readingHistory.stream()
                .filter(r -> !r.getTimestamp().isBefore(start) && !r.getTimestamp().isAfter(end))
                .collect(Collectors.toList());
    }
    // Abstract Method
    public abstract Reading sendReading();

}