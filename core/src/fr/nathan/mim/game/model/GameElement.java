package fr.nathan.mim.game.model;

import com.badlogic.gdx.math.Vector2;

public abstract class GameElement {

    protected final Vector2 position = new Vector2();

    abstract public float getSpeed();

    abstract public float getWidth();
    abstract public float getHeight();

    public Vector2 getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public boolean collideWith(GameElement other) {
        System.out.println(this);
        System.out.println(other);
        return getX() < other.getX() + other.getWidth() &&
                getX() + getWidth() > other.getX() &&
                getY() < other.getY() + other.getHeight() && getY() + getHeight() > other.getY();
    }

    public void update(float delta) {
    }

    // return true : end game
    abstract public boolean onCollide();

    @Override
    public String toString() {
        return "GameElement{" +
                "position=" + position +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                ", speed=" + getSpeed() +
                '}';
    }
}
