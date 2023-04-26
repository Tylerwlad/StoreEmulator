package Product;

import java.util.HashMap;

public class Sales {
    private HashMap<Markup, Integer> salesCounter = new HashMap<>();
    public Markup getMarkup () {
        Markup markup = Markup.FIFTEEN;
        // через свитч выбор нужного значения наценки исходя из ситуации
        return markup;
    }
    public void incrementSalesCounter(Markup markup) {
        salesCounter.put(markup, salesCounter.getOrDefault(markup, 0) + 1);
    }
    public Integer getSalesCounter(Markup markup) {
        return salesCounter.getOrDefault(markup, 0);
    }

    public HashMap<Markup, Integer> getSalesCounter() {
        return salesCounter;
    }

    public void setSalesCounter(HashMap<Markup, Integer> salesCounter) {
        this.salesCounter = salesCounter;
    }
}
