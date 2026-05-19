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

    public void addCrop(Crop crop) { crops.add(crop); }

    public List<Crop> getCrops() { return crops; }

    public Crop getCropBySpecies(String speciesName) {
        for (Crop c : crops) {
            if (c.getSpecies().getName().equals(speciesName)) return c;
        }
        return null;
    }

    @Override public int getEntityCount() { return crops.size(); }
    @Override public void addSensor(Object sensor) { sensors.add(sensor); }
}