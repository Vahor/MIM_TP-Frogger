package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.config.FlyConfiguration;
import fr.nathan.mim.game.config.FroggerConfiguration;
import fr.nathan.mim.game.config.TurtleConfiguration;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;

import java.util.*;

public class World implements Configurable {

    private long seed = -1;
    private boolean debug = false;

    private float width;
    private float height;

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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
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
                if (diff != 0)
                    groupSize += SHARED_RANDOM.nextInt(diff);

                for (int i = 0; i < groupSize; i++) {
                    elements.add(new Turtle(turtleConfiguration));
                }
            }
        }

        else if (road.getType() == Road.Type.ROAD) {
            elements.add(new Vehicle(Vehicle.Type.random()));
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
        frogger.getPosition().set(froggerConfiguration.getStartingPosition());

        Fly fly = new Fly(flyConfiguration);
        fly.getPosition().set(fly.getNextPosition());
        elements.add(fly);

    }

    public void demoWorld() {

        debug  = true;
        width  = 8;
        height = 13.5f;

        froggerConfiguration = new FroggerConfiguration(.1f, 1, new Vector2(
                width / 2 - .25f,
                .15f));
        turtleConfiguration  = new TurtleConfiguration(3, 3f, 1, 3);
        flyConfiguration     = new FlyConfiguration(1, new ArrayList<Vector2>(Arrays.asList(
                new Vector2(0.3f, 12.25f),
                new Vector2(2, 12.25f),
                new Vector2(3.8f, 12.25f),
                new Vector2(5.6f, 12.25f),
                new Vector2(7.3f, 12.25f)
        )));

        roads = new HashSet<Road>();

        frogger = new Frogger(froggerConfiguration);
        frogger.getPosition().set(4, .20f);

        SHARED_RANDOM = new Random();

        for (int i = 1; i < 6; i++) {
            Road road = new Road(SHARED_RANDOM.nextFloat() + .5f,
                    SHARED_RANDOM.nextBoolean() ? Direction.RIGHT : Direction.LEFT,
                    SHARED_RANDOM.nextInt(3) + 2,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(2) + 3,
                    i + .1f,
                    Road.Type.ROAD);

            if (SHARED_RANDOM.nextInt(2) == 0) {
                road.addElement(new Vehicle(Vehicle.Type.random()));
            }
            roads.add(road);
        }

        for (int i = 7; i < 12; i++) {
            Road road = new Road(SHARED_RANDOM.nextFloat() + .5f,
                    SHARED_RANDOM.nextBoolean() ? Direction.RIGHT : Direction.LEFT,
                    SHARED_RANDOM.nextInt(3) + 2,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(2) + 3,
                    i + .1f,
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
