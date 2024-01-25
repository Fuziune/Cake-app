package domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Order implements Identifiable, Serializable {
    private long id;
    private String customerName;
    private LocalDate deliveryDate;
    private List<Cake> cakes;
    private OrderStatus status;

    public Order(long id, String customerName, LocalDate deliveryDate, List<Cake> cakes) {
        this.id = id;
        this.customerName = customerName;
        this.deliveryDate = deliveryDate;
        this.cakes = cakes;
        this.status = OrderStatus.PENDING;
    }
    public boolean compareCakes(Order otherOrder) {
        if (otherOrder == null) {
            return false;
        }

        List<Cake> otherCakes = otherOrder.getCakes();

        // Check if the number of cakes is the same
        if (cakes.size() != otherCakes.size()) {
            return false;
        }

        // Check each cake and its order
        for (int i = 0; i < cakes.size(); i++) {
            Cake thisCake = cakes.get(i);
            Cake otherCake = otherCakes.get(i);

            // Compare cake details (you may want to adjust this based on your Cake class)
            if (!Objects.equals(thisCake.getname(), otherCake.getname())
                    || thisCake.getFlavor() != otherCake.getFlavor()
                    || thisCake.getSize() != otherCake.getSize()) {
                return false;
            }
        }

        return true;
    }
    @Override
    public long getId() {
        return id;
    }
@Override
    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<Cake> getCakes() {
        return cakes;
    }

    public void setCakes(List<Cake> cakes) {
        this.cakes = cakes;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", cakes=" + cakes +
                ", status=" + status +
                '}';
    }
    @Override
    public String getname(){
        return"Order";
    }
}

