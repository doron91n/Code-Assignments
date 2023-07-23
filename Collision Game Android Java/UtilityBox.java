package fluffybun.game;

import android.graphics.Color;
import java.util.Random;

import fluffybun.game.Movement.Direction;

public class UtilityBox {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    private static int ID_NUMBER=0;

public static int generateID(){
    System.out.println("$$$$$$$$$$$$$$ UtilityBox generateID: "+ID_NUMBER);
    return ID_NUMBER++;}

    public static double roundUp(double num_to_round) {
        return Math.round(num_to_round * 100000.0) / 100000.0;
    }

    public static double randomNumber(double min, double max) {
        return ( Math.random() * (max+1)) + min;
    }
    // will return a random number between min-max including.
    public static int randomNumber(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max+1) + min;
    }

public static int randomColor(){
    int color=UtilityBox.randomNumber(0,11);
    switch (color){
        case 0:
            return Color.GREEN;
        case 1:
            return Color.BLACK;
        case 2:
            return Color.WHITE;
        case 3:
            return Color.RED;
        case 4:
            return Color.BLUE;
        case 5:
            return Color.CYAN;
        case 6:
            return Color.DKGRAY;
        case 7:
            return Color.GRAY;
        case 8:
            return Color.LTGRAY;
        case 9:
            return Color.MAGENTA;
        case 10:
            return Color.TRANSPARENT;
        case 11:
            return Color.YELLOW;
            default:
                return  Color.GRAY;
    }
}
    public static Direction getDirectionFromAngle(double angle) {
        if ((angle <= 22) && (angle > 337)) {
            return Direction.EAST;
        }
        if ((angle <= 68) && (angle > 22)) {
            return Direction.NORTH_EAST;
        }
        if ((angle <= 113) && (angle > 68)) {
            return Direction.NORTH;
        }
        if ((angle <= 158) && (angle > 113)) {
            return Direction.NORTH_WEST;
        }
        if ((angle <= 203) && (angle > 158)) {
            return Direction.WEST;
        }
        if ((angle <= 248) && (angle > 203)) {
            return Direction.SOUTH_WEST;
        }
        if ((angle <= 293) && (angle > 248)) {
            return Direction.SOUTH;
        }
        if ((angle <= 337) && (angle > 293)) {
            return Direction.SOUTH_EAST;
        }
        return Direction.NOT_SET;
    }
}
