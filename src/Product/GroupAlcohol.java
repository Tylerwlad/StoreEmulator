package Product;

public enum GroupAlcohol {
    WINE("вино"), SPIRITS("крепкий алкоголь"), BEER("пиво"), LIQUEURS("ликеры");
    String translation;

    GroupAlcohol(String translation) {
        this.translation = translation;
    }

}
