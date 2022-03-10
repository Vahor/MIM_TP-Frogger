package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.model.MovingEntity;

public class Vehicle extends MovingEntity {

    public enum VehicleType {
        RED(0,3.5f),
        GRAY(1,2),
        YELLOW(2,1.5f),
        BLUE(3,1.8f),
        GREEN(4,1.3f),
        ;

        private final int id;
        private final float width;
        private static final VehicleType[] values = values();

        VehicleType(int id, float width) {
            this.id    = id;
            this.width = width;
        }

        public int getId() {
            return id;
        }
    }

    private VehicleType vehicleType;

    public Vehicle(VehicleType vehicleType) {

        if (vehicleType == null)
            vehicleType = VehicleType.values[World.SHARED_RANDOM.nextInt(VehicleType.values.length)];
        setVehicleType(vehicleType);
    }

    @Override
    public void whenOutOfBorder() {
        setVehicleType(VehicleType.values[World.SHARED_RANDOM.nextInt(VehicleType.values.length)]);
        super.whenOutOfBorder();
    }

    @Override
    public float getHeight() {
        return 0.8f;
    }

    @Override
    public float getWidth() {
        return vehicleType.width;
    }

    @Override
    public float getSpeed() {
        return 1.1f;
    }

    @Override
    public boolean onCollide() {
        System.out.println("Vehicle.onCollide");
        position.y = 8;
        return true;
    }

    private void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleType=" + vehicleType +
                ", super=" + super.toString() +
                '}';
    }
}
