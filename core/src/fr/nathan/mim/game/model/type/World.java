package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class World {

    private final Frogger frogger;
    private final HashMap<Integer, Set<GameElement>> elementsByRow = new HashMap<Integer, Set<GameElement>>(5);
    public static Random SHARED_RANDOM = new Random();

    public World() {
        frogger = new Frogger();
        frogger.getPosition().set(4, .20f);

        for (int i = 0; i < 5; i++) {
            Vehicle vehicle = addVehicle(i,
                    SHARED_RANDOM.nextFloat() * WorldRenderer.CAMERA_WIDTH,
                    null,
                    SHARED_RANDOM.nextBoolean());
            System.out.println("vehicle.getX() = " + vehicle.getX());
            if (SHARED_RANDOM.nextInt(2) == 0) {
                addVehicle(i,
                        vehicle.getX() - SHARED_RANDOM.nextInt((int) WorldRenderer.CAMERA_WIDTH - 3) - 3, // 3 = la taille max d'un vehicule, pour éviter de superposer
                        null,
                        vehicle.getFacingDirection() == Direction.RIGHT);
            }
        }
    }

    private Vehicle addVehicle(int row, float initialX, Vehicle.VehicleType initialVehicleType, boolean lookingRight) {
        if (!elementsByRow.containsKey(row)) {
            elementsByRow.put(row, new HashSet<GameElement>(2, 1));
        }

        Vehicle vehicle = new Vehicle(initialVehicleType);
        vehicle.getPosition().set(initialX, row + 1);
        vehicle.setFacingDirection(lookingRight ? Direction.RIGHT : Direction.LEFT);
        vehicle.getVelocity().set(
                vehicle.getFacingDirection().getMotX() * vehicle.getSpeed(),
                vehicle.getFacingDirection().getMotY() * vehicle.getSpeed()
        );

        elementsByRow.get(row).add(vehicle);

        return vehicle;
    }

    public Set<GameElement> getElementsInRow(int row) {
        return elementsByRow.get(row);
    }

    public Frogger getFrogger() {
        return frogger;
    }

    public HashMap<Integer, Set<GameElement>> getElementsByRow() {
        return elementsByRow;
    }
}
