package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.config.FlyConfiguration;
import fr.nathan.mim.game.config.FroggerConfiguration;
import fr.nathan.mim.game.config.TurtleConfiguration;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;

import java.util.*;

public class World implements Configurable {

    private long seed = -1;

    private transient Frogger frogger;

    private FroggerConfiguration froggerConfiguration;
    private TurtleConfiguration turtleConfiguration;
    private FlyConfiguration flyConfiguration;

    private Set<Road> roads;
    private final transient Set<GameElement> elements = new HashSet<GameElement>();

    public static Random SHARED_RANDOM = new Random();
    public static final Timer TIMER = new Timer();

    public Frogger getFrogger() {
        return frogger;
    }

    public Set<Road> getRoads() {
        return roads;
    }

    public Set<GameElement> getElements() {
        return elements;
    }

    public List<GameElement> generateElement(Road road) {
        List<GameElement> elements = new ArrayList<GameElement>(3);
        if (road.getType() == Road.Type.WATER) {
            if (SHARED_RANDOM.nextBoolean()) {
                elements.add(new Tree(Tree.Type.random()));
            }
            else {
                int diff = turtleConfiguration.getMaxGroupSize() - turtleConfiguration.getMinGroupSize();
                int groupSize = turtleConfiguration.getMinGroupSize();
                if(diff != 0)
                    groupSize += SHARED_RANDOM.nextInt(diff);

                for (int i = 0; i < groupSize; i++) {
                    elements.add(new Turtle(turtleConfiguration));
                }
            }
        }

        else if (road.getType() == Road.Type.ROAD) {
            Vehicle vehicle = new Vehicle();
            vehicle.setType(Vehicle.Type.random());
            elements.add(vehicle);
        }

        return elements;
    }

    @Override
    public void afterInitialisation() {
        if (seed != 0)
            SHARED_RANDOM = new Random(seed);
        else
            SHARED_RANDOM = new Random();

        frogger = new Frogger(froggerConfiguration);
        frogger.getPosition().set(
                (WorldRenderer.WORLD_WIDTH - frogger.getWidth()) / 2,
                .15f
        );

        Fly fly = new Fly(flyConfiguration);
        fly.getPosition().set(fly.getNextPosition());
        elements.add(fly);

    }

    public void demoWorld() {

        froggerConfiguration = new FroggerConfiguration(.1f, 1);
        turtleConfiguration  = new TurtleConfiguration(3, 3f, 1, 3);
        flyConfiguration     = new FlyConfiguration(3, 3f, new ArrayList<Vector2>(Arrays.asList(
                new Vector2(0, 12),
                new Vector2(2, 12),
                new Vector2(4, 12),
                new Vector2(6, 12),
                new Vector2(8, 12)
        )));

        roads = new HashSet<Road>();

        frogger = new Frogger(froggerConfiguration);
        frogger.getPosition().set(4, .20f);

        SHARED_RANDOM = new Random();

        for (int i = 1; i < 6; i++) {
            Road road = new Road(SHARED_RANDOM.nextFloat() + .5f,
                    SHARED_RANDOM.nextBoolean() ? Direction.RIGHT : Direction.LEFT,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(2) + 3,
                    i,
                    Road.Type.ROAD);

            if (SHARED_RANDOM.nextInt(2) == 0) {
                road.addElement(new Vehicle());
            }
            roads.add(road);
        }

        for (int i = 7; i < 12; i++) {
            Road road = new Road(SHARED_RANDOM.nextFloat() + .5f,
                    SHARED_RANDOM.nextBoolean() ? Direction.RIGHT : Direction.LEFT,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(2) + 3,
                    i,
                    Road.Type.WATER);

            MovingEntity entity;
            if (SHARED_RANDOM.nextBoolean()) {
                entity = new Turtle(turtleConfiguration);
            }
            else {
                entity = new Tree(Tree.Type.random());
            }

            road.addElement(entity);
            roads.add(road);
        }

        seed = 0;


        elements.add(new Fly(flyConfiguration));
    }
}
