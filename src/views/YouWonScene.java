package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class YouWonScene {

	public static Scene getScene() {

		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(30);

		Label lostLabel = new Label("You Won");
		lostLabel.setFont(Font.font("Chiller", 120));
		lostLabel.setTextFill(Color.GREEN);

		Button exitButton = new Button("    Exit    ");
		exitButton.setAlignment(Pos.BOTTOM_CENTER);
		exitButton.setOnAction(event -> {
			Main.stage.close();
		});

		root.getChildren().addAll(lostLabel, exitButton);

		Image backgroundImage = new Image(GridScene.class.getResourceAsStream("/Backgrounds/YouWon.jpg"));
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setPreserveRatio(false);
		backgroundImageView.setFitHeight(Main.getScreenHeight());
		backgroundImageView.setFitWidth(Main.getScreenWidth());

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(backgroundImageView, root);
		stackPane.setPrefSize(backgroundImage.getWidth(), backgroundImage.getHeight());

		Scene scene = new Scene(stackPane);
		return scene;
	}
}