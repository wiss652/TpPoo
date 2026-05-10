package Farming;

import java.util.ArrayList;
import java.util.List;

public class AquacultureZone extends Zone {
    private int animalCount;
    private String species;
    private FeedingProgram feedingProgram;
    private List<Object> sensors;

    public AquacultureZone(String id) {
        super(id);
        this.sensors = new ArrayList<>();
    }

    public void setSpecies(String species, int count) {
        this.species = species;
        this.animalCount = count;
    }

    public void setFeedingProgram(FeedingProgram program) {
        this.feedingProgram = program;
    }

    public void addWaterSensor(Object waterSensor) {
        sensors.add(waterSensor);
    }

    @Override
    public int getEntityCount() {
        return animalCount;
    }

    @Override
    public void addSensor(Object sensor) {
        sensors.add(sensor);
    }

    public String getSpecies() { return species; }
    public FeedingProgram getFeedingProgram() { return feedingProgram; }
}