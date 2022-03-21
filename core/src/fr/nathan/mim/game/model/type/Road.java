package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.model.GameElement;

import java.util.HashSet;
import java.util.Set;

public class Road implements Configurable {

    public enum Type {
        ROAD(false),
        LOG(true),
        TURTLE(true),
        UNKNOWN(false);
        final boolean dangerous;

        Type(boolean dangerous) {
            this.dangerous = dangerous;
        }

        public boolean isDangerous() {
            return dangerous;
        }
    }

    private transient final Set<GameElement> elements = new HashSet<GameElement>(4);

    private float moveSpeed;
    private Direction direction;
    private Type type;

    private int entityCount;
    private float entityMinDistance;
    private float entityMaxDistance;

    private float offsetY;

    public Road(float moveSpeed, Direction direction, int entityCount, int entityMinDistance, int entityMaxDistance, float offsetY, Type type) {
        this.moveSpeed         = moveSpeed;
        this.direction         = direction;
        this.entityCount       = entityCount;
        this.entityMinDistance = entityMinDistance;
        this.entityMaxDistance = entityMaxDistance;
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

    public void removeElement(GameElement element) {
        elements.remove(element);
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

    public float getEntityMaxDistance() {
        return entityMaxDistance;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getRandomOffsetX() {
        return (World.SHARED_RANDOM.nextFloat() * (getEntityMaxDistance() - getEntityMinDistance())) + getEntityMinDistance();
    }

    /**
     * Retourne l'élement avec le plus petit X
     * @return Element le plus proche de la bordure gauche
     */
    public GameElement getFirstElement() {
        GameElement result = null;
        for (GameElement element : elements) {
            if (result == null)
                result = element;
            else if (result.getX() > element.getX())
                result = element;
        }
        return result;
    }

    /**
     *
     * Retourne l'élement avec le plus grand X
     * @return Element le plus proche de la bordure droite
     */
    public GameElement getLastElement() {
        GameElement result = null;
        for (GameElement element : elements) {
            if (result == null)
                result = element;
            else if (result.getX() < element.getX())
                result = element;
        }
        return result;
    }

    public Type getType() {
        return type;
    }

    public float getHeight() {
        return 1;
    }

    public boolean collideWith(GameElement other) {
        return offsetY < other.getYWithRoadOffset() + other.getHeight() && offsetY + getHeight() > other.getYWithRoadOffset();
    }

    @Override
    public void afterInitialisation() {

        if (entityMinDistance > entityMaxDistance) {
            throw new RuntimeException("Road init : entityMinDistance > entityMaxDistance");
        }

        for (GameElement element : elements) {
            element.afterInitialisation();
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
                ", entityMaxDistance=" + entityMaxDistance +
                ", offsetY=" + offsetY +
                '}';
    }
}
