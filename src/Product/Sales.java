package Product;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Sales {
    private HashMap<Markup, Integer> salesCounter = new HashMap<>();
    public Markup getMarkup (int counterOfIdenticalProductsPerClientByInt, LocalDateTime localDateTime) {
        Markup markup = Markup.TEN;
        if (counterOfIdenticalProductsPerClientByInt > 2) {
            markup = Markup.SEVEN;
        } else if ((localDateTime.getHour() >= 18) && (localDateTime.getHour() <= 20)) {
            markup = Markup.EIGHT;
        } else if ((localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY))||
        (localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
            markup = Markup.FIFTEEN;
        }
        return markup;
    }
    public void incrementSalesCounter(Markup markup) {
        salesCounter.put(markup, salesCounter.getOrDefault(markup, 0) + 1);
    }
    public HashMap<Markup, Integer> getSalesCounter() {
        return salesCounter;
    }

    public void setSalesCounter(HashMap<Markup, Integer> salesCounter) {
        this.salesCounter = salesCounter;
    }
}
