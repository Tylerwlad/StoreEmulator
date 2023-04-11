package Product;
import Product.GroupNoAlcohol;

public class NoAlcoholProduct extends Product {
    private GroupNoAlcohol groupNoAlcohol;
    private String structure;

    public NoAlcoholProduct(GroupNoAlcohol groupNoAlcohol, String structure, String title, double purchasePrice,
    int quantity, double volume) {
        setTitle(title);
        setPurchasePrice(purchasePrice);
        setQuantity(quantity);
        setVolume(volume);
        this.groupNoAlcohol = groupNoAlcohol;
        this.structure = structure;
    }
    public GroupNoAlcohol getGroupNoAlcohol() {
        return groupNoAlcohol;
    }

    public String getStructure() {
        return structure;
    }

    @Override
    public String toString() {
        return "NoAlcoholProduct{" +
                "groupNoAlcohol=" + groupNoAlcohol +
                ", structure='" + structure + '\'' +
                '}';
    }
}
