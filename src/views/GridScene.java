package views;

import engine.Game;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.transform.Rotate;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import model.characters.Character;
import model.characters.Direction;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.world.CharacterCell;


import java.awt.Point;
import java.io.IOException;

import static views.Main.*;

public class GridScene {

	private static final int GRID_SIZE = 15;

	public static Button[][] buttons = new Button[GRID_SIZE][GRID_SIZE];
	public static BorderPane bp = new BorderPane();
	public static StackPane gridStackPane = new StackPane();
	public static GridPane gridPane;

	public static VBox heroesBox = new VBox();

	public static Label currHero = new Label();
	public static Button displayButton = new Button();
	private static ProgressBar progressBar = new ProgressBar();
	public static Label actPts = new Label();
	public static Label supplies = new Label();
	public static Label vaccines = new Label();
	public static Label attackDmg = new Label();
	public static Label heroType = new Label();

	public static GridPane leftGridPane = new GridPane();
	public static GridPane rightGridPane = new GridPane();
	public static Button useSpecial;
	public static void setPogressBar(Hero h) {
		Label l = new Label(h.getCurrentHp() + "/" + h.getMaxHp());
		l.setFont(Font.font("Arial", 20)); // Set font and size
		l.setTextFill(Color.BLACK);
		StackPane s = new StackPane(progressBar, l);
		progressBar.setPrefWidth(180);
		progressBar.setPrefHeight(25);
		progressBar.setProgress((double) h.getCurrentHp() / h.getMaxHp());
		if (h.getCurrentHp() <= (0.2 * h.getMaxHp())) {
			progressBar.setStyle("-fx-accent: red;");
		} else {
			progressBar.setStyle("-fx-accent: green;");
		}
		leftGridPane.add(s, 0, 2);
	}

