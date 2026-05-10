package Farming;

public class PedologicalRequirement {
    private String soilType;
    private double optimalPH;
    private double optimalHumidity;

    public PedologicalRequirement(String soilType, double optimalPH, double optimalHumidity) {
        this.soilType = soilType;
        this.optimalPH = optimalPH;
        this.optimalHumidity = optimalHumidity;
    }

    public String getSoilType() { return soilType; }
    public double getOptimalPH() { return optimalPH; }
    public double getOptimalHumidity() { return optimalHumidity; }

    @Override
    public String toString() {
        return "PedologicalRequirement[sol=" + soilType
                + ", pH=" + optimalPH
                + ", humidite=" + optimalHumidity + "%]";
    }
}