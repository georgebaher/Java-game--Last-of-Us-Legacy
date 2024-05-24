package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import views.GridScene;
import javafx.scene.effect.DropShadow;
import views.Main;

public class Medic extends Hero {
	public static String getGrammaticallyCorrect(String name){
		if(name.endsWith("s"))
			return name + "' health restored";
		else
			return name + "'s health restored";
	}
	public Medic(String name, int maxHp, int attackDamage, int maxActions) {
		super(name, maxHp, attackDamage, maxActions);
	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		if (getTarget() instanceof Zombie)
			throw new InvalidTargetException("You can only cure fellow heroes.");
		if(getTarget() ==null)
			throw new InvalidTargetException("You need to pick a hero to heal first.");
		if (!checkDistance())
			throw new InvalidTargetException("You are only able to heal adjacent targets.");
		super.useSpecial();
		getTarget().setCurrentHp(getTarget().getMaxHp());
		Button healedHero= GridScene.buttons[getTarget().getLocation().x][getTarget().getLocation().y];

		Label errorLabel = new Label(getGrammaticallyCorrect(getTarget().getName()));
		errorLabel.setWrapText(true);
		errorLabel.setTextAlignment(TextAlignment.CENTER);
		errorLabel.setAlignment(Pos.CENTER);
		errorLabel.setFont(Font.font("Arial", 40));
		errorLabel.setTextFill(Color.GREEN);
		errorLabel.setVisible(false);

		GridScene.gridStackPane.getChildren().add(errorLabel);

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
		Main.playSound(Main.healSound);


		setSpecialAction(false);

	}
}
