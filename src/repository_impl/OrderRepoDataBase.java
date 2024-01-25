package repository_impl;

import DataBase.DatabaseConnector;
import domain.Cake;
import domain.Order;
import domain.OrderStatus;
import repository.GenericRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderRepoDataBase implements GenericRepository<Order> {
    public List<Order> repo = new ArrayList<>();

    public List<Order> getAll() {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long orderId = resultSet.getLong("Oid");
                String customerName = resultSet.getString("customerName");
                LocalDate deliveryDate = resultSet.getDate("deliveryDate").toLocalDate();
                OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));

                // Assuming you have a method to retrieve cakes associated with an order
                List<Cake> cakes = getCakesForOrder(connection,orderId);

                Order order = new Order(orderId, customerName, deliveryDate, cakes);
                order.setStatus(status);
                //System.out.println(repo);
                int a=0;
                if(repo.isEmpty())
                    repo.add(order);
                for(Order order1:repo){
                    if (order1.getId() == order.getId() && Objects.equals(order1.getname(),order.getname()))
                    {if(Objects.equals(order1.getDeliveryDate(),order.getDeliveryDate()))
                    { a = 1;
                        break;}
                    }
                }
                if(a==0)
                    repo.add(order);
                //System.out.println(repo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repo;
    }
    public boolean existOrder(Order order){
        int a=0;
        List<Order> list1=getAll();
        for(Order order1:list1){
            if(order1.getId()==order.getId())
                a=1;
        }
        if(a==1)
            return true;
        return false;
    }
    public void save(Order order) {
        if(existOrder(order))
        {try {
            update(order);
        }catch (Exception e) {
            throw new RuntimeException(e);}}
        else{
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO orders(Oid, customerName, deliveryDate, status) VALUES (?, ?, ?, ?)"
             )) {
            long ir = 1;
            for (Order orders : repo) {
                if (orders.getId() == ir) {
                    ir++;
                }
            }
            statement.setLong(1, ir);
            statement.setString(2, order.getCustomerName());
            statement.setDate(3, java.sql.Date.valueOf(order.getDeliveryDate()));
            statement.setString(4, order.getStatus().toString());
            statement.executeUpdate();
            order.setId(getMaxId(connection));
            // Assuming you have a method to save cakes associated with an order
            repo.add(order);
           // repo.remove(0);
            saveCakesForOrder(getMaxId(connection), order.getCakes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}
public long getMaxId(Connection connection){
        long maxId=-1;

        try(
            PreparedStatement statement=connection.prepareStatement("SELECT MAX(OID) AS maxId FROM orders");
            ResultSet resultSet=statement.executeQuery()){
            if(resultSet.next())
            {maxId=resultSet.getLong("maxId");}

            }catch (SQLException e) {
            e.printStackTrace();

}
    return maxId;}

    public void update(Order order) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE orders SET customerName = ?, deliveryDate = ?, status = ? WHERE Oid = ?"
             )) {
            statement.setString(1, order.getCustomerName());
            statement.setDate(2, java.sql.Date.valueOf(order.getDeliveryDate()));
            statement.setString(3, order.getStatus().toString());
            statement.setLong(4, order.getId());

            statement.executeUpdate();

            // Assuming you have a method to update cakes associated with an order
            updateCakesForOrder(order.getId(), order.getCakes());
            repo.remove(Integer.parseInt(String.valueOf(order.getId()-1)));
            repo.add(Integer.parseInt(String.valueOf(order.getId()-1)),order);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(long orderId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE Oid = ?")) {
            statement.setLong(1, orderId);
            statement.executeUpdate();

            // Assuming you have a method to delete cakes associated with an order
            deleteCakesForOrder(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(repo);
        int i=Integer.parseInt(String.valueOf(orderId));
        repo.removeIf(orders -> orders.getId() == orderId);
        //System.out.println(repo);
    }
    public Order findById(long orderId) {
        Order order = null;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE Oid = ?")) {
            statement.setLong(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String customerName = resultSet.getString("customerName");
                    LocalDate deliveryDate = resultSet.getDate("deliveryDate").toLocalDate();
                    OrderStatus status = OrderStatus.valueOf(resultSet.getString("status"));

                    List<Cake> cakes = getCakesForOrder(connection,orderId);

                    order = new Order(orderId, customerName, deliveryDate, cakes);
                    order.setStatus(status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }


    private List<Cake> getCakesForOrder(Connection connection, long orderId) {
        List<Cake> cakes = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT c.* FROM cakes c " +
                        "JOIN order_cakes oc ON c.Cid = oc.cake_id " +
                        "WHERE oc.order_id = ?"
        )) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Cake cake = new Cake(
                            resultSet.getLong("Cid"),
                            resultSet.getString("flavor"),
                            resultSet.getString("size"),
                            resultSet.getDouble("price")
                    );
                    cakes.add(cake);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cakes;
    }

    private void saveCakesForOrder(long orderId, List<Cake> cakes) {
        try (Connection connectionn = DatabaseConnector.getConnection()) {
            for (Cake cake : cakes) {
                try (PreparedStatement statement = connectionn.prepareStatement(
                        "INSERT INTO order_cakes(order_id, cake_id) VALUES (?, ?)"
                )) {
                    statement.setLong(1, orderId);
                    statement.setLong(2, cake.getId());
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCakesForOrder(long orderId, List<Cake> newCakes) {
        // Delete existing associations
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(
                     "DELETE FROM order_cakes WHERE order_id = ?"
             )) {
            deleteStatement.setLong(1, orderId);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Save new associations
        saveCakesForOrder(orderId, newCakes);
    }

    private void deleteCakesForOrder(long orderId) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM order_cakes WHERE order_id = ?"
             )) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

