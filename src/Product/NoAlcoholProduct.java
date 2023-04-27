package Product;

public class NoAlcoholProduct extends Product {
    private GroupNoAlcohol groupNoAlcohol;
    private String structure;

    public NoAlcoholProduct(String title, double purchasePrice, int quantity, double volume,
                            GroupNoAlcohol groupNoAlcohol, String structure) {
        super(title, purchasePrice, quantity, volume);
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
        return title + ' ' + volume + 'л';
    }
}
