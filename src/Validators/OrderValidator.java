package Validators;

import domain.Order;

public class OrderValidator {
    public static void validate(Order order){
        if(order.getname()==null || order.getname().isEmpty())
            throw new IllegalArgumentException("No name added");
        if(order.getCakes().isEmpty())
            throw new IllegalArgumentException("No cakes added");
        if(order==null)
            throw new IllegalArgumentException("Order can not be null");
    }
}
