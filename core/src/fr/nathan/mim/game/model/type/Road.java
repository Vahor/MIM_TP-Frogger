package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.model.GameElement;

import java.util.HashSet;
import java.util.Set;

public class Road implements Configurable {

    public enum Type {
        ROAD, WATER, UNKNOWN
    }

    private transient final Set<GameElement> elements = new HashSet<GameElement>(4);

    private float moveSpeed;
    private Direction direction;
    private Type type;

    private int entityCount;
    private float entityMinDistance;

    private float offsetY;

    public Road(float moveSpeed, Direction direction, int entityCount, int entityMinDistance, float offsetY, Type type) {
        this.moveSpeed         = moveSpeed;
        this.direction         = direction;
        this.entityCount       = entityCount;
        this.entityMinDistance = entityMinDistance;
        this.offsetY           = offsetY;
        this.type              = type;
    }

    // Parser
    public Road() {
    }

    public void addElement(GameElement element) {
        element.setRoad(this);
        elements.add(element);
    }

    public Set<GameElement> getElements() {
        return elements;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getEntityCount() {
        return entityCount;
    }

    public float getEntityMinDistance() {
        return entityMinDistance;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public Type getType() {
        return type;
    }

    public float getHeight() {
        return 1;
    }

    public boolean collideWith(GameElement other) {
        return offsetY < other.getYWithRoad() + other.getHeight() && offsetY + getHeight() > other.getYWithRoad();
    }

    @Override
    public void afterDeserialization() {
        for (GameElement element : elements) {
            element.afterDeserialization();
        }
    }

    @Override
    public void afterInitialization() {
        for (GameElement element : elements) {
            element.afterInitialization();
        }
    }

    @Override
    public String toString() {
        return "Road{" +
                "moveSpeed=" + moveSpeed +
                ", direction=" + direction +
                ", type=" + type +
                ", entityCount=" + entityCount +
                ", entityMinDistance=" + entityMinDistance +
                ", offsetY=" + offsetY +
                '}';
    }
}
