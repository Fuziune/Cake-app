package repository_impl;

import DataBase.DatabaseConnector;
import domain.Cake;
import repository.GenericRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CakeRepoDataBase implements GenericRepository<Cake> {
    //public MemoryRepository<Cake> repo=new MemoryRepository<Cake>();

    public List<Cake> repo=new ArrayList<>();

    @Override
    public String toString() {
        return "CakeRepoDataBase{" +
                "repo=" + repo +
                '}';
    }

    public List<Cake> getAll() {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM cakes");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Cake cake = new Cake(
                        resultSet.getLong("Cid"),
                        resultSet.getString("flavor"),
                        resultSet.getString("size"),
                        resultSet.getDouble("price")
                );
                int a=0;
                if(repo.isEmpty())
                    repo.add(cake);
                for(Cake cakes:repo){
                    if(cakes.getId()==cake.getId() && Objects.equals(cake.getSize(), cakes.getSize()))
                        if(Objects.equals(cake.getFlavor(),cakes.getFlavor()) && Objects.equals(cakes.getPrice(),cakes.getPrice()))
                            a=1;
                }
                if(a==0){
                    repo.add(cake);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repo;
    }
public boolean existCake(Cake cake){
    int a=0;
    List<Cake> list1=getAll();
    for(Cake cake1:list1){
        if(cake1.getId()==cake.getId())
            a=1;
    }
    if(a==1)
        return true;
    return false;
}
    //        catch (ClassNotFoundException e) {
    //            e.printStackTrace();}}
    public void save(Cake cake){
        if(existCake(cake))
        {try {
                update(cake);
            }catch (Exception e) {
                throw new RuntimeException(e);}}
        else{
        try(Connection connection=DatabaseConnector.getConnection();
        PreparedStatement statement=connection.prepareStatement("INSERT INTO cakes(Cid,flavor,size,price) values (?,?,?,?)")){
            long ir = 1;
            for (Cake cakes : repo) {
                if (cakes.getId() == ir) {
                    ir++;
                }
            }
            statement.setLong(1, ir);
            statement.setString(2, cake.getFlavor());
            statement.setString(3, cake.getSize());
            statement.setDouble(4, cake.getPrice());
            statement.executeUpdate();//}

            repo.sort(Comparator.comparingLong(Cake::getId));
        }catch (SQLException e) {
            e.printStackTrace();
    } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }}
    public long getMaxId(Connection connection) {
        long maxId = -1; // Initialize with a default value

        try (//Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(Cid) AS maxId FROM cakes");
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                maxId = resultSet.getLong("maxId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., log it or throw a custom exception
        }

        return maxId;
    }
    public Cake findById(long id) {
        Cake cake = null;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM cakes WHERE Cid = ?");
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    cake = new Cake(
                            resultSet.getLong("Cid"),
                            resultSet.getString("flavor"),
                            resultSet.getString("size"),
                            resultSet.getDouble("price")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cake;
    }
    public void delete(long id)  {
        if(findById(id)==null)
            //throw new Exception("Cake doesn t exist");
            System.out.println("Cake");
        else{
            try(Connection connection=DatabaseConnector.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Delete from cakes where cid=?")){
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //System.out.println(repo);
            int i=Integer.parseInt(String.valueOf(id));
            repo.removeIf(cakes -> cakes.getId() == id);
            //System.out.println(repo);

        }
        }

    public void update(Cake cake) throws Exception {
        if (findById(cake.getId()) != null) {
            try (Connection connection = DatabaseConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE cakes SET flavor = ?, size = ?, price = ? WHERE Cid = ?"
                 )) {
                statement.setString(1, cake.getFlavor());
                statement.setString(2, cake.getSize());
                statement.setDouble(3, cake.getPrice());
                statement.setLong(4, cake.getId());

                statement.executeUpdate();
                repo.remove(Integer.parseInt(String.valueOf(cake.getId()-1)));
                repo.add(Integer.parseInt(String.valueOf(cake.getId()-1)),cake);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("Cake with this id doesn't exist");
        }
    }
}

