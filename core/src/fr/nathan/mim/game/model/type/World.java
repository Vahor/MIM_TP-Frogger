package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class World {

    private Frogger frogger;
    private Set<GameElement> elements;
    public static Random SHARED_RANDOM = new Random();

    public Rectangle waterArea;

    public static final Timer TIMER = new Timer();

    public World() {
    }

    public void demoWorld() {
        elements = new HashSet<GameElement>();
        frogger  = new Frogger();
        frogger.getPosition().set(4, .20f);

        for (int i = 1; i < 6; i++) {
            Vehicle vehicle = addVehicle(i,
                    SHARED_RANDOM.nextFloat() * WorldRenderer.CAMERA_WIDTH,
                    SHARED_RANDOM.nextBoolean());

            if (SHARED_RANDOM.nextInt(2) == 0) {
                addVehicle(i,
                        vehicle.getX() - SHARED_RANDOM.nextInt((int) WorldRenderer.CAMERA_WIDTH - 4) - 3, // 3 = la taille max d'un vehicule, pour éviter de superposer
                        vehicle.getFacingDirection() == Direction.RIGHT);
            }
        }

        for (int i = 7; i < 12; i++) {
            Turtle turtle = new Turtle();
            turtle.getPosition().set(0, i + .1f);
            turtle.setFacingDirection(SHARED_RANDOM.nextBoolean() ? Direction.RIGHT : Direction.LEFT);
            elements.add(turtle);
        }

        Fly fly = new Fly();
        fly.getPosition().set(.3f, 12.25f);
        elements.add(fly);

        waterArea = new Rectangle();
        waterArea.add(0, 0);
        waterArea.add(0, 4);
        waterArea.add(13, 0);
        waterArea.add(13, 4);
        waterArea.setLocation(0, 8);
    }

    private Vehicle addVehicle(int row, float initialX, boolean lookingRight) {
        Vehicle vehicle = new Vehicle();
        vehicle.getPosition().set(initialX, row + .1f);
        vehicle.setFacingDirection(lookingRight ? Direction.RIGHT : Direction.LEFT);

        elements.add(vehicle);

        return vehicle;
    }

    public Frogger getFrogger() {
        return frogger;
    }

    public Rectangle getWaterArea() {
        return waterArea;
    }

    public Set<GameElement> getElements() {
        return elements;
    }
}
