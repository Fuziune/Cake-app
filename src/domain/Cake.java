package domain;

import java.io.Serializable;
import java.util.Objects;

public class Cake implements Identifiable, Serializable {
        private long id;
        private String flavor;
        private String size;
        private double price;

        public Cake(long id, String flavor, String size, double price) {
            this.id = id;
            this.flavor = flavor;
            this.size = size;
            this.price = price;
        }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Cake otherCake = (Cake) obj;
        // Compare the relevant properties for equality
        return Objects.equals(id, otherCake.id); // Add other relevant properties as needed
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Add other relevant properties as needed
    }

    public static Cake parseFromString(String cakeString) {
        String[] cakeTokens = cakeString.split(",");

        if (cakeTokens.length != 4) {
            throw new IllegalArgumentException("Invalid cake string format: " + cakeString);
        }

        long id = Long.parseLong(cakeTokens[0].trim());
        String flavor = cakeTokens[1].trim();
        String size = cakeTokens[2].trim();
        double price = Double.parseDouble(cakeTokens[3].trim());

        return new Cake(id, flavor, size, price);
    }

    @Override
        public long getId() {
            return id;
        }

        public String getFlavor() {
            return flavor;
        }

        public String getSize() {
            return size;
        }

        public double getPrice() {
            return price;
        }
@Override
        public void setId(long id) {
            this.id = id;
        }

        public void setFlavor(String flavor) {
            this.flavor = flavor;
        }

        public void setSize(String size) {
            this.size = size;
        }



    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", flavor='" + flavor + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                '}';
    }
    @Override
    public String getname(){
            return"Cake";
    }

    public void setPrice(double price) {
            this.price=price;
    }
}
