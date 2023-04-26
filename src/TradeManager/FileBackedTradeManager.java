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
            for (GroupNoAlcohol alcohol: GroupNoAlcohol.values()) {
                if (alcohol.getTranslation().equals(a[2].substring(1,a[2].length()-1))) {
                    System.out.println("Создан алкогольный напиток - " + a[2].substring(1,a[2].length()-1));
                }
            }
            for (GroupAlcohol noAlcohol: GroupAlcohol.values()) {
                if (noAlcohol.getTranslation().equals(a[2].substring(1,a[2].length()-1))) {
                    System.out.println("Создан безалкогольный напиток - " + a[2].substring(1,a[2].length()-1));
                }
            }
        }
    }

    @Override
    public void Trade() {

    }
}
