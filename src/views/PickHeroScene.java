package views;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import model.characters.Hero;
import engine.Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static engine.Game.availableHeroes;
import static engine.Game.startGame;
import static views.Main.*;

public class PickHeroScene {
	private static HBox hbox1;
	private static HBox hbox2;
	private static VBox root;

	static double buttonWidth = views.Main.getScreenWidth() / 3.3 - 20;
	static double buttonHeight = views.Main.getScreenHeight() / 1.8;

	public static Scene getScene() {
		try {
			Game.loadHeroes("${PATH}\\Heroes.csv");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// Create label
		Label label = new Label("Pick your Hero");
		label.setFont(Font.font("Chiller", 100));
		label.setTextFill(Color.WHITE);

		Glow glow = new Glow();
		glow.setLevel(1); // Set glow level
		label.setEffect(glow);

		Image backgroundImage = new Image(PickHeroScene.class.getResourceAsStream("/Backgrounds/pickHero.jpg"));
		// Set the background image
		Background background = new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(views.Main.getScreenWidth(),
						views.Main.getScreenHeight(), false, false, false, true)));
		// Create the first HBox with three buttons at the bottom center of the screen
		hbox1 = new HBox(10); // Set spacing between buttons
		hbox1.setAlignment(Pos.CENTER);
		Button button1 = new Button("E X P L O R E R S");
		button1.setStyle(
				"-fx-background-radius: 20;-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-weight: bold; -fx-font-size: 15px;");

		Button button2 = new Button("F I G H T E R S");
		button2.setStyle(
				"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-weight: bold; -fx-font-size: 15px;");
		Button button3 = new Button("M E D I C S");
		button3.setStyle(
				"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-weight: bold; -fx-font-size: 15px;");
		button1.setPrefWidth(400);
		button2.setPrefWidth(400);
		button3.setPrefWidth(400);
		hbox1.getChildren().addAll(button1, button2, button3);

		// Create the second HBox (initially empty) at the middle center of the scene
		hbox2 = new HBox();
		hbox2.setSpacing(10);
		hbox2.setAlignment(Pos.CENTER);

		// Create the root VBox that holds both HBoxes
		root = new VBox(30); // Set spacing between HBoxes
		// Apply glow effect
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20, 0, 0, 0));
		root.getChildren().add(label);
		root.setBackground(background);
		root.getChildren().addAll(hbox1, hbox2);

		// Set the action for each button to switch the second HBox
		button1.setOnMouseEntered(event -> {
			button1.setText("Explorers can view the whole map in a turn");
			button1.setStyle(
					"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-style: italic; -fx-font-size: 15px;");
			glow(button1, Color.BLUE, 20);
		});
		button1.setOnMouseExited(event -> {
			button1.setText("E X P L O R E R S");
			button1.setStyle(
					"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-weight: bold; -fx-font-size: 15px;");
			button1.setEffect(null);
		});
		button1.setOnAction(e -> setHBoxContent("Explorers"));

		button2.setOnMouseEntered(event -> {
			button2.setText("Fighters can attack without costing action points in a turn");
			button2.setStyle(
					"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-style: italic; -fx-font-size: 15px;");
			glow(button2, Color.RED, 20);
		});
		button2.setOnMouseExited(event -> {
			button2.setEffect(null);
			button2.setText("F I G H T E R S");
			button2.setStyle(
					"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-weight: bold; -fx-font-size: 15px;");

		});
		button2.setOnAction(e -> setHBoxContent("Fighters"));

		button3.setOnMouseEntered(event -> {
			button3.setText("Medics can attack without costing action points in a turn");
			button3.setStyle(
					"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-style: italic; -fx-font-size: 15px;");
			glow(button3, Color.GREEN, 20);
		});
		button3.setOnMouseExited(event -> {
			button3.setEffect(null);
			button3.setText("M E D I C S");
			button3.setStyle(
					"-fx-background-radius: 20; -fx-font-family: Palatino; -fx-font-weight: bold; -fx-font-size: 15px;");
		});
		button3.setOnAction(e -> setHBoxContent("Medics"));

		return new Scene(root, Main.getScreenWidth(), Main.getScreenHeight());
	}

	public static Button createButton(String name) {
		Image heroImage = Main.HeroesImages.get(name);
		Image heroData = Main.HeroesData.get(name);
		Button button = new Button();
		button.setPrefWidth(buttonWidth);
		button.setPrefHeight(buttonHeight);
		button.setStyle("-fx-background-color: transparent; ");
		glow(button, Color.BLACK, 20);

		ImageView imageView = new ImageView();
		imageView.setImage(heroImage);
		imageView.setFitWidth(buttonWidth - 10);
		imageView.setFitHeight(buttonHeight - 10);
		button.setGraphic(imageView);
		button.setOnMouseEntered(event -> {
			imageView.setImage(heroData);
			button.setGraphic(imageView);
			glow(button, Color.GOLD, 20);
		});
		button.setOnMouseExited(event -> {
			imageView.setImage(heroImage);
			button.setGraphic(imageView);
			glow(button, Color.BLACK, 20);
		});
		button.setOnAction(e -> {
			for (Hero h : availableHeroes)
				if (h.getName().equals(name))
					heroInAction = h;
			startGame(heroInAction);
			stage.setScene(GridScene.getScene());
			try {
				updateMap();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			GridScene.updateDisplayedData(heroInAction);

		});
		return button;
	}

	public static void glow(Button button, Color color, double r) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(color);
		dropShadow.setRadius(r);
		dropShadow.setSpread(0.25);
		button.setEffect(dropShadow);
	}

	public static void glow(Label label, Color color) {
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(color);
		dropShadow.setRadius(3);
		dropShadow.setSpread(0.5);
		label.setEffect(dropShadow);
	}



	private static void setExplorersHbox() {
		Button button1 = createButton("Riley Abel");
		Button button2 = createButton("Tommy Miller");
		Button button3 = createButton("Tess");
		hbox2.getChildren().clear();
		hbox2.getChildren().addAll(button1, button2, button3);
	}

	private static void setFightersHbox() {
		Button button1 = createButton("Joel Miller");
		Button button2 = createButton("David");
		hbox2.getChildren().clear();
		hbox2.getChildren().addAll(button1, button2);
	}

	private static void setMedicsHbox() {
		Button button1 = createButton("Ellie Williams");
		Button button2 = createButton("Bill");
		Button button3 = createButton("Henry Burell");
		hbox2.getChildren().clear();
		hbox2.getChildren().addAll(button1, button2, button3);

	}

	private static void setHBoxContent(String heroClass) {
		hbox2.getChildren().clear();
		// Add content to the second HBox based on the selected button
		switch (heroClass) {
		case "Explorers":
			setExplorersHbox();
			break;
		case "Fighters":
			setFightersHbox();
			break;
		case "Medics":
			setMedicsHbox();
			break;
		default:
			break;
		}
	}
}