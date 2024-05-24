package views;

import engine.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.characters.Character;
import model.characters.Hero;
import model.characters.Zombie;
import model.collectibles.Collectible;
import model.collectibles.Vaccine;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import java.io.IOException;
import java.util.HashMap;

import static views.GridScene.buttons;
import static views.PickHeroScene.glow;

public class Main extends Application {
	private static final double screenWidth= Screen.getPrimary().getVisualBounds().getWidth();
	private static final double screenHeight=Screen.getPrimary().getVisualBounds().getHeight();
	private static final int GRID_SIZE = 15;

	public static Image Bill = new Image(Main.class.getResourceAsStream("/Heroes/Bill.jpg"));
	public static Image David = new Image(Main.class.getResourceAsStream("/Heroes/David.jpg"));
	public static Image Ellie = new Image(Main.class.getResourceAsStream("/Heroes/Ellie.jpg"));
	public static Image Henry = new Image(Main.class.getResourceAsStream("/Heroes/Henry.jpg"));
	public static Image JoelMiller = new Image(Main.class.getResourceAsStream("/Heroes/JoelMiller.jpg"));
	public static Image RileyAbel = new Image(Main.class.getResourceAsStream("/Heroes/RileyAbel.jpg"));
	public static Image Tess = new Image(Main.class.getResourceAsStream("/Heroes/Tess.jpg"));
	public static Image TommyMiller = new Image(Main.class.getResourceAsStream("/Heroes/TommyMiller.jpg"));

	public static Image BillPNG = new Image(Main.class.getResourceAsStream("/Heroes Png/Bill.png"));
	public static Image DavidPNG = new Image(Main.class.getResourceAsStream("/Heroes Png/David.png"));
	public static Image ElliePNG = new Image(Main.class.getResourceAsStream("/Heroes Png/Ellie.png"));
	public static Image HenryPNG = new Image(Main.class.getResourceAsStream("/Heroes Png/Henry.png"));
	public static Image JoelMillerPNG = new Image(Main.class.getResourceAsStream("/Heroes Png/JoelMiller.png"));
	public static Image RileyAbelPNG = new Image(Main.class.getResourceAsStream("/Heroes Png/RileyAbel.png"));
	public static Image TessPNG = new Image(Main.class.getResourceAsStream("/Heroes Png/Tess.png"));
	public static Image TommyMillerPNG = new Image(Main.class.getResourceAsStream("/Heroes Png/TommyMiller.png"));

	public static Image BillData = new Image(Main.class.getResourceAsStream("/HeroesData/BillData.jpg"));
	public static Image DavidData = new Image(Main.class.getResourceAsStream("/HeroesData/DavidData.jpg"));
	public static Image EllieData = new Image(Main.class.getResourceAsStream("/HeroesData/EllieWilliamsData.jpg"));
	public static Image HenryData = new Image(Main.class.getResourceAsStream("/HeroesData/HenryBurellData.jpg"));
	public static Image JoelMillerData = new Image(Main.class.getResourceAsStream("/HeroesData/JoelMillerData.jpg"));
	public static Image RileyAbelData = new Image(Main.class.getResourceAsStream("/HeroesData/RileyAbelData.jpg"));
	public static Image TessData = new Image(Main.class.getResourceAsStream("/HeroesData/TessData.jpg"));
	public static Image TommyMillerData = new Image(Main.class.getResourceAsStream("/HeroesData/TommyMillerData.jpg"));

	public static Image VaccineImage = new Image(Main.class.getResourceAsStream("/Other/Vaccine.png"));
	public static Image SupplyImage = new Image(Main.class.getResourceAsStream("/Other/Supply.png"));
	public static Image zombieImage = new Image(GridScene.class.getResourceAsStream("/Other/Zombie_resized.png"));

	public static Media trapVideo = new Media(Main.class.getResource("/Other/Trap.mp4").toExternalForm());

