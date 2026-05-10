package Farming;

import java.util.ArrayList;
import java.util.List;

public class LivestockZone extends Zone {
    private List<Animal> animals;
    private FeedingProgram feedingProgram;
    private List<Object> sensors;

    public LivestockZone(String id) {
        super(id);
        this.animals = new ArrayList<>();
        this.sensors = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        System.out.println("Animal " + animal.getId()
                + " affecte a la zone " + getId());
    }

    public void setFeedingProgram(FeedingProgram program) {
        this.feedingProgram = program;
    }

    public void displayFeedingProgram() {
        if (feedingProgram == null) {
            System.out.println("Aucun programme d'alimentation pour zone " + getId());
        } else {
            System.out.print("Zone " + getId() + " - ");
            feedingProgram.display();
        }
    }

    @Override
    public int getEntityCount() {
        return animals.size();
    }

    @Override
    public void addSensor(Object sensor) {
        sensors.add(sensor);
    }

    public List<Animal> getAnimals() { return animals; }
    public FeedingProgram getFeedingProgram() { return feedingProgram; }
}