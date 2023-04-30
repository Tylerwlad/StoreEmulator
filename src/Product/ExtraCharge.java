package Product;

public enum ExtraCharge {
    SEVEN(7), EIGHT(8), TEN(10), FIFTEEN(15);
    private final int numericalValue;

    ExtraCharge(int numericalValue) {
        this.numericalValue = numericalValue;
    }

    public int getNumericalValue() {
        return numericalValue;
    }
}
