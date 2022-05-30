package oop.PastaModified;

public enum PastaType {
    LINGUINE(10), SPAGHETTI(12), FETTUCCINE(14);

    private int price;

    PastaType(int price) {
        this.price = price;
    }

    public int price() {
        return price;
    }
}
