package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.model.MovingEntity;

public class Vehicle extends MovingEntity {

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
    public CollideResult onCollideWith(MovingEntity entity, float delta) {
        if (!(entity instanceof Frogger))
            return CollideResult.NOTHING;

        return CollideResult.DEAD;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        return true;
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

    public enum Type {
        RED(0, 3.5f),
        GRAY(1, 2),
        YELLOW(2, 1.5f),
        BLUE(3, 1.5f),
        GREEN(4, 1.3f),
        ;

        private static final Type[] values = values();
        private final int id;
        private final float width;

        Type(int id, float width) {
            this.id    = id;
            this.width = width;
        }

        public static Type random() {
            return values[World.SHARED_RANDOM.nextInt(values.length)];
        }

        public int getId() {
            return id;
        }
    }
}
