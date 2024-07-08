package org.example.snakeladder;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Circle coin;
    private int currentPosition;
    private String name;

    private static Board gameBoard = new Board();

    public Player(int tileSize, Color coinColor, String playerName){
        coin = new Circle(tileSize/3);
        coin.setFill(coinColor);
        currentPosition = 0;
        coin.setVisible(false);
        name = playerName;
    }

    public List<Integer> getIntermediatePositions(int start, int end) {
        List<Integer> positions = new ArrayList<>();
        if (start < end) {
            for (int i = start + 1; i <= end; i++) {
                positions.add(i);
            }
        } else {
            for (int i = start - 1; i >= end; i--) {
                positions.add(i);
            }
        }
        return positions;
    }

    public void movePlayer(int diceValue) {
        if (currentPosition + diceValue <= 100) {
            int targetPosition = currentPosition + diceValue;
            coin.setVisible(true);
            List<Integer> positions = getIntermediatePositions(currentPosition, targetPosition);

            SequentialTransition sequentialTransition = new SequentialTransition();

            for (int position : positions) {
                currentPosition = position;
                TranslateTransition transition = translateAnimation(1);
                sequentialTransition.getChildren().add(transition);
            }

            // Handle snakes and ladders
            int newPosition = gameBoard.getNewPosition(currentPosition);
            if (newPosition != currentPosition && newPosition != -1) {
                int oldPosition = currentPosition;
                currentPosition = newPosition;
                TranslateTransition snakeLadderTransition = translateAnimation(Math.abs(newPosition - oldPosition));
                sequentialTransition.getChildren().add(snakeLadderTransition);
            }

            sequentialTransition.play();
        }
        if (currentPosition == 0) {
            coin.setVisible(false);
        }
    }

    private TranslateTransition translateAnimation(int steps) {
        int durationMillis = (steps <= 6) ? 100 * steps : 10 * steps;
        TranslateTransition animate = new TranslateTransition(Duration.millis(durationMillis), coin);
        animate.setToX(gameBoard.getXCoordinate(currentPosition));
        animate.setToY(gameBoard.getYCoordinate(currentPosition));
        animate.setAutoReverse(false);
        return animate;
    }

    boolean isWinner(){
        return currentPosition == 100;
    }

    public void startingPosition(){
        currentPosition =  0;
        coin.setVisible(false);
        movePlayer(0);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Circle getCoin() {
        return coin;
    }

    public String getName() {
        return name;
    }
}
