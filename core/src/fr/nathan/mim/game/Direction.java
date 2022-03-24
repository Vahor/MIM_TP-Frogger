package fr.nathan.mim.game;

public enum Direction {

    LEFT(180, -1, 0),
    RIGHT(0, 1, 0),
    UP(90, 0, 1),
    DOWN(-90, 0, -1);

    private final int rotation;
    private final int motX;
    private final int motY;

    Direction(int rotation, int motX, int motY) {
        this.rotation = rotation;
        this.motX     = motX;
        this.motY     = motY;
    }

    public int getRotation() {
        return rotation;
    }

    public int getMotX() {
        return motX;
    }

    public int getMotY() {
        return motY;
    }

    public Direction opposite() {
        switch (this) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            default:
            case DOWN:
                return UP;
        }
    }
}
