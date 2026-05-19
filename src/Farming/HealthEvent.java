package Farming;

import java.util.Date;

public class HealthEvent {
    private Date date;
    private HealthEventType type;
    private double newWeight; // utilisé seulement si type == WEIGHT_CHANGE, sinon 0

    // Constructeur pour VACCINE et DISEASE
    public HealthEvent(Date date, HealthEventType type) {
        this.date = date;
        this.type = type;
        this.newWeight = 0;
    }

    // Constructeur pour WEIGHT_CHANGE — on passe le nouveau poids
    public HealthEvent(Date date, double newWeight) {
        this.date = date;
        this.type = HealthEventType.WEIGHT_CHANGE;
        this.newWeight = newWeight;
    }

    public Date getDate() { return date; }
    public HealthEventType getType() { return type; }
    public double getNewWeight() { return newWeight; }

    @Override
    public String toString() {
        if (type == HealthEventType.WEIGHT_CHANGE) {
            return "HealthEvent[WEIGHT_CHANGE -> " + newWeight + "kg le " + date + "]";
        }
        return "HealthEvent[" + type + " le " + date + "]";
    }
}