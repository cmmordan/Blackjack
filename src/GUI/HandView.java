package GUI;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HandView extends VBox {

    Core.Hand hand;

    HBox cardLayout = new HBox();

    HandView(Core.Hand hand, String handName) {

        Label nameLabel = new Label(handName);
        this.getChildren().add(nameLabel);
        this.getChildren().add(cardLayout);
    }

    //syncView()

}