package sortingVisualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root1 = FXMLLoader.load(getClass().getResource("resources/mainWindow.fxml"));
        primaryStage.setTitle("Sorting Visualizer");
        Image icon = new Image(getClass().getResourceAsStream("resources/bar-chart.png"));
        primaryStage.getIcons().addAll(icon);
        primaryStage.setScene(new Scene(root1));
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setOnCloseRequest(e-> System.exit(0));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
