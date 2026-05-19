package Farming;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private String id;
    private LivestockType type;
    private int age;
    private double weight;
    private EtatSante healthStatus;
    private List<HealthEvent> healthEvents;

    public Animal(String id, LivestockType type, int age, double weight) {
        this.id = id;
        this.type = type;
        this.age = age;
        this.weight = weight;
        this.healthStatus = EtatSante.HEALTHY;
        this.healthEvents = new ArrayList<>();
    }

    // Consigner un événement sanitaire
    // Si c'est une maladie : état passe à SICK
    // Si c'est un changement de poids : poids mis à jour automatiquement
    public void addHealthEvent(HealthEvent event) {
        healthEvents.add(event);
        if (event.getType() == HealthEventType.DISEASE) {
            this.healthStatus = EtatSante.SICK;
        }
        if (event.getType() == HealthEventType.WEIGHT_CHANGE) {
            this.weight = event.getNewWeight(); // mise à jour automatique du poids
        }
    }

    // Getters pour l'UI
    public String getId() { return id; }
    public LivestockType getType() { return type; }
    public int getAge() { return age; }
    public double getWeight() { return weight; }
    public EtatSante getHealthStatus() { return healthStatus; }
    public List<HealthEvent> getHealthEvents() { return healthEvents; }

    @Override
    public String toString() {
        return "Animal[" + id + " | " + type + " | "
                + age + "mois | " + weight + "kg | " + healthStatus + "]";
    }
}