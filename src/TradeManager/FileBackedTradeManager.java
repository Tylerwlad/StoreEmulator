package TradeManager;

import Product.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
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
        // записать продукты в csv
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
                    for (int k = 1; k <= random.nextInt(maximumNumberOfPurchasesPerClients + 1); k++) {
                        // основной цикл покупок
                        int currentProduct = random.nextInt(nomenclature.size());

                        HashMap<Integer, Integer> counterOfIdenticalProductsPerClient = new HashMap<>();
                        counterOfIdenticalProductsPerClient.put(currentProduct,
                                counterOfIdenticalProductsPerClient.getOrDefault(currentProduct, 0) + 1);

                        System.out.println(nomenclature.get(currentProduct).getTitle() + " " +
                                nomenclature.get(currentProduct).getVolume() + "л.");

                    }
                }
                //завершение торговли
                System.out.println(currentDataTime.getDayOfMonth() + " день, " + currentDataTime.getHour() + " часов");
            }
            currentDataTime = currentDataTime.plusHours(theNumberOfHoursTheStoreIsClose);
            System.out.println("Магазин закрывается");
        }
    }
}
