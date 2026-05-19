package Farming;

import java.util.ArrayList;
import java.util.List;

public abstract class Zone {
    private String id;
    private ZoneStatus status;
    private List<ProductionRecord> records;

    public Zone(String id) {
        this.id = id;
        this.status = ZoneStatus.ACTIVE;
        this.records = new ArrayList<>();
    }

    public void suspend() {
        this.status = ZoneStatus.INACTIVE;
        System.out.println("Zone " + id + " suspendue.");
    }

    public void activate() {
        this.status = ZoneStatus.ACTIVE;
        System.out.println("Zone " + id + " reactivee.");
    }

    public void addProductionRecord(ProductionRecord record) {
        records.add(record);
        System.out.println("Production enregistree : " + record);
    }

    public void displayOverview() {
        System.out.println("Zone [" + id + "] - Statut: " + status
                + " - Entites: " + getEntityCount());
    }

    // Chaque sous-classe dit combien d'entites elle a
    public abstract int getEntityCount();

    // Pour l'integration avec la binome (ses capteurs)
    public abstract void addSensor(Object sensor);

    public String getId() { return id; }
    public ZoneStatus getStatus() { return status; }
    public List<ProductionRecord> getRecords() { return records; }
}