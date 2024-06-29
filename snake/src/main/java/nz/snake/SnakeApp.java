package nz.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class SnakeApp extends Application {
    static int stageWidth = 20;
	static int stageHeight = 20;
	static boolean gameOver = true;
	static int blockSize = 25;
	static List<Position> snake = new ArrayList<>();
	static Direction direction = Direction.LEFT;
	static Random random = new Random();
	static Position food = new Position(0, 0);
	static int speed = 5;


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
			GraphicsContext gc = c.getGraphicsContext2D();
			root.getChildren().add(c);

			new AnimationTimer() {
				long prevTick = 0;

				public void handle(long now) {
					if (prevTick == 0) {
						prevTick = now;
						tick(gc);
						return;
					}

					if (now - prevTick > 1000000000 / speed) {
						prevTick = now;
						tick(gc);
					}
				}

			}.start();

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

	public static void tick(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
    	gc.fillRect(0, 0, stageWidth * blockSize, stageHeight * blockSize);

		if (gameOver) {
			gc.fill();
			gc.setFill(Color.RED);
			gc.setFont(new Font("", 50));
			gc.fillText("GAME OVER", 100, 250);
			return;
		}

		//implement rest of game
	}

	public static void newFood() {
		start: while (true) {
			food.x = random.nextInt(stageWidth);
        	food.y = random.nextInt(stageHeight);

			for (Position part : snake) {
				if (part.x == food.x && part.y == food.y) {
					continue start;
				}
			}
			Color foodcolor = Color.RED;
			speed++;
			break;

		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}