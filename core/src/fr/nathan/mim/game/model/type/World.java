package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.config.FlyConfiguration;
import fr.nathan.mim.game.config.FroggerConfiguration;
import fr.nathan.mim.game.config.TurtleConfiguration;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    @Override
    public void afterDeserialization() {
        if (seed != 0)
            SHARED_RANDOM = new Random(seed);
        else
            SHARED_RANDOM = new Random();

        // Ajout des elements sur les routes
        for (Road road : roads) {
            float previousOffsetX = 0;
            float offsetX = 0;
            for (int i = 0; i < road.getEntityCount(); i++) {
                GameElement element = null;


                if (road.getType() == Road.Type.WATER) {
                    if (SHARED_RANDOM.nextBoolean()) {
                        element = new Tree(Tree.Type.random());
                    }
                    else {
                        element = new Turtle(turtleConfiguration);
                    }
                }

                if (road.getType() == Road.Type.ROAD) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setType(Vehicle.Type.random());
                    element = vehicle;
                }


                if (element != null) {
                    offsetX += element.getWidth();
                    element.getPosition().x = road.getDirection() == Direction.LEFT ? offsetX : -offsetX;
                    element.setOffsetXToNextEntity(offsetX - previousOffsetX);

                    road.addElement(element);
                }


                // todo on suppose que la différence n'est pas positive, faire le test dans le constructeur de Road
                // min <= max
                previousOffsetX = offsetX;
                offsetX += (SHARED_RANDOM.nextFloat() * (road.getEntityMaxDistance() - road.getEntityMinDistance())) + road.getEntityMinDistance();

            }

        }

        frogger = new Frogger(froggerConfiguration);
        frogger.getPosition().set(
                (WorldRenderer.WORLD_WIDTH - frogger.getWidth()) / 2,
                .15f
        );

        elements.add(new Fly(flyConfiguration));

    }

    public void demoWorld() {

        froggerConfiguration = new FroggerConfiguration(.1f, 1);
        turtleConfiguration  = new TurtleConfiguration(3, 3f);
        flyConfiguration     = new FlyConfiguration(3, 3f);

        roads   = new HashSet<Road>();
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
