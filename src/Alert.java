import java.time.LocalDateTime;

public class Alert {
    // Attributes
    private int id;
    private LocalDateTime creationDate;
    private SeverityLevel severity;
    private AlertStatus status;
    private Reading reading;

    // Static counter for auto-generating IDs
    private static int nextId = 1;

    // Constructor
    public Alert(SeverityLevel severity, Reading reading) {
        this.id = nextId++;
        this.creationDate = LocalDateTime.now();
        this.severity = severity;
        this.status = AlertStatus.ACTIVE;
        this.reading = reading;
    }

    // Getters
    public int getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public Reading getReading() {
        return reading;
    }

    // Setters
    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    // Business methods
    public void acknowledge() {
        if (status == AlertStatus.ACTIVE) {
            this.status = AlertStatus.ACKNOWLEDGED;
        }
    }

    public void delete() {
        this.status = AlertStatus.DELETED;
    }

    public boolean isActive() {
        return status == AlertStatus.ACTIVE;
    }
}