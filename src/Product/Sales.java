package Product;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Sales {
    private HashMap<ExtraСharge, Integer> salesCounter = new HashMap<>();

    public ExtraСharge getMarkup(int counterOfIdenticalProductsPerClientByInt, LocalDateTime localDateTime) {
        ExtraСharge extraСharge = ExtraСharge.TEN;
        if (counterOfIdenticalProductsPerClientByInt > 2) {
            extraСharge = ExtraСharge.SEVEN;
        } else if ((localDateTime.getHour() >= 18) && (localDateTime.getHour() <= 20)) {
            extraСharge = ExtraСharge.EIGHT;
        } else if ((localDateTime.getDayOfWeek().equals(DayOfWeek.SATURDAY)) ||
                (localDateTime.getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
            extraСharge = ExtraСharge.FIFTEEN;
        }
        return extraСharge;
    } // TODO: переделать метод и сохранение покупок

    public void incrementSalesCounter(ExtraСharge extraСharge) {
        salesCounter.put(extraСharge, salesCounter.getOrDefault(extraСharge, 0) + 1);
    }

    public HashMap<ExtraСharge, Integer> getSalesCounter() {
        return salesCounter;
    }

    public void setSalesCounter(HashMap<ExtraСharge, Integer> salesCounter) {
        this.salesCounter = salesCounter;
    }
}
