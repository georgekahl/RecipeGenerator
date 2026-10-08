package pantry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pantry.database.Database;


public class Main extends Application{

    @Override
    public void start (Stage stage) throws Exception{

        Database.createTables();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml")
        );
        Scene scene = new Scene(loader.load(), 1100, 700);

        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()
        );
        stage.setTitle("Recipe Generator");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}