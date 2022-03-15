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

    private long seed;

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
        SHARED_RANDOM = new Random(seed);

        // Ajout des elements sur les routes
        for (Road road : roads) {
            float offsetX = 0;
            for (int i = 0; i < road.getEntityCount(); i++) {
                GameElement element = null;


                if (road.getType() == Road.Type.WATER) {
                    if (SHARED_RANDOM.nextBoolean()) {
                        element = new Tree(Tree.Type.random());
                    }
                    else {
                        element = new Turtle(turtleConfiguration.getRespawnDelay(), turtleConfiguration.getMaxRideTime());
                    }
                }

                if (road.getType() == Road.Type.ROAD) {
                    element = new Vehicle();
                }


                if (element != null) {
                    element.getPosition().x = road.getDirection() == Direction.LEFT ? offsetX : -offsetX;

                    road.addElement(element);
                }


                offsetX += road.getEntityMinDistance();

            }

        }

        frogger = new Frogger(froggerConfiguration.getJumpDelay(), froggerConfiguration.getJumpDistance(), froggerConfiguration.getAnimationDuration());

        elements.add(new Fly(flyConfiguration.getChangeSpotDelay(), flyConfiguration.getStayOnSportDelay()));

        for (Road road : roads) {
            road.afterInitialization();
        }

    }

    @Override
    public void afterInitialization() {

    }

    public void demoWorld() {

        froggerConfiguration = new FroggerConfiguration(.1f, 1, 7);
        turtleConfiguration  = new TurtleConfiguration(3, 3f);
        flyConfiguration     = new FlyConfiguration(3, 3f);

        roads   = new HashSet<Road>();
        frogger = new Frogger(.1f, 1, WorldRenderer.FRAME_DURATION * 6);
        frogger.getPosition().set(4, .20f);

        for (int i = 1; i < 6; i++) {
            Road road = new Road(1, Direction.RIGHT, 1, 1, i, Road.Type.ROAD);

            if (SHARED_RANDOM.nextInt(2) == 0) {
                road.addElement(new Vehicle());
            }
            roads.add(road);
        }

        for (int i = 7; i < 12; i++) {
            Road road = new Road(1, Direction.RIGHT, 1, 1, i, Road.Type.WATER);

            MovingEntity entity;
            if (SHARED_RANDOM.nextBoolean()) {
                entity = new Turtle(1, 3);
            }
            else {
                entity = new Tree(Tree.Type.random());
            }

            road.addElement(entity);
            roads.add(road);
        }

        seed = 1;


        elements.add(new Fly(flyConfiguration.getChangeSpotDelay(), flyConfiguration.getStayOnSportDelay()));
    }
}
