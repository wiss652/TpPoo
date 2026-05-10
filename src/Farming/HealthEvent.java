package Farming;

import java.util.Date;

public class HealthEvent {
    private Date date;
    private HealthEventType type;

    public HealthEvent(Date date, HealthEventType type) {
        this.date = date;
        this.type = type;
    }

    public Date getDate() { return date; }
    public HealthEventType getType() { return type; }

    @Override
    public String toString() {
        return "HealthEvent[" + type + " le " + date + "]";
    }
}