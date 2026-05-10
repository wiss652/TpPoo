package Farming;

import java.util.ArrayList;
import java.util.List;

public class CropZone extends Zone {
    private List<Crop> crops;
    private List<Object> sensors;

    public CropZone(String id) {
        super(id);
        this.crops = new ArrayList<>();
        this.sensors = new ArrayList<>();
    }

    public void addCrop(Crop crop) {
        crops.add(crop);
        System.out.println("Culture ajoutee : " + crop.getSpecies().getName()
                + " dans la zone " + getId());
    }

    public void generateCropReport() {
        System.out.println("=== Rapport cultures - Zone " + getId() + " ===");
        if (crops.isEmpty()) {
            System.out.println("Aucune culture enregistree.");
        } else {
            for (Crop c : crops) {
                c.displayGrowthStage();
            }
        }
    }

    @Override
    public int getEntityCount() {
        return crops.size();
    }

    @Override
    public void addSensor(Object sensor) {
        sensors.add(sensor);
    }

    public List<Crop> getCrops() { return crops; }
}