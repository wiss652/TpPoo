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

    public void addHealthEvent(HealthEvent event) {
        healthEvents.add(event);
        if (event.getType() == HealthEventType.DISEASE) {
            this.healthStatus = EtatSante.SICK;
        }
        System.out.println("Evenement sanitaire enregistre pour animal "
                + id + " : " + event);
    }

    public void updateWeight(double newWeight) {
        this.weight = newWeight;
        System.out.println("Poids mis a jour pour animal " + id + " : " + newWeight + " kg");
    }

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