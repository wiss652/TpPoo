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

    public void addZone(Zone zone) { zones.add(zone); }

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

    public boolean suspendZone(String zoneId) {
        Zone z = getZoneById(zoneId);
        if (z != null) { z.suspend(); return true; }
        return false;
    }

    public boolean activateZone(String zoneId) {
        Zone z = getZoneById(zoneId);
        if (z != null) { z.activate(); return true; }
        return false;
    }

    public List<Zone> getAllZones() { return zones; }

    public Zone getZoneById(String id) {
        for (Zone z : zones) { if (z.getId().equals(id)) return z; }
        return null;
    }

    public boolean addCropToZone(String zoneId, Crop crop) {
        Zone z = getZoneById(zoneId);
        if (z instanceof CropZone) { ((CropZone) z).addCrop(crop); return true; }
        return false;
    }

    public List<Crop> getCropsByZone(String zoneId) {
        Zone z = getZoneById(zoneId);
        if (z instanceof CropZone) return ((CropZone) z).getCrops();
        return new ArrayList<>();
    }

    public boolean updateCropGrowthStage(String zoneId, String speciesName, GrowthStage stage) {
        Zone z = getZoneById(zoneId);
        if (z instanceof CropZone) {
            Crop c = ((CropZone) z).getCropBySpecies(speciesName);
            if (c != null) { c.updateGrowthStage(stage); return true; }
        }
        return false;
    }

    public boolean addProductionRecord(String zoneId, ProductionRecord record) {
        Zone z = getZoneById(zoneId);
        if (z != null) { z.addProductionRecord(record); return true; }
        return false;
    }

    public boolean addAnimalToZone(String zoneId, Animal animal) {
        Zone z = getZoneById(zoneId);
        if (z instanceof LivestockZone) { ((LivestockZone) z).addAnimal(animal); return true; }
        return false;
    }

    public List<Animal> getAnimalsByZone(String zoneId) {
        Zone z = getZoneById(zoneId);
        if (z instanceof LivestockZone) return ((LivestockZone) z).getAnimals();
        return new ArrayList<>();
    }

    public Animal getAnimalById(String animalId) {
        for (Zone z : zones) {
            if (z instanceof LivestockZone) {
                Animal a = ((LivestockZone) z).getAnimalById(animalId);
                if (a != null) return a;
            }
        }
        return null;
    }

    public boolean addHealthEvent(String animalId, HealthEvent event) {
        Animal a = getAnimalById(animalId);
        if (a != null) { a.addHealthEvent(event); return true; }
        return false;
    }

    public boolean setFeedingProgram(String zoneId, FeedingProgram program) {
        Zone z = getZoneById(zoneId);
        if (z instanceof LivestockZone) { ((LivestockZone) z).setFeedingProgram(program); return true; }
        if (z instanceof AquacultureZone) { ((AquacultureZone) z).setFeedingProgram(program); return true; }
        return false;
    }

    public FeedingProgram getFeedingProgramByZone(String zoneId) {
        Zone z = getZoneById(zoneId);
        if (z instanceof LivestockZone) return ((LivestockZone) z).getFeedingProgram();
        if (z instanceof AquacultureZone) return ((AquacultureZone) z).getFeedingProgram();
        return null;
    }

    public FeedingProgram getFeedingProgramByAnimal(String animalId) {
        for (Zone z : zones) {
            if (z instanceof LivestockZone) {
                LivestockZone lz = (LivestockZone) z;
                if (lz.getAnimalById(animalId) != null) return lz.getFeedingProgram();
            }
        }
        return null;
    }

    public String getName() { return name; }
}