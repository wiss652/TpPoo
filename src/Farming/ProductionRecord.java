package Farming;

import java.util.Date;

public class ProductionRecord {
    private Date date;
    private TypeProduction type;
    private double quantity;

    public ProductionRecord(Date date, TypeProduction type, double quantity) {
        this.date = date;
        this.type = type;
        this.quantity = quantity;
    }

    public Date getDate() { return date; }
    public TypeProduction getType() { return type; }
    public double getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "ProductionRecord[" + type + " | " + quantity + " | " + date + "]";
    }
}