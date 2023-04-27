package TradeManager;

import Product.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FileBackedTradeManager implements TradeManager {
    HashMap<Integer, Product> nomenclature = new HashMap<>();
    private static int countProduct;
    private final Month currentMonth = Month.APRIL;
    private final LocalDateTime startDataTime = LocalDateTime.of(2023, currentMonth, 1, 8, 0);

    @Override
    public void saveManager() {
        StringBuilder builder = new StringBuilder();
        for (Integer number: nomenclature.keySet()) {
            builder.append(nomenclature.get(number).toStringSave()).append("\n");
        }
        try (Writer fileWriter = new FileWriter("src\\WareHouseSave.csv")) {
            fileWriter.write(builder.toString());
        } catch (Exception e) {
            System.out.println("Файл не найден");
        }
    }

    @Override
    public void loadManager() {
        Reader fileReader;
        ArrayList<String> fileInArrayList = new ArrayList<>();
        try {
            fileReader = new FileReader("src\\WareHouse.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader bf = new BufferedReader(fileReader)) {
            while (bf.ready()) {
                fileInArrayList.add(bf.readLine());
            }
        } catch (Exception e) {
            System.out.println("Файл не найден");
        }
        for (String s: fileInArrayList) {
            String[] a = s.split(", ");
            for (GroupNoAlcohol noAlcohol: GroupNoAlcohol.values()) {
                if (noAlcohol.getTranslation().equals(a[2].substring(1,a[2].length()-1))) {
                    nomenclature.put(countProduct++, new NoAlcoholProduct(a[0].substring(1,a[0].length()-1),
                            Double.parseDouble(a[1]), Integer.parseInt(a[5]), Double.parseDouble(a[3]),
                            noAlcohol, a[4].substring(1, a[4].length()-1)));
                }
            }
            for (GroupAlcohol alcohol: GroupAlcohol.values()) {
                if (alcohol.getTranslation().equals(a[2].substring(1,a[2].length()-1))) {
                    nomenclature.put(countProduct++, new AlcoholProduct(a[0].substring(1,a[0].length()-1),
                            Double.parseDouble(a[1]), Integer.parseInt(a[5]), Double.parseDouble(a[3]),
                            alcohol, Double.parseDouble(a[4].substring(0, a[4].length()-1))));
                }
            }
        }
    }

    @Override
    public void trade() {
        loadManager();
        LocalDateTime currentDataTime = startDataTime;
        int theNumberOfHoursTheStoreIsOpen = 13;
        int theNumberOfHoursTheStoreIsClose = 24 - theNumberOfHoursTheStoreIsOpen;
        int maximumNumberOfClientsPerHour = 10;
        int maximumNumberOfPurchasesPerClients = 10;
        Random random = new Random();
        while (currentDataTime.getMonth().equals(currentMonth)) {
            System.out.println("Новый день, магазин открывается");
            System.out.println(currentDataTime.getDayOfMonth() + " день, " + currentDataTime.getHour() + " часов");
            for (int i = 1; i <= theNumberOfHoursTheStoreIsOpen; i++) {
                currentDataTime = currentDataTime.plusHours(1);
                // начало торговли
                for (int j = 1; j <= random.nextInt(maximumNumberOfClientsPerHour + 1); j++) {
                    System.out.println(j + " покупатель");

                    HashMap<Integer, Integer> counterOfIdenticalProductsPerClient = new HashMap<>();

                    for (int k = 1; k <= random.nextInt(maximumNumberOfPurchasesPerClients + 1); k++) {
                        // основной цикл покупок
                        int currentProduct = random.nextInt(nomenclature.size());
                        while (nomenclature.get(currentProduct).getQuantity() <= 0) {
                            currentProduct = random.nextInt(nomenclature.size());
                        }
                        counterOfIdenticalProductsPerClient.put(currentProduct,
                                counterOfIdenticalProductsPerClient.getOrDefault(currentProduct, 0) + 1);

                        Markup markup = nomenclature.get(currentProduct).getSales().getMarkup(
                                counterOfIdenticalProductsPerClient.get(currentProduct), currentDataTime);
                        nomenclature.get(currentProduct).getSales().incrementSalesCounter(markup);

                        printPurchaseInformation(currentProduct, markup);

                        nomenclature.get(currentProduct).setQuantity(nomenclature.get(currentProduct).getQuantity() - 1);
                    }
                }
                //завершение торговли
                System.out.println(currentDataTime.getDayOfMonth() + " день, " + currentDataTime.getHour() + " часов");
            }
            currentDataTime = currentDataTime.plusHours(theNumberOfHoursTheStoreIsClose);
            System.out.println("Магазин закрывается");
            for (Integer integer: nomenclature.keySet()) {
                if (nomenclature.get(integer).getQuantity() < 10) {
                    nomenclature.get(integer).setQuantity(nomenclature.get(integer).getQuantity() + 150);
                    nomenclature.get(integer).setPurchase(nomenclature.get(integer).getPurchase() + 1);
                }
            }
        }
        report();
        saveManager();
    }
    private void printPurchaseInformation (int currentProduct, Markup markup) {
        StringBuilder builder = new StringBuilder(nomenclature.get(currentProduct).toString());
        switch (markup) {
            case SEVEN -> {
                builder.append(", наценка на товар 7%, цена продажи - ");
                builder.append(String.format("%.2f", (nomenclature.get(currentProduct).getPurchasePrice() * 1.07)));
            }
            case EIGHT -> {
                builder.append(", наценка на товар 8%, цена продажи - ");
                builder.append(String.format("%.2f", (nomenclature.get(currentProduct).getPurchasePrice() * 1.08)));
            }
            case TEN -> {
                builder.append(", наценка на товар 10%, цена продажи - ");
                builder.append(String.format("%.2f", (nomenclature.get(currentProduct).getPurchasePrice() * 1.1)));
            }
            case FIFTEEN -> {
                builder.append(", наценка на товар 15%, цена продажи - ");
                builder.append(String.format("%.2f", (nomenclature.get(currentProduct).getPurchasePrice() * 1.15)));
            }
        }
        System.out.println(builder);
    }
    private void report () {
        double totalProfit = 0;
        int expenseOnPurchases = 0;
        StringBuilder builder = new StringBuilder();
        for (Integer number: nomenclature.keySet()) {
            int quantityOfProductSold = 0;
            expenseOnPurchases += (nomenclature.get(number).getPurchase() * 150 *
                    nomenclature.get(number).getPurchasePrice());
            for (Markup markup: nomenclature.get(number).getSales().getSalesCounter().keySet()){
                quantityOfProductSold += nomenclature.get(number).getSales().getSalesCounter().get(markup);
                switch (markup) {
                    case SEVEN -> totalProfit += (nomenclature.get(number).getPurchasePrice() *
                            nomenclature.get(number).getSales().getSalesCounter().get(markup) * 0.07);
                    case EIGHT -> totalProfit += (nomenclature.get(number).getPurchasePrice() *
                            nomenclature.get(number).getSales().getSalesCounter().get(markup) * 0.08);
                    case TEN -> totalProfit += (nomenclature.get(number).getPurchasePrice() *
                            nomenclature.get(number).getSales().getSalesCounter().get(markup) * 0.1);
                    case FIFTEEN -> totalProfit += (nomenclature.get(number).getPurchasePrice() *
                            nomenclature.get(number).getSales().getSalesCounter().get(markup) * 0.15);
                }
            }
            builder.append(nomenclature.get(number)).append(", проданно: ").append(quantityOfProductSold).
                    append(", закупленно: ").append(nomenclature.get(number).getPurchase() * 150).append("\n");
        }
        builder.append("Общая прибыль ").append(totalProfit).append("\n").append("Расходы на закуп ")
                .append(expenseOnPurchases);
        try (Writer fileWriter = new FileWriter("src\\report.txt")) {
            fileWriter.write(builder.toString());
        } catch (Exception e) {
            System.out.println("Файл не найден");
        }
    }
}