	public static void updateDisplayedData(Hero hero) {
		if (hero == null) {
			setPogressBar(deadHero);

			currHero.setText(deadHero.getName());
			currHero.setFont(Font.font("Arial", 20));
			currHero.setTextFill(Color.RED);
			PickHeroScene.glow(currHero, Color.RED);

			ImageView heroImageView = new ImageView(HeroesImages.get(deadHero.getName()));
			heroImageView.setFitHeight(229);
			heroImageView.setFitWidth(179);
			displayButton.setMinSize(180, 230);
			displayButton.setMaxSize(180, 230);
			displayButton.setGraphic(heroImageView);

			if (deadHero instanceof Fighter)
				heroType.setText("Type: Fighter");
			else if (deadHero instanceof Medic)
				heroType.setText("Type: Medic");
			else
				heroType.setText("Type: Explorer");
			heroType.setFont(Font.font("Chiller", 30));
			heroType.setTextFill(Color.RED);

			ImageView deadSign = new ImageView(new Image(GridScene.class.getResourceAsStream("/Other/X.png")));
			deadSign.setFitHeight(230);
			deadSign.setFitWidth(180);
			StackPane deadPane = new StackPane(heroImageView, deadSign);
			displayButton.setGraphic(deadPane);
			PickHeroScene.glow(displayButton, Color.RED, 20);

			actPts.setText("Action Points: " + deadHero.getActionsAvailable() + " / " + deadHero.getMaxActions());
			actPts.setFont(Font.font("Chiller", 30));
			actPts.setTextFill(Color.RED);

			supplies.setText("Supplies: " + deadHero.getSupplyInventory().size());
			supplies.setFont(Font.font("Chiller", 30));
			supplies.setTextFill(Color.RED);

			vaccines.setText("Vaccines: " + deadHero.getVaccineInventory().size());
			vaccines.setFont(Font.font("Chiller", 30));
			vaccines.setTextFill(Color.RED);

			attackDmg.setText("Attack Damage: " + deadHero.getAttackDmg());
			attackDmg.setFont(Font.font("Chiller", 30));
			attackDmg.setTextFill(Color.RED);
			return;
		}
		if(hero.isSpecialAction()){
			useSpecial.setStyle("-fx-background-color: transparent; " +
					"-fx-background-radius: 30; " +
					"-fx-border-color: gold; " +
					"-fx-border-width: 2px; " +
					"-fx-border-radius: 30; " +
					"-fx-text-fill: gold; " +
					"-fx-font-size: 18px; " +
					"-fx-font-family: Chiller;" +
					"-fx-padding: 10px 16px;" );
		}
		else{
			useSpecial.setStyle("-fx-background-color: transparent; " +
					"-fx-background-radius: 30; " +
					"-fx-border-color: white; " +
					"-fx-border-width: 2px; " +
					"-fx-border-radius: 30; " +
					"-fx-text-fill: white; " +
					"-fx-font-size: 18px; " +
					"-fx-font-family: Chiller;" +
					"-fx-padding: 10px 16px;" );
		}
		setPogressBar(hero);

		currHero.setText(hero.getName());
		currHero.setFont(Font.font("Arial", 25));
		currHero.setTextFill(Color.WHITE);
		PickHeroScene.glow(currHero, Color.WHITE);

		ImageView heroImageView = new ImageView(HeroesImages.get(hero.getName()));
		heroImageView.setFitHeight(229);
		heroImageView.setFitWidth(179);
		displayButton.setMinSize(180, 230);
		displayButton.setMaxSize(180, 230);
		displayButton.setGraphic(heroImageView);

		if (hero instanceof Fighter)
			heroType.setText("Type: Fighter");
		else if (hero instanceof Medic)
			heroType.setText("Type: Medic");
		else
			heroType.setText("Type: Explorer");
		heroType.setFont(Font.font("Chiller", 30));
		heroType.setTextFill(Color.WHITE);

		if (hero.getCurrentHp() == 0) {
			ImageView deadSign = new ImageView(new Image(GridScene.class.getResourceAsStream("/Other/X.png")));
			deadSign.setFitHeight(230);
			deadSign.setFitWidth(180);
			StackPane deadPane = new StackPane(heroImageView, deadSign);
			displayButton.setGraphic(deadPane);
			PickHeroScene.glow(displayButton, Color.RED, 20);
			currHero.setTextFill(Color.RED);
			PickHeroScene.glow(currHero, Color.RED);
			heroType.setTextFill(Color.RED);
			actPts.setTextFill(Color.RED);
			supplies.setTextFill(Color.RED);
			vaccines.setTextFill(Color.RED);
			attackDmg.setTextFill(Color.RED);
		} else {
			if (hero instanceof Fighter)
				PickHeroScene.glow(displayButton, Color.RED, 20);
			else if (hero instanceof Medic)
				PickHeroScene.glow(displayButton, Color.GREEN, 20);
			else
				PickHeroScene.glow(displayButton, Color.BLUE, 20);
		}
		if(hero.getCurrentHp()>0) {
			actPts.setText("Action Points: " + hero.getActionsAvailable() + " / " + hero.getMaxActions());
			actPts.setFont(Font.font("Chiller", 30));
			actPts.setTextFill(Color.WHITE);

			supplies.setText("Supplies " + hero.getSupplyInventory().size());
			supplies.setFont(Font.font("Chiller", 30));
			supplies.setTextFill(Color.WHITE);


			vaccines.setText("Vaccines: " + hero.getVaccineInventory().size());
			vaccines.setFont(Font.font("Chiller", 30));
			vaccines.setTextFill(Color.WHITE);

			attackDmg.setText("Attack Damage: " + hero.getAttackDmg());
			attackDmg.setFont(Font.font("Chiller", 30));
			attackDmg.setTextFill(Color.WHITE);
		}
	}

	private static Button createButton() {
		Button button = new Button();
		button.setMinSize(Main.buttonSize, Main.buttonSize);
		button.setMaxSize(Main.buttonSize, Main.buttonSize);

		button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		button.setBorder(new Border(
				new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		button.setStyle("-fx-opacity: 0.15;");

		button.setOnMouseClicked(event -> {
			Main.playSound(Main.clickSound);
			if (Game.map[14 - gridPane.getRowIndex(button)][gridPane.getColumnIndex(button)].isVisible() && Game.map[14 - gridPane.getRowIndex(button)][gridPane.getColumnIndex(button)] instanceof CharacterCell) {
				if( heroInAction!=null){
					heroInAction.setTarget(((CharacterCell) Game.map[14 - gridPane.getRowIndex(button)][gridPane.getColumnIndex(button)]).getCharacter());
					try {
						updateMap();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

			}
		});
		// Add hover effect to the button
		button.setOnMouseEntered(event -> {
			button.setBorder(new Border(
					new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			button.setStyle("-fx-opacity: 1;");
		});
		button.setOnMouseExited(event -> {
			button.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
					BorderWidths.DEFAULT)));
			if(!Game.map[14-gridPane.getRowIndex(button)][gridPane.getColumnIndex(button)].isVisible())
				button.setStyle("-fx-opacity: 0.15;");
		});
		return button;
	}

	public static void showError(String message) {
		// Create a label to display the error message
		Label errorLabel = new Label(message);
		errorLabel.setWrapText(true);
		errorLabel.setTextAlignment(TextAlignment.CENTER);
		;
		errorLabel.setAlignment(Pos.CENTER);
		errorLabel.setFont(Font.font("Arial", 40));
		errorLabel.setTextFill(Color.RED);
		errorLabel.setVisible(false);

		gridStackPane.getChildren().add(errorLabel);

		// Create a fade transition to gradually fade out the error message
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), errorLabel);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setOnFinished(event -> {
			// Hide the error message when the fade out animation is finished
			errorLabel.setVisible(false);
		});

		// Create a translate transition to move the error message upwards
		TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), errorLabel);
		translateTransition.setFromY(0);
		translateTransition.setToY(-50); // Adjust the value to control the distance it moves

