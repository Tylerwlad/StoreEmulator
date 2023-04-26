package Product;

import java.util.HashMap;
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

    protected class Sales {
        protected HashMap<Markup, Integer> salesCounter;
        Sales() {
            salesCounter = new HashMap<>();
            salesCounter.put(Markup.SEVEN, 0);
            salesCounter.put(Markup.EIGHT, 0);
            salesCounter.put(Markup.TEN, 0);
            salesCounter.put(Markup.FIFTEEN, 0); // Уместна ли подобная инициализация?
        }
        Markup getMarkup () {
            Markup markup = Markup.FIFTEEN;
            // через свитч выбор нужного значения наценки исходя из ситуации
            return markup;
        }
    }
    public void incrementSalesCounter(Markup markup) {
        sales.salesCounter.put(markup, sales.salesCounter.get(markup) + 1);
    }
    public Integer getSalesCounter(Markup markup) {
        return sales.salesCounter.get(markup);
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
}
