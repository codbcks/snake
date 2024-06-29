package nz.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class SnakeApp extends Application {
    static int stageWidth = 20;
	static int stageHeight = 20;
	static boolean gameOver = false;
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

			// key events
			snakeScene.setOnKeyPressed(key -> {
				if ((key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) && direction != Direction.DOWN) {
					direction = Direction.UP;
				}
				if ((key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) && direction != Direction.UP) {
					direction = Direction.DOWN;
				}
				if ((key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) && direction != Direction.RIGHT) {
					direction = Direction.LEFT;
				}
				if ((key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) && direction != Direction.LEFT) {
					direction = Direction.RIGHT;
				}
			});


			snake.add(new Position(stageWidth / 2, stageHeight / 2));
			snake.add(new Position(stageWidth / 2 + 1, stageHeight / 2));
			snake.add(new Position(stageWidth / 2 + 2, stageHeight / 2));
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
			gc.setFill(Color.RED);
			gc.setFont(new Font("", 50));
			gc.fillText("GAME OVER", 100, 250);
			return;
		}

		// move snake
		for (int i = snake.size() - 1; i >= 1; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}

		switch (direction) {
		case UP:
			snake.get(0).y--;
			if (snake.get(0).y < 0) {
				gameOver = true;
			}
			break;
		case DOWN:
			snake.get(0).y++;
			if (snake.get(0).y >= stageHeight) {
				gameOver = true;
			}
			break;
		case LEFT:
			snake.get(0).x--;
			if (snake.get(0).x < 0) {
				gameOver = true;
			}
			break;
		case RIGHT:
			snake.get(0).x++;
			if (snake.get(0).x >= stageWidth) {
				gameOver = true;
			}
			break;
		}

		// check if food is eaten
		if (food.x == snake.get(0).x && food.y == snake.get(0).y) {
			snake.add(new Position(-1, -1));
			newFood();
		}

		// check if snake collides with itself
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				gameOver = true;
				break;
			}
		}

		// fill background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, stageWidth * blockSize, stageHeight * blockSize);

		// draw snake
		for (Position part : snake) {
			gc.setFill(Color.LIME);
			gc.fillRoundRect(part.x * blockSize, part.y * blockSize, blockSize, blockSize, 10, 10);
		}

		// draw food
		gc.setFill(Color.RED);
		gc.fillOval(food.x * blockSize, food.y * blockSize, blockSize, blockSize);

		// draw score
		gc.setFill(Color.WHITE);
		gc.setFont(new Font("", 30));
		gc.fillText("Score: " + (speed - 5), 10, 30);
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
			speed++;
			break;

		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}