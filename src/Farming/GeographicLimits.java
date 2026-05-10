package Farming;

public class GeographicLimits {
    private String description;

    public GeographicLimits(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "GeographicLimits[" + description + "]";
    }
}