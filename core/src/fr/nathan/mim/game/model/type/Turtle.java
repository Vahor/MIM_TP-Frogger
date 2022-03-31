package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.config.TurtleConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Turtle extends MovingEntity {

    private final float respawnDelay;
//    private final float maxRideTime;

    private transient State state = State.MOVE;

    private transient float stateTime = 0;
//    private transient float currentRideTime = 0;

    public Turtle(TurtleConfiguration turtleConfiguration) {
        this.respawnDelay = turtleConfiguration.getRespawnDelay();
//        this.maxRideTime  = turtleConfiguration.getMaxRideTime();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        stateTime  = 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        return true;
    }

    public float getStateTime() {
        return stateTime;
    }

    @Override
    public float getWidth() {
        return 0.7f;
    }

    @Override
    public float getHeight() {
        return 0.7f;
    }

    @Override
    public CollideResult onCollideWith(MovingEntity entity, float delta) {

        if (!(entity instanceof Frogger))
            return CollideResult.NOTHING;

        if (state != State.MOVE) return CollideResult.NOTHING;
//
//        currentRideTime += delta;
//
//        if (currentRideTime > maxRideTime) {
//            setState(State.SINK);
//            getVelocity().set(0, 0);
//            entity.getVelocity().set(0, 0);
//            currentRideTime = 0;
//        }

//         todo reset currentRideTime lorsqu'on n'est plus dessus

        return CollideResult.RIDE;
    }

    @Override
    public void afterInitialisation() {
        super.afterInitialisation();
        onSpawnEnd();
    }

    public void onSinkEnd() {
        setState(State.DEAD);
        World.TIMER.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setState(State.SPAWN);
            }
        }, respawnDelay);
    }

    public void onSpawnEnd() {
        setState(State.MOVE);
        updateVelocity();
    }

    @Override
    public String toString() {
        return "Turtle{" +
                "state=" + state +
                ", stateTime=" + stateTime +
                ", respawnDelay=" + respawnDelay +
                ", super=" + super.toString() +
                '}';
    }

    public enum State {
        MOVE, SINK, SPAWN, DEAD
    }
}