	public static Media backgroundMusic = new Media(Main.class.getResource("/Other/SawSoundTrack.mp3").toExternalForm());
	public static Media clickSound = new Media(Main.class.getResource("/Other/ClickSound.mp3").toExternalForm());
	public static Media zombieSound = new Media(Main.class.getResource("/Other/ZombieSound.mp3").toExternalForm());
	public static Media gunSound = new Media(Main.class.getResource("/Other/GunSound.mp3").toExternalForm());
	public static Media healSound =new Media(Main.class.getResource("/Other/heal.mp3").toExternalForm());
	public static Media pickUpSound = new Media(Main.class.getResource("/Other/pickup.mp3").toExternalForm());


	public static HashMap<String, Image> HeroesImages = new HashMap<String, Image>();
	public static HashMap<String, Image> HeroesPNGImages = new HashMap<String, Image>();
	public static HashMap<String, Image> HeroesData = new HashMap<String, Image>();

	public static Stage stage;
	public static final double buttonSize = (screenHeight - 100) / GRID_SIZE;

	public static Hero heroInAction;
	public static Hero deadHero;
	public static boolean trapFlag=false;

	public static Cell[][]mainMap;

	public static void displayMap(Cell[][] map){
		for(int i=0;i<15;i++){
			for(int j=0;j<15;j++){
				if(map[i][j].isVisible()) {
					if (map[i][j] instanceof CharacterCell) {
						Character c = ((CharacterCell) map[i][j]).getCharacter();
						if (c instanceof Hero) {
							System.out.print("h ");
						} else if (c instanceof Zombie) {
							System.out.print("z ");
						} else System.out.print("N ");

					} else if (map[i][j] instanceof CollectibleCell) System.out.print("c ");
					else System.out.print("T ");
				}
				else System.out.print("# ");
			}
			System.out.println();
		}

	}
	public static void displayTrap() {
		MediaPlayer mediaPlayerTrap = new MediaPlayer(Main.trapVideo);
		MediaView mediaView = new MediaView(mediaPlayerTrap);
		mediaView.setFitHeight(Main.getScreenHeight()-50);
		mediaView.setFitWidth(Main.getScreenHeight() -100);
		mediaView.setPreserveRatio(false);
		mediaView.setSmooth(true);
		mediaView.setCache(true);
		GridScene.gridPane.setOpacity(0.25);
		GridScene.gridStackPane.setMaxSize(Main.getScreenHeight() - 100, Main.getScreenHeight() - 100);
		GridScene.gridStackPane.setMinSize(Main.getScreenHeight() - 100, Main.getScreenHeight() - 100);
		GridScene.gridStackPane.getChildren().setAll(mediaView, GridScene.gridPane);
		mediaPlayerTrap.play();
		mediaPlayerTrap.setOnEndOfMedia(() -> {
			GridScene.gridPane.setOpacity(1);
			GridScene.gridStackPane.getChildren().remove(mediaView);
		});
	}

