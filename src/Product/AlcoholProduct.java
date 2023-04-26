package Product;

public class AlcoholProduct extends Product {
    private GroupAlcohol groupAlcohol;
    private double alcoholStrength;

    public AlcoholProduct(String title, double purchasePrice, int quantity, double volume,
                          GroupAlcohol groupAlcohol, double alcoholStrength) {
        super(title, purchasePrice, quantity, volume);
        this.groupAlcohol = groupAlcohol;
        this.alcoholStrength = alcoholStrength;
    }

    public GroupAlcohol getGroupAlcohol() {
        return groupAlcohol;
    }

    public double getAlcoholStrength() {
        return alcoholStrength;
    }

    @Override
    public String toString() {
        return "AlcoholProduct{" +
                "groupAlcohol=" + groupAlcohol +
                ", alcoholStrength=" + alcoholStrength +
                ", title='" + title + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", quantity=" + quantity +
                ", volume=" + volume +
                '}';
    }
}