		// Chain the fade and translate transitions
		fadeTransition.setNode(errorLabel);
		fadeTransition.play();
		translateTransition.play();

		// Show the error message
		errorLabel.setVisible(true);

	}

	public static Scene getScene() {
		heroesBox.setAlignment(Pos.CENTER);
		heroesBox.setSpacing(6);
		// Create the grid pane
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setGridLinesVisible(false);
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				Button button = createButton();
				buttons[row][col] = button;
				gridPane.add(button, col, 14 - row);
				button.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			}
		}
		Button attack = new Button("Attack");
		attack.setStyle("-fx-background-color: transparent; " +
				"-fx-background-radius: 30; " +
				"-fx-border-color: white; " +
				"-fx-border-width: 2px; " +
				"-fx-border-radius: 30; " +
				"-fx-text-fill: white; " +
				"-fx-font-size: 22px; " +
				 "-fx-font-family: Chiller;");
		attack.setPrefSize(100, 40);
		//attack.setStyle("-fx-font-family: Chiller; -fx-font-size: 18px;");
		attack.setOnMouseEntered(e->PickHeroScene.glow(attack,Color.WHITE,20));
		attack.setOnMouseExited(e->PickHeroScene.glow(attack,Color.WHITE,0));

		attack.setOnMouseClicked(e -> {
			Main.playSound(Main.clickSound);
			try {
				heroInAction.attack();
				Main.updateMap();
			} catch (Exception exception) {
				if(heroInAction==null) showError("Hero is dead. Select another one");
				else showError(exception.getMessage());

			}
		});

		Button cure = new Button("Cure");
		cure.setStyle("-fx-background-color: transparent; " +
				"-fx-background-radius: 30; " +
				"-fx-border-color: white; " +
				"-fx-border-width: 2px; " +
				"-fx-border-radius: 30; " +
				"-fx-text-fill: white; " +
				"-fx-font-size: 22px; " +
				"-fx-font-family: Chiller;");
		cure.setPrefSize(100, 40);
		//cure.setStyle("-fx-font-family: Chiller; -fx-font-size: 18px;");
		cure.setOnMouseEntered(e->PickHeroScene.glow(cure,Color.WHITE,20));
		cure.setOnMouseExited(e->PickHeroScene.glow(cure,Color.WHITE,0));

		cure.setOnMouseClicked(e -> {
			Main.playSound(Main.clickSound);
			try {
				heroInAction.cure();
				Main.updateMap();
			} catch (Exception exception) {
				if(heroInAction==null) showError("Hero is dead. Select another one");
				else showError(exception.getMessage());

			}
		});

		useSpecial = new Button("Use Special");
		useSpecial.setStyle("-fx-background-color: transparent; " +
				"-fx-background-radius: 30; " +
				"-fx-border-color: white; " +
				"-fx-border-width: 2px; " +
				"-fx-border-radius: 30; " +
				"-fx-text-fill: white; " +
				"-fx-font-size: 18px; " +
				"-fx-font-family: Chiller;" +
						"-fx-padding: 10px 16px;" );
		useSpecial.setPrefSize(100, 40);
		//useSpecial.setStyle("-fx-font-family: Chiller; -fx-font-size: 18px;");

		useSpecial.setOnMouseEntered(e->PickHeroScene.glow(useSpecial,Color.WHITE,20));
		useSpecial.setOnMouseExited(e->PickHeroScene.glow(useSpecial,Color.WHITE,0));

		useSpecial.setOnMouseClicked(e -> {
			Main.playSound(Main.clickSound);
			try {
				heroInAction.useSpecial();
				Main.updateMap();
			} catch (Exception exception) {
				if(heroInAction==null) showError("Hero is dead. Select another one");
				else showError(exception.getMessage());
			}
		});

		Button endTurn = new Button("End Turn");
		endTurn.setStyle("-fx-background-color: transparent; " +
				"-fx-background-radius: 30; " +
				"-fx-border-color: white; " +
				"-fx-border-width: 2px; " +
				"-fx-border-radius: 30; " +
				"-fx-text-fill: white; " +
				"-fx-font-size: 22px; " +
				"-fx-font-family: Chiller;");
		endTurn.setPrefSize(100, 40);
		//endTurn.setStyle("-fx-font-family: Chiller; -fx-font-size: 18px;");

		endTurn.setOnMouseEntered(e->PickHeroScene.glow(endTurn,Color.WHITE,20));
		endTurn.setOnMouseExited(e->PickHeroScene.glow(endTurn,Color.WHITE,0));

		endTurn.setOnMouseClicked(e -> {
			Main.playSound(Main.clickSound);
			try {
				Game.endTurn();
				Main.updateMap();
			} catch (Exception exception) {
				if(heroInAction==null) showError("Hero is dead. Select another one");
				else showError(exception.getMessage());
			}
		});

		VBox vBoxL = new VBox();
		VBox vBoxR = new VBox();
		vBoxL.setPadding(new Insets(0, 0, 0, 50));
		vBoxR.setPadding(new Insets(0, 130, 0, 0));

		rightGridPane.setVgap(30);
		rightGridPane.setHgap(30);

		vBoxL.getChildren().add(leftGridPane);
		vBoxL.setAlignment(Pos.CENTER);

		vBoxR.getChildren().add(rightGridPane);
		vBoxR.setAlignment(Pos.CENTER);

		rightGridPane.add(attack, 0, 0);
		rightGridPane.add(cure, 0, 1);
		rightGridPane.add(useSpecial, 0, 2);
		rightGridPane.add(endTurn, 0, 3);

		leftGridPane.add(currHero, 0, 0);
		leftGridPane.add(displayButton, 0, 1);
		leftGridPane.add(progressBar, 0, 2);
		leftGridPane.add(heroType, 0, 3);
		leftGridPane.add(attackDmg, 0, 4);
		leftGridPane.add(actPts, 0, 5);
		leftGridPane.add(supplies, 0, 6);
		leftGridPane.add(vaccines, 0, 7);
		leftGridPane.add(heroesBox, 1, 1);
		leftGridPane.setVgap(10);
		leftGridPane.setHgap(10);

		bp.setLeft(vBoxL);
		bp.setRight(vBoxR);
		gridStackPane.getChildren().add(gridPane);

		bp.setOnKeyPressed(event -> {
			try {
				switch (event.getCode()) {
				case W:
					heroInAction.move(Direction.UP);
					break;
				case A:
					heroInAction.move(Direction.LEFT);
					break;
				case D:
					heroInAction.move(Direction.RIGHT);
					break;
				case S:
					heroInAction.move(Direction.DOWN);
					break;
				}
			} catch (Exception exception) {
				if(heroInAction==null) showError("Hero is dead. Select another one");
				else showError(exception.getMessage());
			}
			try {
				Main.updateMap();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if(trapFlag){
				trapFlag = false;
                displayTrap();
			}
		});

		bp.setCenter(gridStackPane);

		// Load the background image
		Image backgroundImage = new Image(GridScene.class.getResourceAsStream("/Backgrounds/bckg_filter6.jpg"));
		// Create an image view for the background image
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setFitWidth(Main.getScreenWidth());
		backgroundImageView.setFitHeight(Main.getScreenHeight());

		// Create the main layout
		StackPane root = new StackPane(backgroundImageView, bp);

		// Create the scene
		return new Scene(root, Main.getScreenWidth(), Main.getScreenHeight()); // Adjust the size as desired
	}

	public static void updateHeroesBox() {
		heroesBox.getChildren().clear();

		for (Hero hero : Game.heroes) {
			Button button = new Button();
			if (hero != heroInAction) {
				button.setPrefSize(50, 50);
				button.setMinSize(50, 50);
				ImageView imageView = new ImageView(Main.HeroesImages.get(hero.getName()));
				imageView.setFitWidth(49);
				imageView.setFitHeight(49);
				button.setGraphic(imageView);
				heroesBox.getChildren().add(button);
			}
			button.setOnMousePressed(e -> {
				heroInAction = hero;
				updateHeroesBox();
				updateDisplayedData(heroInAction);
				try {
					updateMap();
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			});
			button.setOnMouseEntered(e -> {
				updateDisplayedData(hero);
				PickHeroScene.glow(button, Color.GOLD, 10);
			});
			button.setOnMouseExited(e -> {
				updateDisplayedData(heroInAction);
				PickHeroScene.glow(button, Color.GOLD, 0);
			});
		}

	}
}