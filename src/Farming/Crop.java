package Farming;

import java.util.Date;

public class Crop {
    private SpeciesCulture species;
    private Date plantingDate;
    private Date expectedHarvestDate;
    private GrowthStage growthStage;
    private PedologicalRequirement requirement;

    public Crop(SpeciesCulture species, Date plantingDate,
                Date expectedHarvestDate, PedologicalRequirement requirement) {
        this.species = species;
        this.plantingDate = plantingDate;
        this.expectedHarvestDate = expectedHarvestDate;
        this.requirement = requirement;
        this.growthStage = GrowthStage.GERMINATION;
    }

    public void updateGrowthStage(GrowthStage stage) {
        this.growthStage = stage;
        System.out.println("Stade mis a jour : " + species.getName() + " -> " + stage);
    }

    public void displayGrowthStage() {
        System.out.println(species.getName()
                + " | Stade : " + growthStage
                + " | Recolte prevue : " + expectedHarvestDate);
    }

    public SpeciesCulture getSpecies() { return species; }
    public Date getPlantingDate() { return plantingDate; }
    public Date getExpectedHarvestDate() { return expectedHarvestDate; }
    public GrowthStage getGrowthStage() { return growthStage; }
    public PedologicalRequirement getRequirement() { return requirement; }

    @Override
    public String toString() {
        return "Crop[" + species + " | " + growthStage + "]";
    }
}