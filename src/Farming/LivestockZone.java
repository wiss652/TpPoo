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

    public void addAnimal(Animal animal) { animals.add(animal); }

    public Animal getAnimalById(String id) {
        for (Animal a : animals) { if (a.getId().equals(id)) return a; }
        return null;
    }

    // Définir le programme d'alimentation
    public void setFeedingProgram(FeedingProgram program) {
        this.feedingProgram = program;
    }

    // Afficher le programme d'alimentation de la zone (retourne l'objet pour l'UI)
    public FeedingProgram getFeedingProgram() { return feedingProgram; }

    // Afficher le programme d'alimentation d'un animal spécifique par son id
    public FeedingProgram getFeedingProgramByAnimal(String animalId) {
        Animal a = getAnimalById(animalId);
        if (a != null) return feedingProgram; // tous les animaux de la zone ont le même programme
        return null;
    }

    @Override public int getEntityCount() { return animals.size(); }
    @Override public void addSensor(Object sensor) { sensors.add(sensor); }

    public List<Animal> getAnimals() { return animals; }
}