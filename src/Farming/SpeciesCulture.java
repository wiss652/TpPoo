package Farming;

public class SpeciesCulture {
    private String name;
    private CultureFamily family;

    public SpeciesCulture(String name, CultureFamily family) {
        this.name = name;
        this.family = family;
    }

    public String getName() { return name; }
    public CultureFamily getFamily() { return family; }

    @Override
    public String toString() {
        return name + " (" + family + ")";
    }
}