package com.elinvar.exercise;

public class PriceUpdate {

    private final String companyName;
    private final double price;

    public PriceUpdate(String companyName, double price) {
        this.companyName = companyName;
        this.price = price;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return companyName + " - " + price;
    }

    /**
     * Validates the equality of this object by using companyName attribute
     * @param o The object to be compared
     * @return true if the passed object if equal to this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceUpdate that = (PriceUpdate) o;

        return !(companyName != null ? !companyName.equals(that.companyName) : that.companyName != null);

    }

    /**
     * Generates a hashCode by using companyName attribute
     * @return a evenly distributed hashCode
     */
    @Override
    public int hashCode() {
        return companyName != null ? companyName.hashCode() : 0;
    }
}
