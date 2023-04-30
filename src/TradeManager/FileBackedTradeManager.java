package TradeManager;

import Product.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FileBackedTradeManager implements TradeManager {
    static final HashMap<Integer, Product> nomenclature = new HashMap<>();
    static List<Receipt> receipts = new ArrayList<>();
    private static int countProduct;
    private final Month currentMonth = Month.APRIL;
    private final LocalDateTime startDataTime = LocalDateTime.of(2023, currentMonth, 1, 8, 0);

    @Override
    public void saveManager() {
        StringBuilder builder = new StringBuilder();
        for (Integer number : nomenclature.keySet()) {
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
        for (String s : fileInArrayList) {
            String[] a = s.split(", ");
            for (GroupNoAlcohol noAlcohol : GroupNoAlcohol.values()) {
                if (noAlcohol.getTranslation().equals(a[2].substring(1, a[2].length() - 1))) {
                    nomenclature.put(countProduct++, new NoAlcoholProduct(a[0].substring(1, a[0].length() - 1),
                            Double.parseDouble(a[1]), Integer.parseInt(a[5]), Double.parseDouble(a[3]),
                            noAlcohol, a[4].substring(1, a[4].length() - 1)));
                }
            }
            for (GroupAlcohol alcohol : GroupAlcohol.values()) {
                if (alcohol.getTranslation().equals(a[2].substring(1, a[2].length() - 1))) {
                    nomenclature.put(countProduct++, new AlcoholProduct(a[0].substring(1, a[0].length() - 1),
                            Double.parseDouble(a[1]), Integer.parseInt(a[5]), Double.parseDouble(a[3]),
                            alcohol, Double.parseDouble(a[4].substring(0, a[4].length() - 1))));
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
        Random random = new Random();
        while (currentDataTime.getMonth().equals(currentMonth)) {
            System.out.println("Новый день, магазин открывается");
            System.out.println(currentDataTime.getDayOfMonth() + " день, " + currentDataTime.getHour() + " часов");
            for (int i = 1; i <= theNumberOfHoursTheStoreIsOpen; i++) {
                currentDataTime = currentDataTime.plusHours(1);
                int randomClients = random.nextInt(maximumNumberOfClientsPerHour + 1);
                for (int j = 1; j <= randomClients; j++) {
                    System.out.println(j + " покупатель");
                    receipts.add(new Receipt(currentDataTime, nomenclature));
                }
                System.out.println(currentDataTime.getDayOfMonth() + " день, " + currentDataTime.getHour() + " часов" +
                        "\n");
            }
            currentDataTime = currentDataTime.plusHours(theNumberOfHoursTheStoreIsClose);
            System.out.println("Магазин закрывается");
            for (Integer integer : nomenclature.keySet()) {
                if (nomenclature.get(integer).getQuantity() < 10) {
                    nomenclature.get(integer).setQuantity(nomenclature.get(integer).getQuantity() + 150);
                    nomenclature.get(integer).setPurchase(nomenclature.get(integer).getPurchase() + 1);
                }
            }
        }
        report();
        saveManager();
    }

    private void report() {
        double totalProfit = 0;
        int expenseOnPurchases = 0;
        StringBuilder builder = new StringBuilder();
        for (Receipt receipt : receipts) {
            for (int i = 0; i < receipt.getProducts().size(); i++) {
                totalProfit += (receipt.getPrice(i) - receipt.getProducts().get(i).getPurchasePrice());
            }
        }
        for (Integer number : nomenclature.keySet()) {
            int quantityOfProductSold = 0;
            expenseOnPurchases += (nomenclature.get(number).getPurchase() * 150 *
                    nomenclature.get(number).getPurchasePrice());
            for (Receipt receipt : receipts) {
                for (Product product : receipt.getProducts()) {
                    if (product.equals(nomenclature.get(number))) {
                        quantityOfProductSold++;
                    }
                }
            }
            builder.append(nomenclature.get(number)).append(", проданно: ").append(quantityOfProductSold).
                    append(", закупленно: ").append(nomenclature.get(number).getPurchase() * 150).append("\n");
        }
        builder.append("Общая прибыль ").append(Math.floor(totalProfit * 100) / 100).append("\n")
                .append("Расходы на закуп ").append(expenseOnPurchases);
        try (Writer fileWriter = new FileWriter("src\\report.txt")) {
            fileWriter.write(builder.toString());
        } catch (Exception e) {
            System.out.println("Файл не найден");
        }
    }
}
