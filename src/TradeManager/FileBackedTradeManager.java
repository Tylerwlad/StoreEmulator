package TradeManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class FileBackedTradeManager implements TradeManager {
    @Override
    public void saveManager() {

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
            for (int i = 0; i < a.length; i++) {
                System.out.println(a[i]);
            }
        }
    }

    @Override
    public void Trade() {

    }
}
