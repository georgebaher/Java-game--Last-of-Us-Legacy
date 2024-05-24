package views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingScene {

	private static final int LOADING_DURATION = 500; // Duration in seconds
	private static ProgressBar progressBar;

	public static void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			sleep(seconds);
		}
	}

	public static Scene getScene() {
		// Create the progress bar
		progressBar = new ProgressBar();
		progressBar.setPrefWidth(500); // Adjust the width as desired
		progressBar.setPrefHeight(15); // Adjust the height as desired
		progressBar.setStyle("-fx-accent: #FF0000;");
		// Create a layout for the loading screen
		VBox root = new VBox(progressBar);
		root.setAlignment(Pos.CENTER);

		// Load the background image
		Image backgroundImage = new Image(LoadingScene.class.getResourceAsStream("/Backgrounds/loading.jpg"));
		// Set the background image
		Background background = new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(Main.getScreenWidth(), Main.getScreenHeight(), false, false, false, true)));

		root.setBackground(background);
		// Create the scene and return
		return new Scene(root, Main.getScreenWidth(), Main.getScreenHeight());
	}

	public static void startLoading(Stage primaryStage, Scene scene) {

		double durationPerFrame = LOADING_DURATION / progressBar.getWidth();

		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, event -> {
			double progress = progressBar.getProgress() + (1.0 / progressBar.getWidth());
			progressBar.setProgress(progress);
		}), new KeyFrame(Duration.seconds(durationPerFrame)));

		// Create a new thread to load the GridView scene in the background
		Thread thread = new Thread(() -> {
			sleep(2);
			MediaPlayer mediaPlayer = new MediaPlayer(Main.backgroundMusic);
			// Set the starting time to 30 seconds
			mediaPlayer.setOnReady(() -> {
				mediaPlayer.seek(mediaPlayer.getMedia().getDuration()
						.multiply(10.0 / mediaPlayer.getTotalDuration().toSeconds()));
			});

			// Start playing the music
			mediaPlayer.setVolume(0.2);
			mediaPlayer.play();

			Platform.runLater(() -> {
				primaryStage.setScene(scene);
				primaryStage.setMaximized(true);
			});
		});
		// thread.setDaemon(true); // Set the thread to run in the background
		thread.start();

		timeline.setOnFinished(event -> {
			// Stop the thread if it is still running
			if (thread.isAlive()) {
				thread.interrupt();
			}
		});
		timeline.play();
	}
}