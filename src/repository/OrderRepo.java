package repository;

import domain.Order;

import java.util.List;

public interface OrderRepo {
    void addOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(long orderId);

    Order getOrderById(long orderId);

    List<Order> getAllOrders();
}
