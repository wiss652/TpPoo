package Farming;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static Farm farm = new Farm("Ferme ESI");
    static LivestockZone zoneElevage;

    public static void main(String[] args) {

        // Initialisation de la ferme avec des données de test
        zoneElevage = farm.createLivestockZone("ZE-01");
        CropZone zoneCulture = farm.createCropZone("ZC-01");
        AquacultureZone zoneAqua = farm.createAquacultureZone("ZA-01");

        farm.addAnimalToZone("ZE-01", new Animal("A001", LivestockType.DAIRY, 36, 450.0));
        farm.addAnimalToZone("ZE-01", new Animal("A002", LivestockType.MEAT, 24, 80.0));
        farm.addAnimalToZone("ZE-01", new Animal("A003", LivestockType.BIRD, 6, 2.5));
        farm.setFeedingProgram("ZE-01", new FeedingProgram("Programme vaches", "Foin", 15.0));

        SpeciesCulture ble = new SpeciesCulture("Ble", CultureFamily.CEREAL);
        farm.addCropToZone("ZC-01", new Crop(ble, new Date(), new Date(),
                new PedologicalRequirement("argileux", 6.5, 60.0)));
        zoneAqua.setSpecies("Poissons", 200);
        farm.setFeedingProgram("ZA-01", new FeedingProgram("Programme poissons", "Granules", 2.5));

        // Menu principal
        int choix = 0;
        while (choix != 6) {
            System.out.println("\n========== SMART FARMING ==========");
            System.out.println("1. Gerer les zones");
            System.out.println("2. Gerer les cultures");
            System.out.println("3. Gerer les animaux");
            System.out.println("4. Programme d'alimentation");
            System.out.println("5. Vue d'ensemble");
            System.out.println("6. Quitter");
            System.out.print("Choix : ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1 -> menuZones();
                case 2 -> menuCultures();
                case 3 -> menuAnimaux();
                case 4 -> menuAlimentation();
                case 5 -> vueEnsemble();
                case 6 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================================================
    // 1. MENU ZONES
    // ======================================================
    static void menuZones() {
        System.out.println("\n--- GESTION DES ZONES ---");
        System.out.println("1. Suspendre une zone");
        System.out.println("2. Reactiver une zone");
        System.out.println("3. Enregistrer une production");
        System.out.print("Choix : ");
        int c = scanner.nextInt();

        System.out.print("ID de la zone : ");
        String id = scanner.next();

        switch (c) {
            case 1 -> {
                boolean ok = farm.suspendZone(id);
                System.out.println(ok ? "Zone " + id + " suspendue." : "Zone introuvable.");
            }
            case 2 -> {
                boolean ok = farm.activateZone(id);
                System.out.println(ok ? "Zone " + id + " reactivee." : "Zone introuvable.");
            }
            case 3 -> {
                System.out.println("Type : 1.CROP  2.DAIRY  3.EGG  4.AQUACULTURE");
                System.out.print("Choix : ");
                int t = scanner.nextInt();
                TypeProduction type = switch (t) {
                    case 1 -> TypeProduction.CROP;
                    case 2 -> TypeProduction.DAIRY;
                    case 3 -> TypeProduction.EGG;
                    default -> TypeProduction.AQUACULTURE;
                };
                System.out.print("Quantite : ");
                double q = scanner.nextDouble();
                farm.addProductionRecord(id, new ProductionRecord(new Date(), type, q));
                System.out.println("Production enregistree.");
            }
        }
    }

    // ======================================================
    // 2. MENU CULTURES
    // ======================================================
    static void menuCultures() {
        System.out.println("\n--- GESTION DES CULTURES ---");
        System.out.println("1. Afficher rapport cultures");
        System.out.println("2. Mettre a jour stade de croissance");
        System.out.print("Choix : ");
        int c = scanner.nextInt();

        switch (c) {
            case 1 -> {
                System.out.print("ID zone culture : ");
                String id = scanner.next();
                List<Crop> crops = farm.getCropsByZone(id);
                if (crops.isEmpty()) { System.out.println("Aucune culture."); return; }
                System.out.println("Rapport cultures - Zone " + id + " :");
                for (Crop crop : crops) {
                    System.out.println("  " + crop.getSpecies().getName()
                            + " | Famille: " + crop.getSpecies().getFamily()
                            + " | Stade: " + crop.getGrowthStage()
                            + " | pH optimal: " + crop.getRequirement().getOptimalPH());
                }
            }
            case 2 -> {
                System.out.print("ID zone culture : ");
                String id = scanner.next();
                System.out.print("Nom espece : ");
                String nom = scanner.next();
                System.out.println("Stade : 1.GERMINATION  2.GROWTH  3.MATURITY  4.HARVEST");
                System.out.print("Choix : ");
                int s = scanner.nextInt();
                GrowthStage stade = switch (s) {
                    case 1 -> GrowthStage.GERMINATION;
                    case 2 -> GrowthStage.GROWTH;
                    case 3 -> GrowthStage.MATURITY;
                    default -> GrowthStage.HARVEST;
                };
                boolean ok = farm.updateCropGrowthStage(id, nom, stade);
                System.out.println(ok ? "Stade mis a jour." : "Culture introuvable.");
            }
        }
    }

    // ======================================================
    // 3. MENU ANIMAUX
    // ======================================================
    static void menuAnimaux() {
        System.out.println("\n--- GESTION DES ANIMAUX ---");
        System.out.println("1. Afficher les animaux");
        System.out.println("2. Consigner un evenement sanitaire");
        System.out.print("Choix : ");
        int c = scanner.nextInt();

        switch (c) {
            case 1 -> {
                System.out.print("ID zone elevage : ");
                String id = scanner.next();
                List<Animal> animaux = farm.getAnimalsByZone(id);
                if (animaux.isEmpty()) { System.out.println("Aucun animal."); return; }
                for (Animal a : animaux) System.out.println("  " + a);
            }
            case 2 -> consignerEvenementSanitaire();
        }
    }
    static void consignerEvenementSanitaire() {
        System.out.println("\n===== EVENEMENT SANITAIRE =====");

        // Entrer l'id de l'animal
        System.out.print("ID animal : ");
        String id = scanner.next();

        Animal a = farm.getAnimalById(id);
        if (a == null) {
            System.out.println("Animal introuvable !");
            return;
        }

        System.out.println("Animal : " + a.getId() + " [" + a.getHealthStatus() + "]");

        // Choisir le type d'événement
        System.out.println("Type evenement : 1.VACCINE  2.DISEASE  3.WEIGHT_CHANGE");
        System.out.print("Choix : ");
        int choix = scanner.nextInt();

        HealthEventType type = switch (choix) {
            case 1 -> HealthEventType.VACCINE;
            case 2 -> HealthEventType.DISEASE;
            default -> HealthEventType.WEIGHT_CHANGE;
        };

        // Créer et consigner l'événement
        a.addHealthEvent(new HealthEvent(new Date(), type));

        System.out.println("Evenement '" + type + "' consigne pour " + a.getId()
                + " | Etat sante : " + a.getHealthStatus());
    }

    // ======================================================
    // CONSIGNER EVENEMENT SANITAIRE
    /* ======================================================
    static void consignerEvenementSanitaire() {
        System.out.println("\n===== EVENEMENT SANITAIRE =====");

        // Afficher tous les animaux avec leur état de santé
        List<Animal> animaux = farm.getAnimalsByZone("ZE-01");
        for (int i = 0; i < animaux.size(); i++) {
            System.out.println((i + 1) + ". " + animaux.get(i).getId()
                    + " [" + animaux.get(i).getHealthStatus() + "]");
        }

        System.out.print("Numero animal : ");
        int idx = scanner.nextInt() - 1;
        if (idx < 0 || idx >= animaux.size()) {
            System.out.println("Animal introuvable !");
            return;
        }
        Animal a = animaux.get(idx);

        System.out.println("Type evenement : 1.Vaccin  2.Maladie  3.Evolution poids");
        System.out.print("Choix : ");
        int t = scanner.nextInt();

        switch (t) {
            case 1 -> {
                a.addHealthEvent(new HealthEvent(new Date(), HealthEventType.VACCINE));
                System.out.println("Vaccin consigne pour " + a.getId());
            }
            case 2 -> {
                a.addHealthEvent(new HealthEvent(new Date(), HealthEventType.DISEASE));
                System.out.println("Maladie consignee pour " + a.getId()
                        + " | Etat : " + a.getHealthStatus());
            }
            case 3 -> {
                System.out.print("Nouveau poids (kg) : ");
                double poids = scanner.nextDouble();
                a.addHealthEvent(new HealthEvent(new Date(), poids));
                System.out.println("Evolution poids consignee pour " + a.getId()
                        + " | Nouveau poids : " + a.getWeight() + " kg");
            }
            default -> System.out.println("Choix invalide.");
        }
    } */

    // ======================================================
    // 4. MENU ALIMENTATION
    // ======================================================
    static void menuAlimentation() {
        System.out.println("\n--- PROGRAMMES D'ALIMENTATION ---");
        System.out.println("1. Afficher programme par zone");
        System.out.println("2. Afficher programme par animal");
        System.out.print("Choix : ");
        int c = scanner.nextInt();

        switch (c) {
            case 1 -> {
                System.out.print("ID zone : ");
                String id = scanner.next();
                FeedingProgram fp = farm.getFeedingProgramByZone(id);
                if (fp == null) { System.out.println("Aucun programme."); return; }
                System.out.println("Programme : " + fp.getDescription()
                        + " | Aliment : " + fp.getFoodType()
                        + " | Quantite/repas : " + fp.getQuantityPerMeal() + " kg");
            }
            case 2 -> {
                System.out.print("ID animal : ");
                String id = scanner.next();
                FeedingProgram fp = farm.getFeedingProgramByAnimal(id);
                if (fp == null) { System.out.println("Animal introuvable ou pas de programme."); return; }
                System.out.println("Programme de " + id + " : " + fp.getDescription()
                        + " | Aliment : " + fp.getFoodType()
                        + " | Quantite/repas : " + fp.getQuantityPerMeal() + " kg");
            }
        }
    }

    // ======================================================
    // 5. VUE D'ENSEMBLE
    // ======================================================
    static void vueEnsemble() {
        System.out.println("\n========== VUE D'ENSEMBLE ==========");
        for (Zone z : farm.getAllZones()) {
            System.out.println("Zone [" + z.getId() + "] | Statut: " + z.getStatus()
                    + " | Entites: " + z.getEntityCount()
                    + " | Productions: " + z.getRecords().size());
        }
        System.out.println("=====================================");
    }
}