package nz.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SnakeApp extends Application {
    static int stageWidth = 20;
	static int stageHeight = 20;
	static boolean gameOver = false;
	static int blockSize = 25;
	static List<Position> snake = new ArrayList<>();
	static Direction direction = Direction.LEFT;
	static Random random = new Random();

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public static class Position {
		int x;
		int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public void start(Stage primaryStage) {
		try {

			VBox root = new VBox();
			Canvas c = new Canvas(stageWidth * blockSize, stageHeight * blockSize);
			root.getChildren().add(c);
			Scene snakeScene = new Scene(root, stageWidth * blockSize, stageHeight * blockSize);


			// snake.add(new Position(stageWidth / 2, stageHeight / 2));
			// snake.add(new Position(stageWidth / 2 + 1, stageHeight / 2));
			// snake.add(new Position(stageWidth / 2 + 2, stageHeight / 2));
			primaryStage.setTitle("Snake");
			primaryStage.setScene(snakeScene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}