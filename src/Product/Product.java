package Product;

import java.util.Objects;

abstract class Product {
    private String title;
    private double purchasePrice;
    private int quantity;
    private double volume;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
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
}