	public static void updateMap() throws IOException {


		if (heroInAction == null) {
			GridScene.showError("Hero is dead. Select another one");
			return;
		}
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				Cell c = Game.map[i][j];
				if (c.isVisible()) {
					buttons[i][j].setStyle("-fx-opacity:1");
					if (c instanceof CollectibleCell) {
						glow(buttons[i][j], Color.BLACK, 10);
						Collectible collectible = ((CollectibleCell) c).getCollectible();
						if (collectible instanceof Vaccine) {
							ImageView imageView = new ImageView(VaccineImage);
							imageView.setFitHeight(buttonSize - 1);
							imageView.setPreserveRatio(true);
							buttons[i][j].setGraphic(imageView);
						} else {
							ImageView imageView = new ImageView(SupplyImage);
							imageView.setFitHeight(buttonSize - 1);
							imageView.setPreserveRatio(true);
							buttons[i][j].setGraphic(imageView);
						}
					}
					else if (c instanceof CharacterCell) {
						Character character = ((CharacterCell) c).getCharacter();
						if (character instanceof Hero ) {
							if(character!=heroInAction.getTarget())
								glow(buttons[i][j], Color.WHITE, 10);

							if (character.getTarget() != null){
								character.setTarget(((CharacterCell) Game.map[character.getTarget().getLocation().x][character.getTarget().getLocation().y]).getCharacter());

							}

							ImageView imageView = new ImageView(HeroesPNGImages.get(character.getName()));
							imageView.setFitHeight(buttonSize - 2);
							imageView.setPreserveRatio(true);
							buttons[i][j].setGraphic(imageView);
							buttons[i][j].setMinSize(buttonSize,buttonSize);
							buttons[i][j].setMaxSize(buttonSize,buttonSize);
							if (character == heroInAction ){
									glow(buttons[i][j], Color.GOLD, 15);
									if(character.getTarget()!=null){
										int x = character.getTarget().getLocation().x;
										int y = character.getTarget().getLocation().y;
										glow(buttons[x][y],Color.RED,15);
									}

							}

						} else if (character instanceof Zombie) {
							if(heroInAction!=null &&  character!=heroInAction.getTarget())
								glow(buttons[i][j], Color.BLACK, 10);
							ImageView imageView = new ImageView(zombieImage);
							imageView.setFitHeight(buttonSize - 2);
							imageView.setPreserveRatio(true);
							buttons[i][j].setGraphic(imageView);
						} else{
							glow(buttons[i][j], Color.BLACK, 10);
							buttons[i][j].setGraphic(null);
						}

					}
				}
				else {
					buttons[i][j].setStyle("-fx-opacity:0.15");
					buttons[i][j].setGraphic(null);
				}
			}
		}
		if (Game.checkWin()) {
			Main.stage.setScene(YouWonScene.getScene());
			return;
		}
		if (Game.checkGameOver()) {
			Main.stage.setScene(GameOverScene.getScene());
		}


		GridScene.updateDisplayedData(heroInAction);
		GridScene.updateHeroesBox();
		if (heroInAction.getCurrentHp() == 0){
			deadHero=heroInAction;
			Main.heroInAction = null;
		}

	}

	public static void loadImages(){
		HeroesImages.put("Bill", Bill);
		HeroesImages.put("David", David);
		HeroesImages.put("Ellie Williams", Ellie);
		HeroesImages.put("Henry Burell", Henry);
		HeroesImages.put("Joel Miller", JoelMiller);
		HeroesImages.put("Riley Abel", RileyAbel);
		HeroesImages.put("Tess", Tess);
		HeroesImages.put("Tommy Miller", TommyMiller);

		HeroesPNGImages.put("Bill", BillPNG);
		HeroesPNGImages.put("David", DavidPNG);
		HeroesPNGImages.put("Ellie Williams", ElliePNG);
		HeroesPNGImages.put("Henry Burell", HenryPNG);
		HeroesPNGImages.put("Joel Miller", JoelMillerPNG);
		HeroesPNGImages.put("Riley Abel", RileyAbelPNG);
		HeroesPNGImages.put("Tess", TessPNG);
		HeroesPNGImages.put("Tommy Miller", TommyMillerPNG);

		HeroesData.put("Bill", BillData);
		HeroesData.put("David", DavidData);
		HeroesData.put("Ellie Williams", EllieData);
		HeroesData.put("Henry Burell", HenryData);
		HeroesData.put("Joel Miller", JoelMillerData);
		HeroesData.put("Riley Abel", RileyAbelData);
		HeroesData.put("Tess", TessData);
		HeroesData.put("Tommy Miller", TommyMillerData);


	}
	public void start(Stage primaryStage)  {



		loadImages();
		stage = primaryStage;
		primaryStage.setResizable(false);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Last of Us - Legacy");
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Backgrounds/icon.jpg")));

		Scene loadingScene = LoadingScene.getScene();
		Scene pickHero = PickHeroScene.getScene();

		primaryStage.setScene(loadingScene);
		LoadingScene.startLoading(primaryStage, pickHero);


		primaryStage.show();
	}

	public static void playSound(Media m) {
		MediaPlayer mediaPlayer = new MediaPlayer(m);
		mediaPlayer.play();
	}

	public static double getScreenWidth() {
		return screenWidth;
	}

	public static double getScreenHeight() {
		return screenHeight;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
