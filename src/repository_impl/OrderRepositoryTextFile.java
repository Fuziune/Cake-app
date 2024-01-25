package repository_impl;

import domain.Cake;
import domain.Order;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class OrderRepositoryTextFile extends FileRepository<Order>{
    public OrderRepositoryTextFile(String file) {
        super(file);
    }

    @Override
    protected void readfromfile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            String line ;
            while ((line = reader.readLine()) != null) {
                String[] token=line.split("[,]");
                if(token.length!=5)
                    continue;
                else {Long id = Long.parseLong(token[0]);
                String customerName = token[1].trim();
                LocalDate deliveryDate = LocalDate.parse(token[2]);

                // Split the cake tokens and create a List<Cake>
                String[] cakeTokens = token[3].split("\\|");
                List<Cake> cakes = new ArrayList<>();

                for (String cakeToken : cakeTokens) {
                    // Assuming Cake has a constructor that can parse the cake information from a String
                    Cake cake = Cake.parseFromString(cakeToken.trim());
                    cakes.add(cake);
                }

                Order order = new Order(id, customerName, deliveryDate, cakes);
            }
        }
    }catch (FileNotFoundException f){
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writefromfile() {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(this.file))){
            Iterable<Order> orders=this.getAll();
            for(Order order:orders){
               // System.out.println(order);
                writer.write(order.getId()+","+order.getname()+","+order.getDeliveryDate()+","+order.getCakes());
                writer.write('\n');
            }


        }catch (IOException e) {
            throw new RuntimeException(e);
    }
}}
