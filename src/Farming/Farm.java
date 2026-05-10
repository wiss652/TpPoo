package Farming;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private String name;
    private List<Zone> zones;

    public Farm(String name) {
        this.name = name;
        this.zones = new ArrayList<>();
    }

    public void addZone(Zone zone) {
        zones.add(zone);
        System.out.println("Zone ajoutee : " + zone.getId());
    }

    public CropZone createCropZone(String id) {
        CropZone zone = new CropZone(id);
        addZone(zone);
        return zone;
    }

    public LivestockZone createLivestockZone(String id) {
        LivestockZone zone = new LivestockZone(id);
        addZone(zone);
        return zone;
    }

    public AquacultureZone createAquacultureZone(String id) {
        AquacultureZone zone = new AquacultureZone(id);
        addZone(zone);
        return zone;
    }

    public void suspendZone(String zoneId) {
        Zone z = findZone(zoneId);
        if (z != null) z.suspend();
    }

    public void activateZone(String zoneId) {
        Zone z = findZone(zoneId);
        if (z != null) z.activate();
    }

    public void displayAllZones() {
        System.out.println("========== Ferme : " + name + " ==========");
        if (zones.isEmpty()) {
            System.out.println("Aucune zone enregistree.");
        } else {
            for (Zone z : zones) {
                z.displayOverview();
            }
        }
        System.out.println("==========================================");
    }

    private Zone findZone(String id) {
        for (Zone z : zones) {
            if (z.getId().equals(id)) return z;
        }
        System.out.println("Zone introuvable : " + id);
        return null;
    }

    public String getName() { return name; }
    public List<Zone> getZones() { return zones; }
}