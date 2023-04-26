package Product;

public enum GroupNoAlcohol {
    MINERAL_WATERS("минеральные воды"), JUICES("соки"), OTHER_BEVERAGES("прочие напитки");
    private String translation;
    GroupNoAlcohol(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
