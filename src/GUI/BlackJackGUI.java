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
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BlackJackGUI extends Application {
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
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
        HBox dealerHBox = new HBox();
        dealerHand.getChildren().add(dealerHBox);

        VBox playerHand = new VBox();
        playerHand.getChildren().add(new Label("Player 1"));
        HBox playCardVBox = new HBox();

        try {
            Image card2C = new Image(new FileInputStream("img/cards/2C.png"));
            Image card2D = new Image(new FileInputStream("img/cards/2C.png"));

            ImageView cardView = new ImageView(card2C);
            cardView.setImage(card2D);
            cardView.setPreserveRatio(true);
            cardView.setFitHeight(200);
            playCardVBox.getChildren().add(cardView);
        }
        catch(Exception e) {
            // Image not found
        }



        HBox playerRow = new HBox();
        playerRow.getChildren().add(playCardVBox);

        borderPane.setCenter(playerRow);

        Scene table = new Scene(borderPane, 300, 250);

        primaryStage.setTitle("Black Jack John");
        primaryStage.setScene(table);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
