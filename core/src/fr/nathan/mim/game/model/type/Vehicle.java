package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Json;
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
    }

    private Type type = Type.RED;

    @Override
    public void whenOutOfBorder() {
        setVehicleType(Type.values[World.SHARED_RANDOM.nextInt(Type.values.length)]);
        super.whenOutOfBorder();
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
    public boolean onCollide(Frogger frogger, float delta) {
        System.out.println("Vehicle.onCollide");
        return true;
    }

    private void setVehicleType(Type type) {
        this.type = type;
    }

    public Type getVehicleType() {
        return type;
    }

    @Override
    public void afterDeserialization() {
        super.afterDeserialization();
        updateVelocity();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleType=" + type +
                ", super=" + super.toString() +
                '}';
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("vehicleType", type);
    }
}
