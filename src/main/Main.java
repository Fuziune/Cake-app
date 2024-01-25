package main;

import Service.Service;
import Settings.Settings;
import com.sun.javafx.stage.EmbeddedWindow;
import domain.Cake;
import domain.Order;
import gui.CakeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Service.Service;
import repository.GenericRepository;
import repository_impl.CakeRepoDataBase;
import repository_impl.OrderRepoDataBase;
import service_impl.GenericService;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Settings settings=new Settings();
        OrderRepoDataBase repo=new OrderRepoDataBase();
        GenericRepository repository=new CakeRepoDataBase();
        GenericRepository<Order> repoOrder= new OrderRepoDataBase();
        GenericRepository<Cake> repoCake=new CakeRepoDataBase();
        Service service=new GenericService<>(repo);
        CakeController controller=new CakeController(service);
        // Your application logic goes here
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/CakesGUI.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
