import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SensorDashboard {

    public void displayZoneDashboard(Zone zone, List<NumericReading> allReadings) {
        List<NumericReading> zoneReadings = allReadings.stream()
                .filter(r -> r.getSensor().getZone().equals(zone))
                .collect(Collectors.toList());

        System.out.println("\n📊 Dashboard for zone: " + zone.getName());
        Map<Sensor, List<NumericReading>> bySensor = zoneReadings.stream()
                .collect(Collectors.groupingBy(Reading::getSensor));

        for (var entry : bySensor.entrySet()) {
            NumericReading last = entry.getValue().get(entry.getValue().size()-1);
            System.out.printf("Sensor %s : %s %s [%s]%n",
                    entry.getKey().getUniqueCode(),
                    last.getValue(), last.getUnit(),
                    getColorIndicator(last));
        }
    }

    public String getColorIndicator(NumericReading r) {
        switch (r.getLevel()) {
            case NORMAL: return "\u001B[32m● NORMAL\u001B[0m";
            case WARNING: return "\u001B[33m● WARNING\u001B[0m";
            case CRITICAL: return "\u001B[31m● CRITICAL\u001B[0m";
            default: return "?";
        }
    }

    public void displayReadingHistory(Sensor sensor, int graphWidth) {
        List<NumericReading> history = sensor.getReadingHistory();
        System.out.println("\n📈 History for sensor " + sensor.getUniqueCode());
        double min = sensor.getThresholds().getMinValue();
        double max = sensor.getThresholds().getMaxValue();
        double range = max - min;
        for (NumericReading r : history) {
            double norm = range > 0 ? (r.getValue() - min) / range : 0.5;
            norm = Math.max(0.0, Math.min(1.0, norm));   // clamp to [0,1]
            int bars = (int)(norm * graphWidth);
            String color = switch (r.getLevel()) {
                case NORMAL   -> "\u001B[32m";
                case WARNING  -> "\u001B[33m";
                case CRITICAL -> "\u001B[31m";
            };
            String bar = color + "█".repeat(bars) + "\u001B[0m" + "░".repeat(graphWidth - bars);
            System.out.printf("%s %s %.2f %s%n", r.getTimestamp().toLocalTime(), bar, r.getValue(), r.getUnit());
        }
    }
}