package Product;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Receipt {
    private final LocalDateTime timeOfPurchase;
    private final List<Product> products;
    private final HashMap<Integer, Product> nomenclature;

    public Receipt(LocalDateTime timeOfPurchase, HashMap<Integer, Product> nomenclature) {
        this.timeOfPurchase = timeOfPurchase;
        products = new ArrayList<>();
        this.nomenclature = nomenclature;
        creatingPurchasesInAReceipt();
    }

    public LocalDateTime getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public List<Product> getProducts() {
        return products;
    }

    private void creatingPurchasesInAReceipt() {
        int maximumNumberOfPurchasesPerClients = 10;
        Random random = new Random();
        boolean isEndProduct = false;
        int randomPurchases = random.nextInt(maximumNumberOfPurchasesPerClients + 1);
        for (int i = 1; i <= randomPurchases; i++) {
            int currentProduct = random.nextInt(nomenclature.size());
            while (nomenclature.get(currentProduct).getQuantity() <= 0) {
                int count = 0;
                for (Integer integer : nomenclature.keySet()) {
                    if (nomenclature.get(integer).getQuantity() == 0) {
                        count++;
                    }
                }
                if (count == nomenclature.size()) {
                    System.out.println("Товары закончились");
                    isEndProduct = true;
                    break;
                }
                currentProduct = random.nextInt(nomenclature.size());
            }
            if (isEndProduct) {
                break;
            }
            products.add(nomenclature.get(currentProduct));
            nomenclature.get(currentProduct).setQuantity(nomenclature.get(currentProduct).getQuantity() - 1);
        }
        products.sort(new ProductComparator());

        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%-35s наценка(%%): %-8d  стоимость: %-10.2f \n", products.get(i),
                    definitionExtraCharge(i).getNumericalValue(), getPrice(i));
        }
        System.out.println();
    }

    public double getPrice(int i) {
        double price;
        price = products.get(i).purchasePrice + (products.get(i).purchasePrice *
                definitionExtraCharge(i).getNumericalValue() / 100);
        return price;
    }

    private ExtraCharge definitionExtraCharge(int i) {
        ExtraCharge extraCharge = ExtraCharge.TEN;
        if (i >= 2) {
            if (products.get(i).equals(products.get(i - 1)) && products.get(i).equals(products.get(i - 2))) {
                extraCharge = ExtraCharge.SEVEN;
            }
        } else if ((timeOfPurchase.getHour() >= 18) && (timeOfPurchase.getHour() <= 20)) {
            extraCharge = ExtraCharge.EIGHT;
        } else if ((timeOfPurchase.getDayOfWeek().equals(DayOfWeek.SATURDAY)) ||
                (timeOfPurchase.getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
            extraCharge = ExtraCharge.FIFTEEN;
        }
        return extraCharge;
    }
}