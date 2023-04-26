package TradeManager;

import Product.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

public class FileBackedTradeManager implements TradeManager {
    HashMap<Integer, Product> nomenclature = new HashMap<>();
    private static int countProduct;
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
    public void Trade() {

    }
}
