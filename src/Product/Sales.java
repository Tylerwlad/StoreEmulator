package Product;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Sales {
    private HashMap<ExtraCharge, Integer> salesCounter = new HashMap<>();

    public ExtraCharge getMarkup(int counterOfIdenticalProductsPerClientByInt, LocalDateTime localDateTime) {
        ExtraCharge extraCharge = ExtraCharge.TEN;
        if (counterOfIdenticalProductsPerClientByInt > 2) {
            extraCharge = ExtraCharge.SEVEN;
        } else if ((localDateTime.getHour() >= 18) && (localDateTime.getHour() <= 20)) {
            extraCharge = ExtraCharge.EIGHT;
        } else if ((localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)) ||
                (localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
            extraCharge = ExtraCharge.FIFTEEN;
        }
        return extraCharge;
    } // TODO: переделать метод и сохранение покупок

    public void incrementSalesCounter(ExtraCharge extraCharge) {
        salesCounter.put(extraCharge, salesCounter.getOrDefault(extraCharge, 0) + 1);
    }

    public HashMap<ExtraCharge, Integer> getSalesCounter() {
        return salesCounter;
    }

    public void setSalesCounter(HashMap<ExtraCharge, Integer> salesCounter) {
        this.salesCounter = salesCounter;
    }
}
