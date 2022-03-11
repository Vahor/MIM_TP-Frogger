package fr.nathan.mim.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import fr.nathan.mim.game.model.type.Frogger;

public abstract class GameElement implements Json.Serializable{

    protected final Vector2 position = new Vector2();

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
        return getX() < other.getX() + other.getWidth() &&
                getX() + getWidth() > other.getX() &&
                getY() < other.getY() + other.getHeight() && getY() + getHeight() > other.getY();
    }

    public void update(float delta) {
    }

    public void afterDeserialization() {}

    // return true : end game
    abstract public boolean onCollide(Frogger frogger, float delta);

    @Override
    public String toString() {
        return "GameElement{" +
                "position=" + position +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                '}';
    }

    @Override
    public void write(Json json) {
        json.writeValue("position", position);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        json.readFields(this, jsonData);
    }
}
