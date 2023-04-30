package Product;

import java.util.Objects;

public abstract class Product {
    protected String title;
    protected double purchasePrice;
    protected int quantity;
    protected double volume;
    protected int purchase;
    protected Sales sales = new Sales();

    public Sales getSales() {
        return sales;
    }

    public Product(String title, double purchasePrice, int quantity, double volume) {
        this.title = title;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.volume = volume;
    }

    public String getTitle() {
        return title;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Double.compare(product.getVolume(), getVolume()) == 0 && Objects.equals(getTitle(), product.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getVolume());
    }

    @Override
    public abstract String toString();

    public abstract String toStringSave();
}
