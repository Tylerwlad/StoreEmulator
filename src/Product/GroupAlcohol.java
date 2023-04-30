package Product;

public enum GroupAlcohol {
    WINE("вино"), SPIRITS("крепкий алкоголь"), BEER("пиво"), LIQUEURS("ликеры");
    private final String translation;

    GroupAlcohol(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
