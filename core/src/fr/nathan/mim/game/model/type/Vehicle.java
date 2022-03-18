package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.model.MovingEntity;

public class Vehicle extends MovingEntity {

    public enum Type {
        RED(0, 3.5f),
        GRAY(1, 2),
        YELLOW(2, 1.5f),
        BLUE(3, 1.5f),
        GREEN(4, 1.3f),
        ;

        private final int id;
        private final float width;
        private static final Type[] values = values();

        Type(int id, float width) {
            this.id    = id;
            this.width = width;
        }

        public int getId() {
            return id;
        }

        public static Type random() {
            return values[World.SHARED_RANDOM.nextInt(values.length)];
        }
    }

    private Type type;

    public Vehicle(Type type) {
        this.type = type;
    }

    @Override
    public float getHeight() {
        return 0.8f;
    }

    @Override
    public float getWidth() {
        return type.width;
    }

    @Override
    public CollideResult onCollideWith(MovingEntity frogger, float delta) {
        return CollideResult.DEAD;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void afterInitialisation() {
        super.afterInitialisation();
        updateVelocity();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleType=" + type +
                ", super=" + super.toString() +
                '}';
    }
}
