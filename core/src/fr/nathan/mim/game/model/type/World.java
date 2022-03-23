package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.*;
import fr.nathan.mim.game.model.GameElement;

import java.util.*;

public class World implements Configurable {

    public static Random SHARED_RANDOM = new Random();
    public static final Timer TIMER = new Timer();

    private long seed = -1;
    private boolean debug = false;

    private float width;
    private float height;

    private transient Frogger frogger;

    private FroggerConfiguration froggerConfiguration;
    private TurtleConfiguration turtleConfiguration;
    private FlyConfiguration flyConfiguration;
    private RefugeFlyConfiguration refugeFlyConfiguration;
    private RefugeConfiguration refugeConfiguration;

    private Set<Road> roads;
    private final transient Set<GameElement> elements = new HashSet<GameElement>();

    private transient boolean pause = false;
    private transient boolean cheat = false;

    private float speedBoostPerScore;

    private float maxTime;
    private transient Float currentTime;

    private transient long score;

    private transient float successMessageTime;

    private int maxLives;
    private transient int remainingLives;

    public static World instance;
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

    public void setPause(boolean pause) {
        this.pause = pause;
    }
    public boolean isPause() {
        return pause;
    }

    public boolean isCheat() {
        return cheat;
    }
    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }
    public Float getCurrentTime() {
        return currentTime;
    }

    public void setScore(long score) {
        this.score = score;
    }
    public long getScore() {
        return score;
    }

    public float getMoveSpeedBoost() {
        return score * speedBoostPerScore;
    }

    public int getMaxLives() {
        return maxLives;
    }
    public int getRemainingLives() {
        return remainingLives;
    }
    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public void setSuccessMessageTime(float successMessageTime) {
        this.successMessageTime = successMessageTime;
    }

    public float getSuccessMessageTime() {
        return successMessageTime;
    }

    public List<GameElement> generateElement(Road road) {
        List<GameElement> elements = new ArrayList<GameElement>(3);
        if (road.getType() == Road.Type.TURTLE) {
            int diff = turtleConfiguration.getMaxGroupSize() - turtleConfiguration.getMinGroupSize();
            int groupSize = turtleConfiguration.getMinGroupSize();
            if (diff != 0)
                groupSize += SHARED_RANDOM.nextInt(diff);

            for (int i = 0; i < groupSize; i++) {
                elements.add(new Turtle(turtleConfiguration));
            }
        }
        else if (road.getType() == Road.Type.LOG) {
            elements.add(new Tree(Tree.Type.random()));
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


        instance = this;
        init();

    }

    public void init() {
        elements.clear();

        for (Road road : roads) {
            road.getElements().clear();
            for (GameElement element : generateElement(road)) {
                road.addElement(element);
                element.afterInitialisation();
            }
        }

        frogger = new Frogger(froggerConfiguration);
        frogger.getPosition().set(froggerConfiguration.getStartingPosition());

        elements.add(new Fly(flyConfiguration));
        elements.add(new RefugeFly(refugeFlyConfiguration, refugeConfiguration));

        for (GameElement element : elements) {
            element.afterInitialisation();
        }

        for (Vector2 position : refugeConfiguration.getPositions()) {
            Refuge refuge = new Refuge();
            refuge.getPosition().set(position);
            elements.add(refuge);
        }

        remainingLives = maxLives;

        currentTime = maxTime + 1;

    }

    public void demoWorld() {

        instance = this;

        debug              = true;
        width              = 8;
        height             = 13.5f;
        maxTime            = 60;
        speedBoostPerScore = .005f;

        maxLives = 5;

        froggerConfiguration   = new FroggerConfiguration(
                .05f,
                1,
                new Vector2(width / 2 - .25f, .15f));
        turtleConfiguration    = new TurtleConfiguration(3, 3f, 1, 3);
        flyConfiguration       = new FlyConfiguration(
                3,
                .4f
        );
        refugeFlyConfiguration = new RefugeFlyConfiguration(1);

        refugeConfiguration = new RefugeConfiguration(new ArrayList<Vector2>(Arrays.asList(
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
                    i % 2 == 0 ? Direction.LEFT : Direction.RIGHT,
                    SHARED_RANDOM.nextInt(3) + 2,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(2) + 3,
                    i + .1f,
                    Road.Type.ROAD);
            roads.add(road);
        }

        for (int i = 7; i < 12; i++) {
            Road road = new Road(SHARED_RANDOM.nextFloat() + .5f,
                    i % 2 == 0 ? Direction.LEFT : Direction.RIGHT,
                    SHARED_RANDOM.nextInt(3) + 2,
                    SHARED_RANDOM.nextInt(3) + 1,
                    SHARED_RANDOM.nextInt(2) + 3,
                    i + .1f,
                    i % 2 == 0 ? Road.Type.TURTLE : Road.Type.LOG);
            roads.add(road);

        }

        seed = 0;

        for (Road road : roads) {
            for (GameElement element : generateElement(road)) {
                road.addElement(element);
            }
        }


        elements.add(new Fly(flyConfiguration));
        elements.add(new RefugeFly(refugeFlyConfiguration, refugeConfiguration));

        init();
    }
}
