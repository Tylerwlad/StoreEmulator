package Product;

public class AlcoholProduct extends Product {
    private GroupNoAlcohol groupNoAlcohol;
    private double alcoholStrength;

    public AlcoholProduct(String title, double purchasePrice, int quantity, double volume,
                          GroupNoAlcohol groupNoAlcohol, double alcoholStrength) {
        super(title, purchasePrice, quantity, volume);
        this.groupNoAlcohol = groupNoAlcohol;
        this.alcoholStrength = alcoholStrength;
    }

    public GroupNoAlcohol getGroupNoAlcohol() {
        return groupNoAlcohol;
    }

    public double getAlcoholStrength() {
        return alcoholStrength;
    }

    @Override
    public String toString() {
        return "AlcoholProduct{" +
                "groupNoAlcohol=" + groupNoAlcohol +
                ", alcoholStrength=" + alcoholStrength +
                ", title='" + title + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", quantity=" + quantity +
                ", volume=" + volume +
                '}';
    }
}
