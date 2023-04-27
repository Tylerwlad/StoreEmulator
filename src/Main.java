import TradeManager.*;

public class Main {
    public static void main(String[] args) {
        TradeManager tradeManager = new FileBackedTradeManager();
        tradeManager.trade();
    }
}