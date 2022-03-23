package fr.nathan.mim.game.model;

import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.model.type.Road;

public abstract class GameElement implements Configurable, Collidable {

    protected transient final Vector2 position = new Vector2(0, 0);

    private transient Road road;

    abstract public float getWidth();
    abstract public float getHeight();

    public Direction getDirection() {
        return road.getDirection();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Road getRoad() {
        return road;
    }
    public void setRoad(Road road) {
        this.road = road;
    }

    public float getX() {
        return position.x;
    }
    public float getY() {
        return position.y;
    }

    public boolean isVisible() {
        return true;
    }

    public float getRotationOffset() { // todo faire la rotation sur le png directement pour le frogger ?
        return 0;
    }

    public float getYWithRoadOffset() {
        return getY() + road.getOffsetY();
    }

    public boolean collideWith(MovingEntity other) {
        return getX() < other.getX() + other.getWidth() &&
                getX() + getWidth() > other.getX() &&
                getYWithRoadOffset() < other.getYWithRoadOffset() + other.getHeight() && getYWithRoadOffset() + getHeight() > other.getYWithRoadOffset();
    }

    public void update(float delta)   {}

    public void afterInitialisation() {}

    // return DEAD : end game
    public CollideResult onCollideWith(MovingEntity frogger, float delta) {return CollideResult.NOTHING;}

    public CollideResult handleCollision(MovingEntity element, float delta) {
        if (collideWith(element)) {
            return onCollideWith(element, delta);
        }
        return CollideResult.MISS;
    }

    @Override
    public String toString() {
        return "GameElement{" +
                "position=" + position +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                ", road=" + getRoad() +
                '}';
    }

}
