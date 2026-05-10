package Farming;

public class FeedingProgram {
    private String description;
    private String foodType;
    private double quantityPerMeal;

    public FeedingProgram(String description, String foodType, double quantityPerMeal) {
        this.description = description;
        this.foodType = foodType;
        this.quantityPerMeal = quantityPerMeal;
    }

    public void display() {
        System.out.println("Programme alimentation : " + description
                + " | Aliment : " + foodType
                + " | Quantite/repas : " + quantityPerMeal + " kg");
    }

    public String getDescription() { return description; }
    public String getFoodType() { return foodType; }
    public double getQuantityPerMeal() { return quantityPerMeal; }
}