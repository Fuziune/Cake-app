package UI;

import Service.Service;
import Settings.Settings;
import domain.Cake;
import domain.Identifiable;
import domain.Order;
import domain.OrderStatus;
import repository_impl.*;
import service_impl.GenericService;
import service_impl.GenericServiceFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ui {
    private static Service<Order> orderService1;
    private static Service<Cake> cakeService1;

    private static CakeRepoDataBase cakeRepoDataBase;
    private static OrderRepoDataBase orderRepoDataBase;

    public static void main(String[] args) {
        manageRepository();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Manage Cakes");
            System.out.println("2. Manage Orders");
            System.out.println("3. reports");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageEntities(cakeService1, "Cake", scanner);
                    break;
                case 2:
                    manageEntities(orderService1, "Order", scanner);
                    break;
                case 3:
                    reports(scanner);
                case 4:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static <T extends Identifiable> void manageRepository(){
        Scanner scaner =new Scanner(System.in);
        Settings settings=new Settings();
        System.out.println("1-Choose repository input/output type");
        System.out.println("2-MemoryRepository");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        Cake cake1=new Cake(1,"chocolate","large",200);
        Cake cake2=new Cake(2,"banana","medium",120);
        Cake cake3=new Cake(3,"cheese","small",100);
        Cake cake4=new Cake(4,"vanilla","large",180);
        Cake cake5=new Cake(5,"lemon","small",130);
        List<Cake> da1=new ArrayList<Cake>();
        da1.add(cake1);
        da1.add(cake2);
        Order order=new Order(1,"Ioan",LocalDate.of(2020, 1, 8),da1);
        int choise=scaner.nextInt();
        scaner.nextLine();
        switch (choise){
            case 1:
                String typeRepo=Settings.getPropertyTest("Repository");
                if(typeRepo.equals("file")){
                    settings.updateProperty("Location","file");
                Scanner scaner1=new Scanner(System.in);
                System.out.println("1-text repository");
                System.out.println("2-binary repository");
                System.out.println("3-JASON repository");
                    System.out.println("XML repository");
                int choise1=scaner1.nextInt();
                scaner1.nextLine();
                switch (choise1){
                    case 1:
                        settings.updateProperty("CakeRepository","text");
                        settings.updateProperty("CakeFilePath","cakes.txt");
                        settings.updateProperty("OrderRepository","text");
                        settings.updateProperty("OrderFilePath","orders.txt");
                        break;
                    case 2:
                        settings.updateProperty("CakeRepository","binary");
                        settings.updateProperty("CakeFilePath","cakes.bin");
                        settings.updateProperty("OrderRepository","binary");
                        settings.updateProperty("OrderFilePath","orders.bin");
                        break;
                    case 3:
                        System.out.println("Exiting the application.");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

                String cakeRepositoryType = settings.getPropertyTest("CakeRepository");
                System.out.println(cakeRepositoryType);
                String cakeFilePath = Settings.getPropertyTest("CakeFilePath");
                System.out.println(cakeFilePath);
                String orderRepositoryType = settings.getPropertyTest("OrderRepository");
                System.out.println(orderRepositoryType);
                String orderFilePath = settings.getPropertyTest("OrderFilePath");
                System.out.println(orderFilePath);
                FileRepository<Cake> cakeRepository;
                if (cakeRepositoryType.equals("text")) {
                    cakeRepository = new CakeRepositoryTextFile(cakeFilePath);
                } else {
                    cakeRepository = new CakeRepositoryBinaryFile<Cake>(cakeFilePath);
                }

                FileRepository<Order> orderRepository ;
                if (orderRepositoryType.equals("text")) {
                    orderRepository = new OrderRepositoryTextFile(orderFilePath);
                }
                else{
                    orderRepository=new CakeRepositoryBinaryFile<Order>(orderFilePath);
                }

                cakeRepository.addElem(cake1);
                cakeRepository.addElem(cake2);
                cakeRepository.addElem(cake3);
                cakeRepository.addElem(cake4);
                cakeRepository.addElem(cake5);
                cakeService1 = new GenericServiceFile<Cake>(cakeRepository);

                orderRepository.addElem(order);
                orderService1 = new GenericServiceFile<Order>(orderRepository);}
                else if (typeRepo.equals("database")) {
                    settings.updateProperty("Location","data");
                    CakeRepoDataBase cakeRepoData=new CakeRepoDataBase();
                    OrderRepoDataBase orderRepoData=new OrderRepoDataBase();
                    cakeService1=new GenericService<Cake>(cakeRepoData);
                    orderService1=new GenericService<Order>(orderRepoData);
                    /*if(cakeService1.getEntityById(1)==null)
                    {cakeService1.createEntity(cake1);}
                    if(cakeService1.getEntityById(2)==null)
                        cakeService1.createEntity(cake2);
                    if(cakeService1.getEntityById(3)==null)
                        cakeService1.createEntity(cake3);
                    if(cakeService1.getEntityById(4)==null)
                        cakeService1.createEntity(cake4);
                    if(cakeService1.getEntityById(5)==null)
                        cakeService1.createEntity(cake5);
                    //if(orderService1.getEntityById(1)==null)
                    //orderService1.createEntity(order);
                    //orderRepoDataBase.saveOrder(order);*/

                }
                break;


            case 2:
                MemoryRepository<Cake> cakeRepository1= new MemoryRepository<>();
                cakeRepository1.save(cake1);
                cakeRepository1.save(cake2);
                cakeRepository1.save(cake3);
                cakeRepository1.save(cake4);
                cakeRepository1.save(cake5);
                cakeService1 = new GenericService<Cake>(cakeRepository1);
                MemoryRepository<Order> orderRepository1 = new MemoryRepository<>();
                orderRepository1.save(order);
                orderService1 = new GenericService<Order>(orderRepository1);
                break;
            case 3:
                System.out.println("Exiting the application.");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    private static <T extends Identifiable> void manageEntities(Service<T> service, String entityName, Scanner scanner) {
        while (true) {
            System.out.println("1. Create " + entityName);
            System.out.println("2. Update " + entityName);
            System.out.println("3. Delete " + entityName);
            System.out.println("4. List All " + entityName + "s");
            System.out.println("5  Reports");
            System.out.println("6. Go Back");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createEntity(service, entityName, scanner);
                    break;
                case 2:
                    updateEntity(service, entityName, scanner);
                    break;
                case 3:
                    deleteEntity(service, entityName, scanner);
                    break;
                case 4:
                    listEntities(service, entityName);
                    break;
                case 5:
                    if(entityName.equals("Cake"))
                        System.out.println("No reports for cakes menu");
                    else
                        reports(scanner);
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    //Names of Persons Who Booked a Certain Cake:
    public static List<String> getNamesForCake(Cake cake, Collection<Order> orders) {
        List<String> names = orders.stream()
                .filter(order -> order.getCakes().contains(cake))
                .map(Order::getCustomerName)
                .collect(Collectors.toList());

        System.out.println("Filtered Orders: " + names);
        return names;
    }
    //All Cakes Rented by a Certain Person:
    public static List<Cake> getAllCakesRentedByPerson(String personName, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getCustomerName().equals(personName))
                .flatMap(order -> order.getCakes().stream())
                .collect(Collectors.toList());
    }
    //List of Birthday Cakes Ordered for a Given Day:
    public static List<Cake> getBirthdayCakesForDay(LocalDate deliveryDate, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getDeliveryDate().equals(deliveryDate))
                .flatMap(order -> order.getCakes().stream())
                .collect(Collectors.toList());
    }
    //Days When a Certain Birthday Cake Has to Be Delivered:
    public static List<LocalDate> getDeliveryDaysForBirthdayCake(Cake cake, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getCakes().contains(cake))
                .map(Order::getDeliveryDate)
                .collect(Collectors.toList());
    }
    //Status of a Certain Order (given by Order ID)
    public static OrderStatus getStatusForOrder(long orderId, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getId() == orderId)
                .findFirst()
                .map(Order::getStatus)
                .orElse(null); // or throw an exception if needed
    }
private static<T extends Identifiable> void reports(Scanner scanner){
        //Scanner scanner=new Scanner(System.in);
    while(true){
    System.out.println( "1-Names of Persons Who Booked a Certain Cake");
    System.out.println("2-All Cakes Rented by a Certain Person");
    System.out.println("3-List of Birthday Cakes Ordered for a Given Day");
    System.out.println("4-Days When a Certain Birthday Cake Has to Be Delivered");
    System.out.println("5-Status of a Certain Order (given by Order ID)");
    System.out.println("6-Exit");
    int care=scanner.nextInt();
    scanner.nextLine();
        switch (care){
            case 1:
                System.out.println("Enter the cake ID:");
                long cakeId = scanner.nextLong();
                scanner.nextLine();
                Cake cake =cakeService1.getEntityById(cakeId);//service.getById(cakeId);
                System.out.println(cake);
                if (cake != null) {
                    Collection<Order> lista=  orderService1.getAllEntities("Order");
                    System.out.println(lista);
                    List<String> names = getNamesForCake(cake,lista); //(List)orderService1.getAllEntities("Order"));
                    System.out.println("Names of Persons Who Booked the Cake: " + names);
                } else {
                    System.out.println("Cake not found.");
                }
                break;

            case 2:
                System.out.println("Enter the person's name:");
                String personName = scanner.nextLine();
                Collection<Order> lista=  orderService1.getAllEntities("Order");
                List<Cake> cakesRentedByPerson = getAllCakesRentedByPerson(personName, lista);
                System.out.println("Cakes Rented by " + personName + ": " + cakesRentedByPerson);
                break;

            case 3:
                System.out.println("Enter the delivery date (yyyy-MM-dd):");
                String dateStr = scanner.nextLine();
                LocalDate deliveryDate = LocalDate.parse(dateStr);
                Collection<Order> lista2=  orderService1.getAllEntities("Order");
                List<Cake> birthdayCakesForDay = getBirthdayCakesForDay(deliveryDate, lista2);
                System.out.println("Birthday Cakes Ordered for " + deliveryDate + ": " + birthdayCakesForDay);
                break;

            case 4:
                System.out.println("Enter the cake ID:");
                long cakeIdForDeliveryDays = scanner.nextLong();
                scanner.nextLine();
                Cake cakeForDeliveryDays = cakeService1.getEntityById(cakeIdForDeliveryDays);
                if (cakeForDeliveryDays != null) {
                    Collection<Order> lista3=  orderService1.getAllEntities("Order");
                    List<LocalDate> deliveryDays = getDeliveryDaysForBirthdayCake(cakeForDeliveryDays, lista3);
                    System.out.println("Days When Cake has to Be Delivered: " + deliveryDays);
                } else {
                    System.out.println("Cake not found.");
                }
                break;
            case 5:
                System.out.println("Enter the order ID:");
                long orderId = scanner.nextLong();
                scanner.nextLine();
                Collection<Order> lista4= orderService1.getAllEntities("Order");
                OrderStatus orderStatus = getStatusForOrder(orderId, lista4);
                if (orderStatus != null) {
                    System.out.println("Status of Order " + orderId + ": " + orderStatus);
                } else {
                    System.out.println("Order not found.");
                }
                break;
            case 6:
                return; // Exit the loop and end the method

            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
        }

        }
}
    private static <T extends Identifiable> void createEntity(Service<T> service, String entityName, Scanner scanner) {
        if (entityName.equals("Cake")) {
            System.out.print("Enter Cake Flavor: ");
            String flavor = scanner.nextLine().trim();
            System.out.print("Enter Cake Size: ");
            String size = scanner.nextLine().trim();
            System.out.print("Enter Cake Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Cake cake = new Cake(0, flavor, size, price);
            service.createEntity((T) cake);
        }
        else if (entityName.equals("Order")) {
            System.out.print("Enter Customer Name: ");
            String customerName = scanner.nextLine().trim();
            System.out.print("Enter Delivery Date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine().trim();
            LocalDate deliveryDate = LocalDate.parse(dateStr);

            List<Cake> cakes = new ArrayList<>();
            while (true) {
                System.out.print("Enter Cake ID (0 to stop adding cakes): ");
                long cakeId = scanner.nextLong();
                scanner.nextLine();
                cakeService1.getAllEntities("cake");
                if (cakeId == 0) {
                    break;
                }
                Cake cake = cakeService1.getEntityById(cakeId);
                if (cake != null) {
                    cakes.add(cake);
                } else {
                    System.out.println("Invalid Cake ID. Cake not added to the order.");
                }
            }

            Order order = new Order(0, customerName, deliveryDate, cakes);
            service.createEntity((T) order);
        }
        System.out.println(entityName + " created successfully.");
    }

    private static <T extends Identifiable> void updateEntity(Service<T> service, String entityName, Scanner scanner) {
        System.out.print("Enter " + entityName + " ID to update: ");
        long entityId = scanner.nextLong();
        scanner.nextLine();

        T entity = service.getEntityById(entityId);
        if (entity != null) {
            if (entityName.equals("Cake")) {
                System.out.print("Enter Cake Flavor: ");
                String flavor = scanner.nextLine().trim();
                System.out.print("Enter Cake Size: ");
                String size = scanner.nextLine().trim();
                System.out.print("Enter Cake Price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();

                Cake updatedCake = new Cake(entityId, flavor, size, price);
                service.updateEntity((T) updatedCake);
            } else if (entityName.equals("Order")) {
                System.out.print("Enter Customer Name: ");
                String customerName = scanner.nextLine().trim();
                System.out.print("Enter Delivery Date (YYYY-MM-DD): ");
                String dateStr = scanner.nextLine().trim();
                LocalDate deliveryDate = LocalDate.parse(dateStr);

                List<Cake> cakes = new ArrayList<>();
                while (true) {
                    System.out.print("Enter Cake ID (0 to stop adding cakes): ");
                    long cakeId = scanner.nextLong();
                    scanner.nextLine();
                    if (cakeId == 0) {
                        break;
                    }

                    Cake cake = cakeService1.getEntityById(cakeId);
                    if (cake != null) {
                        cakes.add(cake);
                    } else {
                        System.out.println("Invalid Cake ID. Cake not added to the order.");
                    }
                }

                Order updatedOrder = new Order(entityId, customerName, deliveryDate, cakes);
                service.updateEntity((T) updatedOrder);
            }
            System.out.println(entityName + " with ID " + entityId + " updated successfully.");
        } else {
            System.out.println("Invalid " + entityName + " ID. Update failed.");
        }
    }

    private static <T extends Identifiable> void deleteEntity(Service<T> service, String entityName, Scanner scanner) {
        System.out.print("Enter " + entityName + " ID to delete: ");
        long entityId = scanner.nextLong();
        scanner.nextLine();

        try {
            if(entityName.equals("Cake")){
                List<Cake> list=new ArrayList<>();
                for(Order order:orderService1.getAllEntities("order")){
                    list.clear();
                    for(Cake cake:order.getCakes()){
                        if(cake.getId()!=entityId)
                        {list.add(cake);}
                    }
                    if(list.size()==order.getCakes().size()){
                        list.clear();
                    }
                    else{
                        order.setCakes(list);
                    }
                }
            }
            service.deleteEntity(entityId, entityName);
            System.out.println(entityName + " with ID " + entityId + " has been deleted.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static <T extends Identifiable> void listEntities(Service<T> service, String entityName) {
        Collection<T> entities= service.getAllEntities(entityName);
        if (entities.isEmpty()) {
            System.out.println("No " + entityName + "s found.");
        } else {
            System.out.println("List of " + entityName + "s:");
            for (T entity : entities) {
                System.out.println(entity);
            }
        }
    }
}
