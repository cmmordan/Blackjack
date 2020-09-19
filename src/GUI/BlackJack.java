package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.awt.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlackJack extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        BorderPane borderPane = new BorderPane();
        ToolBar toolbar = new ToolBar();
        HBox statusbar = new HBox();
        borderPane.setTop(toolbar);
        //borderPane.setCenter(appContent);
        borderPane.setBottom(statusbar);

        VBox dealerHand = new VBox();
        dealerHand.getChildren().add(new Label("Dealer"));


        VBox playerHand = new VBox();
        playerHand.getChildren().add(new Label("Player 1"));
        HBox playCardVBox = new HBox();
        //Image image = new Image(getClass().getResourceAsStream("labels.jpg"));
        //playCardVBox.getChildren().add(new ImageView(new Image("file:img/cards/2C.png")));

        HBox playerRow = new HBox();

        borderPane.setCenter(dealerHand);

        Scene table = new Scene(borderPane, 300, 250);

        primaryStage.setTitle("Black Jack John");
        primaryStage.setScene(table);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
