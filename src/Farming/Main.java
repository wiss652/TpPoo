package Farming;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        // === Creation de la ferme ===
        Farm farm = new Farm("Ferme ESI");

        // === Zone Culture ===
        CropZone cropZone = farm.createCropZone("ZC-01");

        SpeciesCulture ble = new SpeciesCulture("Ble", CultureFamily.CEREAL);
        PedologicalRequirement req = new PedologicalRequirement("argileux", 6.5, 60.0);
        Crop culture = new Crop(ble, new Date(), new Date(), req);
        culture.updateGrowthStage(GrowthStage.GROWTH);
        cropZone.addCrop(culture);
        cropZone.generateCropReport();

        // === Zone Elevage ===
        LivestockZone livestockZone = farm.createLivestockZone("ZE-01");

        Animal vache = new Animal("A001", LivestockType.DAIRY, 36, 450.0);
        vache.addHealthEvent(new HealthEvent(new Date(), HealthEventType.VACCINE));
        livestockZone.addAnimal(vache);

        FeedingProgram program = new FeedingProgram("Programme vaches", "Foin", 15.0);
        livestockZone.setFeedingProgram(program);
        livestockZone.displayFeedingProgram();

        // === Zone Aquacole ===
        AquacultureZone aquaZone = farm.createAquacultureZone("ZA-01");
        aquaZone.setSpecies("Poissons", 200);

        // === Vue d'ensemble ===
        farm.displayAllZones();

        // === Enregistrer une production ===
        cropZone.addProductionRecord(
                new ProductionRecord(new Date(), TypeProduction.CROP, 1200.5)
        );

        // === Test suspension ===
        farm.suspendZone("ZC-01");
        farm.displayAllZones();
        farm.activateZone("ZC-01");
    }
}