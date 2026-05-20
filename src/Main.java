import java.time.LocalDateTime;
import java.util.List;

/**
 * Demo principal — Etudiant 2 : Capteurs & Alertes
 *
 * Fonctionnalités démontrées :
 *   1.1  Ajouter et configurer un capteur (type, zone, seuils)
 *   1.2  Tableau de bord par zone avec indicateurs colorés
 *   1.3  Historique des relevés filtrable par date
 *   1.4  Changer le statut d'un capteur
 *   1.5  Graphiques d'évolution par capteur et par zone
 *   2.1  Déclenchement automatique des alertes
 *   2.2  Panneau des alertes actives, triées par gravité (CRITICAL d'abord)
 *   2.3  Acquitter / supprimer une alerte
 *   2.4  Historique des alertes filtrable (zone, type, niveau, période)
 */
public class Main {

    private static void header(String title) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("  " + title);
        System.out.println("═".repeat(60));
    }

    private static void section(String title) {
        System.out.println("\n── " + title + " " + "─".repeat(Math.max(0, 55 - title.length())));
    }

    public static void main(String[] args) {

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 1.1 — Ajouter et configurer des capteurs
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 1.1 — Ajout & configuration des capteurs");

        Zone zoneA = new Zone("Zone A - Bassin");
        Zone zoneB = new Zone("Zone B - Serre");

        // ThresholdRange(min, max, warningMin, warningMax)
        // Hors [min,max] → CRITICAL | Hors [wMin,wMax] mais dans [min,max] → WARNING | Sinon → NORMAL

        // pH eau  : normal 6.5–8.5, critique hors 4–10
        WaterSensor capteurEau = new WaterSensor("EAU-001",
                new ThresholdRange(4.0, 10.0, 6.5, 8.5), zoneA, "pH", MeasurementType.PH);

        // Humidité sol : normal 30–70 %, critique hors 10–90
        SoilSensor capteurSol = new SoilSensor("SOL-001",
                new ThresholdRange(10.0, 90.0, 30.0, 70.0), zoneA, "%", MeasurementType.HUMIDITY);

        // Température : normal 18–28 °C, critique hors 5–40
        EnvironmentalSensor capteurEnv = new EnvironmentalSensor("ENV-001",
                new ThresholdRange(5.0, 40.0, 18.0, 28.0), zoneB, "°C", MeasurementType.TEMPERATURE);

        // Oxygène dissous : normal 6–12 mg/L, critique hors 2–15
        BiometricSensor capteurBio = new BiometricSensor("BIO-001",
                new ThresholdRange(2.0, 15.0, 6.0, 12.0), zoneB, "mg/L", MeasurementType.DISSOLVED_OXYGEN);

        // GPS
        GPSSensor capteurGPS = new GPSSensor("GPS-001", new ThresholdRange(0.0, 1.0), zoneA);

        System.out.println("Capteurs configurés :");
        System.out.printf("  %-10s WaterSensor        pH          Zone: %s%n", capteurEau.getUniqueCode(), zoneA.getName());
        System.out.printf("  %-10s SoilSensor         Humidité    Zone: %s%n", capteurSol.getUniqueCode(), zoneA.getName());
        System.out.printf("  %-10s EnvironmentalSensor Température Zone: %s%n", capteurEnv.getUniqueCode(), zoneB.getName());
        System.out.printf("  %-10s BiometricSensor    O2 dissous  Zone: %s%n", capteurBio.getUniqueCode(), zoneB.getName());
        System.out.printf("  %-10s GPSSensor                      Zone: %s%n", capteurGPS.getUniqueCode(), zoneA.getName());

        // Ajout à la ferme
        Farm ferme = new Farm();
        ferme.addZone(zoneA);
        ferme.addZone(zoneB);
        ferme.addSensor(capteurEau);
        ferme.addSensor(capteurSol);
        ferme.addSensor(capteurEnv);
        ferme.addSensor(capteurBio);
        ferme.addSensor(capteurGPS);

        // ═══════════════════════════════════════════════════════════
        // Injection de relevés variés (NORMAL / WARNING / CRITICAL)
        // ═══════════════════════════════════════════════════════════
        header("INJECTION DES RELEVÉS SIMULÉS");

        double[] valeursPH   = { 7.2, 7.5, 3.5, 6.0, 9.2, 7.0, 8.0, 2.0, 9.8 };
        double[] valeursHum  = { 45.0, 25.0, 9.0, 55.0, 80.0, 35.0, 91.0, 60.0, 15.0 };
        double[] valeursTemp = { 22.0, 30.0, 4.0, 17.0, 41.0, 25.0, 19.0, 32.0 };
        double[] valeursO2   = { 8.5, 1.5, 5.0, 13.0, 9.0, 16.0, 7.0, 4.5 };

        for (double v : valeursPH) {
            NumericReading r = new NumericReading(capteurEau, v, capteurEau.getUnitOfMeasure());
            capteurEau.addReading(r);
            ferme.triggerAlert(r);
            System.out.printf("  EAU-001 pH=%-5.1f  → %s%n", v, r.getLevel());
        }
        for (double v : valeursHum) {
            NumericReading r = new NumericReading(capteurSol, v, capteurSol.getUnitOfMeasure());
            capteurSol.addReading(r);
            ferme.triggerAlert(r);
            System.out.printf("  SOL-001 hum=%-5.1f → %s%n", v, r.getLevel());
        }
        for (double v : valeursTemp) {
            NumericReading r = new NumericReading(capteurEnv, v, capteurEnv.getUnitOfMeasure());
            capteurEnv.addReading(r);
            ferme.triggerAlert(r);
            System.out.printf("  ENV-001 temp=%-5.1f→ %s%n", v, r.getLevel());
        }
        for (double v : valeursO2) {
            NumericReading r = new NumericReading(capteurBio, v, capteurBio.getUnitOfMeasure());
            capteurBio.addReading(r);
            ferme.triggerAlert(r);
            System.out.printf("  BIO-001 O2=%-5.1f  → %s%n", v, r.getLevel());
        }

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 1.2 — Tableau de bord par zone
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 1.2 — Tableau de bord par zone");
        ferme.showDashboardForZone(zoneA);
        ferme.showDashboardForZone(zoneB);

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 1.3 — Historique filtré par date
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 1.3 — Historique des relevés (filtre par date)");
        LocalDateTime debut = LocalDateTime.now().minusMinutes(5);
        LocalDateTime fin   = LocalDateTime.now().plusMinutes(1);

        section("Historique EAU-001 (5 dernières minutes)");
        ferme.showSensorHistory(capteurEau, debut, fin);

        section("Historique SOL-001 (5 dernières minutes)");
        ferme.showSensorHistory(capteurSol, debut, fin);

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 1.4 — Changer le statut d'un capteur
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 1.4 — Changement de statut d'un capteur");

        System.out.println("Statut initial ENV-001 : " + capteurEnv.getStatus());

        ferme.changeSensorStatus(capteurEnv, SensorStatus.FAILING);
        System.out.println("→ FAILING  | isActive = " + capteurEnv.isActive());

        ferme.changeSensorStatus(capteurEnv, SensorStatus.SUSPENDED);
        System.out.println("→ SUSPENDED| isActive = " + capteurEnv.isActive());

        ferme.changeSensorStatus(capteurEnv, SensorStatus.ACTIVE);
        System.out.println("→ ACTIVE   | isActive = " + capteurEnv.isActive());

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 1.5 — Graphiques d'évolution
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 1.5 — Graphiques d'évolution");

        section("Graphique par capteur : EAU-001");
        ferme.showGraphForSensor(capteurEau);

        section("Graphique par capteur : SOL-001");
        ferme.showGraphForSensor(capteurSol);

        section("Graphiques par zone : Zone A");
        ferme.displayZoneGraphs(zoneA, 30);

        section("Graphiques par zone : Zone B");
        ferme.displayZoneGraphs(zoneB, 30);

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 2.1 — Déclenchement automatique des alertes
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 2.1 — Déclenchement automatique des alertes");
        System.out.println("Alertes déclenchées automatiquement pour chaque relevé hors seuil.");
        System.out.println("Total d'alertes générées : " + ferme.getAllAlerts().size());

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 2.2 — Panneau alertes actives (CRITICAL d'abord)
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 2.2 — Panneau des alertes actives (triées CRITICAL → WARNING)");
        ferme.showActiveAlerts();

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 2.3 — Acquitter / supprimer une alerte
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 2.3 — Acquitter et supprimer des alertes");

        List<Alert> actives = ferme.getActiveAlerts();
        if (actives.size() >= 2) {
            int idAck = actives.get(0).getId();
            int idDel = actives.get(1).getId();

            System.out.println("Acquittement de l'alerte #" + idAck);
            ferme.acknowledgeAlert(idAck);
            ferme.getAllAlerts().stream()
                    .filter(a -> a.getId() == idAck)
                    .findFirst()
                    .ifPresent(a -> System.out.println("  → Nouveau statut : " + a.getStatus()));

            System.out.println("\nSuppression de l'alerte #" + idDel);
            ferme.deleteAlert(idDel);
            ferme.getAllAlerts().stream()
                    .filter(a -> a.getId() == idDel)
                    .findFirst()
                    .ifPresent(a -> System.out.println("  → Nouveau statut : " + a.getStatus()));

            section("Panneau mis à jour (après acquittement/suppression)");
            ferme.showActiveAlerts();
        }

        // ═══════════════════════════════════════════════════════════
        // FONCTIONNALITÉ 2.4 — Historique des alertes (filtres)
        // ═══════════════════════════════════════════════════════════
        header("FONCTIONNALITÉ 2.4 — Historique des alertes (filtrable)");

        section("Par zone : Zone A");
        ferme.showAlertsByZone(zoneA);

        section("Par type de capteur : WaterSensor");
        ferme.showAlertsBySensorType(WaterSensor.class);

        section("Par gravité : CRITICAL");
        ferme.showAlertsBySeverity(SeverityLevel.CRITICAL);

        section("Par gravité : WARNING");
        ferme.showAlertsBySeverity(SeverityLevel.WARNING);

        section("Par période (5 dernières minutes)");
        ferme.showAlertsByDateRange(
                LocalDateTime.now().minusMinutes(5),
                LocalDateTime.now().plusSeconds(5));

        section("Filtre combiné : Zone A + WaterSensor + CRITICAL");
        ferme.getAllAlerts().stream()
                .filter(a -> a.getReading().getSensor().getZone().equals(zoneA))
                .filter(a -> a.getReading().getSensor() instanceof WaterSensor)
                .filter(a -> a.getSeverity() == SeverityLevel.CRITICAL)
                .forEach(System.out::println);

        header("FIN — Toutes les fonctionnalités 1.1–1.5 et 2.1–2.4 démontrées");
    }
}