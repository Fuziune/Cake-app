package gui;

import domain.Cake;
import domain.Identifiable;
import domain.Order;
import domain.OrderStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Service.Service;
import javafx.stage.Stage;
import repository.GenericRepository;
import repository_impl.CakeRepoDataBase;
import repository_impl.CakeRepositoryTextFile;
import repository_impl.OrderRepoDataBase;
import service_impl.GenericService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CakeController<T extends Identifiable> {
    @FXML
    private Service<T> service;
    private final GenericRepository<T> orderRepoDataBase= (GenericRepository<T>) new OrderRepoDataBase();
    private Service<T> service1=new GenericService<>(orderRepoDataBase);
    // Inject the service through the constructor
    /*public CakeController(Service<Order> service) {
        this.service = service;
    }*/
    public void SetRepoCake(){
        GenericRepository<Cake> rpr =  new CakeRepoDataBase();
        service= new GenericService<T>((GenericRepository<T>) rpr);
    }
    @FXML
    private Button AddOrder;

    @FXML
    private Button Exit;
    @FXML
    private Button Submit;

    @FXML
    private Button ShowMenu;
//ready or not
    @FXML
    private DialogPane welcome;
    @FXML
    private ButtonBar ButtonBarCake;
    @FXML
    private Button Back;
    @FXML
    private Button CreateCake;
    @FXML
    void CreatePrajitura() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/CreateCake.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) CreateCake.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private TextField TextFlavor;

    @FXML
    private TextField TextPrice;

    @FXML
    private TextField TextSize;

    @FXML
    private TextField Textid;
    @FXML
    private Button DeleteCake;
    @FXML
    private TextField TextDeleteId;
    void DeleteCake() {
//        GenericRepository<Cake> rpr =new CakeRepoDataBase();
//        service= new GenericService<T>((GenericRepository<T>) rpr);
        SetRepoCake();
        try {
            // Parse the cake ID from the TextDeleteId field
            Long cakeIdToDelete = Long.parseLong(TextDeleteId.getText());

            // Find the cake with the specified ID from your service
            Cake cakeToDelete = (Cake) service.getEntityById(cakeIdToDelete);

            if (cakeToDelete != null) {
                // Delete the cake from your service
                service.deleteEntity(cakeIdToDelete,"Cake");

                // Optionally, you can update the list view or perform other actions

                // Clear the TextDeleteId field after deleting the cake
                TextDeleteId.clear();
            } else {
                // Handle the case where no cake is found with the specified ID
                // You might want to display an error message or take appropriate action
            }
        } catch (NumberFormatException e) {
            // Handle the case where the ID couldn't be parsed
            e.printStackTrace(); // You might want to log or display an error message
        }
    }
    @FXML
    private Button ReadCake;
    @FXML
    void ReadPrajitura() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/CakesRead.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) ReadCake.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void DeletePrajitura() {

        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/DeleteCake.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) DeleteCake.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    void UpdateCake() {
//       GenericRepository<Cake> rpr =new CakeRepoDataBase();
//        service= new GenericService<T>((GenericRepository<T>) rpr);
        SetRepoCake();
        try {
            // Parse values from text fields
            long cakeId = Long.parseLong(Textid.getText());
            String flavor = TextFlavor.getText();
            double price = Double.parseDouble(TextPrice.getText());
            String size = TextSize.getText();

            // Find the cake with the specified id from your service
            Cake selectedCake = (Cake) service.getEntityById(cakeId);

            if (selectedCake != null) {
                // Update the properties of the selected cake
                selectedCake.setFlavor(flavor);
                selectedCake.setPrice(price);
                selectedCake.setSize(size);

                // Update the cake in your service
                service.updateEntity((T) selectedCake);

                // Optionally, you can update the list view or perform other actions

                // Clear the text fields after updating the cake
                Textid.clear();
                TextFlavor.clear();
                TextPrice.clear();
                TextSize.clear();
            } else {
                // Handle the case where no cake is found with the specified id
                // You might want to display an error message or take appropriate action
            }
        } catch (NumberFormatException e) {
            // Handle the case where the id, price, or other fields couldn't be parsed
            e.printStackTrace(); // You might want to log or display an error message
        }
    }
    void BirthCake(){
//       GenericRepository<Cake> rpr =new CakeRepoDataBase();
//        service= new GenericService<T>((GenericRepository<T>) rpr);
        SetRepoCake();
        try {
            // Parse values from text fields
            String flavor = TextFlavor.getText();
            double price = Double.parseDouble(TextPrice.getText());
            String size = TextSize.getText();

            // Create a new Cake object
            Cake newCake = new Cake(0,flavor, size, price);

            // Add the newCake to your service
            service.createEntity((T) newCake);

            // Optionally, you can update the list view or perform other actions

            // Clear the text fields after adding the cake
            TextFlavor.clear();
            TextPrice.clear();
            TextSize.clear();
        } catch (NumberFormatException e) {
            // Handle the case where the price couldn't be parsed as a double
            e.printStackTrace(); // You might want to log or display an error message
        }
    }
    @FXML
    private Button UpdateCake;
    @FXML
    void UpdatePrajitura() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/UpdateCake.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) UpdateCake.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void Submit1() {
        BirthCake();
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InCakes.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void Submit3(){
        DeleteCake();
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InCakes.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void Submit2() {
        UpdateCake();
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InCakes.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public CakeController(Service service) {
        this.service = service;
    }
    @FXML
    private Button Cakes;


    @FXML
    void BackGo2() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/CakesGUI.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Back.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void BackGo3() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InCakes.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Back.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void AddOrderFunction() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) AddOrder.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void CakesEntre() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InCakes.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Cakes.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void BackGo() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/CakesGUI.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Back.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void BackGo1() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/CakesGUI.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Back.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void PrintMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MenuPrint.fxml"));

            // Create a new instance of CakeController with the required service
            CakeController menuController = new CakeController(service);

            // Set the new controller for the loaded FXML
            loader.setController(menuController);

            Stage stage = (Stage) ShowMenu.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private ListView<Cake> AllCakes = new ListView<>();
void PopulateList(){
    GenericRepository<Cake> rpr =new CakeRepoDataBase();
    service= new GenericService<T>((GenericRepository<T>) rpr);
    ObservableList<Cake> CakesList= FXCollections.observableArrayList((ArrayList<Cake>) service.getAllEntities("Cake"));
    AllCakes.setItems(CakesList);
}
    void PopulateOrdersList() {
        GenericRepository<Order> orderRepo = new OrderRepoDataBase();
        service = new GenericService<T>((GenericRepository<T>) orderRepo);
        ObservableList<Order> ordersList = FXCollections.observableArrayList((ArrayList<Order>) service.getAllEntities("Order"));
        AllOrders.setItems(ordersList);
    }

    public void initialize(){
        PopulateList();
        PopulateOrdersList();
//        if(!DeliveriesOnDate.toString().isEmpty()){
//            //apelare functie ce afiseaza raportul respectiv folosind informatiile din DeliveriesOnDate
//            AllReports.setItems((ObservableList) getBirthdayCakesForDay(LocalDate.parse(DeliveriesOnDate.getText()),(Collection<Order>) service1.getAllEntities("Order")));
//
//        }
//        if(!DeliveriesForOrder.toString().isEmpty()){
//            //apelare functie ce afiseaza raportul respectiv folosind informatiile din DeliveriesForOrder
//            String id = DeliveriesForOrder.getText();
//            Cake cake = (Cake)service.getEntityById(Long.parseLong(id));
//            AllReports.setItems((ObservableList) getDeliveryDaysForBirthdayCake(cake,(Collection<Order>) service1.getAllEntities("Order")));
//        }
//        if(!OrderStatus.toString().isEmpty()){
//            //apelare functie ce afiseaza raportul respectiv folosind informatiile din OrderStatus
//            String id = OrderStatus.getText();
//            List<OrderStatus> orderStatuses = new ArrayList<>();
//            orderStatuses.add(getStatusForOrder(Long.parseLong(id),(Collection<Order>) service1.getAllEntities("Order")));
//            AllReports.setItems((ObservableList) orderStatuses);
//        }
//        if(!OrdersOfPerson.toString().isEmpty()){
//            //apelare functie ce afiseaza raportul respectiv folosind informatiile din OrdersOfPerson
//            AllReports.setItems((ObservableList) getAllCakesRentedByPerson(OrdersOfPerson.getText(),(Collection<Order>) service1.getAllEntities("Order")));
//        }
//        if(!CustomersByOrder.toString().isEmpty()){
//            //apelare functie ce afiseaza raportul respectiv folosind informatiile din CustomersByOrder
//            String id = CustomersByOrder.getText();
//            Cake cake = (Cake) service.getEntityById(Long.parseLong(id));
//
//            AllReports.setItems((ObservableList) getNamesForCake(cake,(Collection<Order>) service1.getAllEntities("Order")));
//}
    }
    @FXML
    private void exitApplication() {
        Stage stage = (Stage) Exit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private Button CreateOrder;

    @FXML
    private Button DeleteOrder;

    @FXML
    private Button ReadOrder;

    @FXML
    private Button UpdateOrder;



    @FXML
    void CreateComanda() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/CreateOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) CreateOrder.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void DeleteComanda() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/DeleteOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) DeleteOrder.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ReadComanda() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/ReadOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) ReadOrder.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateComanda() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/UpdateOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) UpdateOrder.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private Button AddCakesButton;
    @FXML
    void AddCakeOrder() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/AddCakesOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) AddCakesButton.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @ FXML
    private DatePicker DatePicker=new DatePicker();

    //private String[] CakesForOrderNow;
    @FXML
    private TextField TextName=new TextField();
    @FXML
    private TextField AddCakeById=new TextField();
    String[] addthecakesfororder(){
        return AddCakeById.getText().split(",");
    }
    private void addOrderWithCakes() {
        GenericRepository<Cake> rpr =new CakeRepoDataBase();
        service= new GenericService<T>((GenericRepository<T>) rpr);
        try {
            LocalDate orderDate = DatePicker.getValue();
            String customerName = TextName.getText();

            // Assuming you have an Order class with an appropriate constructor

            //System.out.println(Arrays.toString(CakesForOrderNow));
            // Split the string of cake IDs and add them to the order
            String[] cakeIds = AddCakeById.getText().split(",");
                List<Cake> cakesToAdd = new ArrayList<>();
            System.out.println(Arrays.toString(cakeIds));
            for (String cakeId : cakeIds) {
                long id = Long.parseLong(cakeId.trim());

                // Fetch the cake by ID from your repository or service
                Cake cake = (Cake) service.getEntityById(id);

                if (cake != null) {
                    cakesToAdd.add(cake);
                }
                System.out.println(cakesToAdd);
            }
            Order newOrder = new Order(0, customerName,orderDate,cakesToAdd);
            // Add the new order to your service or repository
            service1.createEntity((T) newOrder);
            // Optionally, you can update your orders list or perform other actions

            // Clear the input fields after adding the order
            DatePicker.setValue(null);
            TextName.clear();
            AddCakeById.clear();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    void Submit4() {
        addOrderWithCakes();
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private ListView<Order> AllOrders=new ListView<>();


    @FXML
    void BackGo4() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Back.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private TextField TextDeletedOrder;
    @FXML
    private void deleteOrderById() {
        try {
            // Parse the order ID from the text field
            long orderId = Long.parseLong(TextDeletedOrder.getText());

            // Get the order from your service or repository based on the ID
            Order orderToDelete = (Order) service.getEntityById(orderId);

            if (orderToDelete != null) {
                // Delete the order from your service or repository
                service1.deleteEntity(orderId,"Order");

                // Optionally, you can update the orders list or perform other actions

                // Clear the text field after deleting the order
                TextDeletedOrder.clear();
            } else {
                // Handle the case where no order with the specified ID is found
                // You might want to display an error message or take appropriate action
            }
        } catch (NumberFormatException e) {
            // Handle the case where the order ID couldn't be parsed as a long
            e.printStackTrace(); // You might want to log or display an error message
        }
    }

    @FXML
    void Submit5() {
        deleteOrderById();
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private TextField AddCakeByIdUp;

    @FXML
    private DatePicker DatePickerUp;

    @FXML
    private TextField IdOrderUp;

    @FXML
    private TextField NameUp;
    @FXML
    void Submit6() {
        updateOrder();
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Submit.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void updateOrder() {
        GenericRepository<Cake> rpr =new CakeRepoDataBase();
        service= new GenericService<T>((GenericRepository<T>) rpr);
        try {
            // Parse the order ID from the text field
            long orderId = Long.parseLong(IdOrderUp.getText());

            // Get the order from your service or repository based on the ID
            Order orderToUpdate = (Order) service1.getEntityById(orderId);

            if (orderToUpdate != null) {
                // Update the order properties
                LocalDate orderDate = DatePickerUp.getValue();
                String customerName = NameUp.getText();

                // Assuming you have an Order class with appropriate setter methods
                orderToUpdate.setDeliveryDate(orderDate);
                orderToUpdate.setCustomerName(customerName);

                // Parse the cake IDs from the text field
                String cakeIdsString = AddCakeByIdUp.getText();
                String[] cakeIds = cakeIdsString.split(",");

                // Assuming you have a method to get cakes by ID from your service or repository
                List<Cake> cakesToAdd = new ArrayList<>();
                for (String cakeId : cakeIds) {
                    long id = Long.parseLong(cakeId.trim());
                    Cake cake = (Cake) service.getEntityById(id);
                    if (cake != null) {
                        cakesToAdd.add(cake);
                    }
                }

                // Set the list of cakes for the order
                orderToUpdate.setCakes(cakesToAdd);

                // Update the order in your service or repository
                service1.updateEntity((T) orderToUpdate);

                // Optionally, you can update the orders list or perform other actions

                // Clear the input fields after updating the order
                AddCakeByIdUp.clear();
                DatePickerUp.setValue(null);
                IdOrderUp.clear();
                NameUp.clear();
            } else {
                // Handle the case where no order with the specified ID is found
                // You might want to display an error message or take appropriate action
            }
        } catch (NumberFormatException e) {
            // Handle the case where the order ID couldn't be parsed as a long
            e.printStackTrace(); // You might want to log or display an error message
        }
    }
    @FXML
    void GoReports() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/ReportOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Report.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private Button Report;
    @FXML
    private ListView<?> AllReports;


    @FXML
    private Button DeliveriesDate;

    @FXML
    private Button Deliveryday;

    @FXML
    private Button GetCustomOrder;

    @FXML
    private Button OrderStatuss;

    @FXML
    private Button OrdersOfPersonB;

    @FXML
    private TextField Os;

    @FXML
    void BackGo5() {
        try {
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/gui/InOrder.fxml"));
            CakeController controller = new CakeController((GenericService) service);
            Stage stage = (Stage) Back.getScene().getWindow();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private TextField DeliveriesOnDate = new TextField();
    @FXML
    private TextField DeliveriesForOrder = new TextField();
    @FXML
    private TextField OrderStatus = new TextField();
    @FXML
    private TextField OrdersOfPerson = new TextField();
    @FXML
    private TextField CustomersByOrder = new TextField();

    //Names of Persons Who Booked a Certain Cake:
    public  List<String> getNamesForCake(Cake cake, Collection<Order> orders) {
        List<String> names = orders.stream()
                .filter(order -> order.getCakes().contains(cake))
                .map(Order::getCustomerName)
                .collect(Collectors.toList());

        System.out.println("Filtered Orders: " + names);
        return names;
    }
    //All Cakes Rented by a Certain Person:
    public  List<Cake> getAllCakesRentedByPerson(String personName, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getCustomerName().equals(personName))
                .flatMap(order -> order.getCakes().stream())
                .collect(Collectors.toList());
    }
    //List of Birthday Cakes Ordered for a Given Day:
    public  List<Cake> getBirthdayCakesForDay(LocalDate deliveryDate, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getDeliveryDate().equals(deliveryDate))
                .flatMap(order -> order.getCakes().stream())
                .collect(Collectors.toList());
    }
    //Days When a Certain Birthday Cake Has to Be Delivered:
    public  List<LocalDate> getDeliveryDaysForBirthdayCake(Cake cake, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getCakes().contains(cake))
                .map(Order::getDeliveryDate)
                .collect(Collectors.toList());
    }
    //Status of a Certain Order (given by Order ID)
    public OrderStatus getStatusForOrder(long orderId, Collection<Order> orders) {
        return orders.stream()
                .filter(order -> order.getId() == orderId)
                .findFirst()
                .map(Order::getStatus)
                .orElse(null); // or throw an exception if needed
    }

}
