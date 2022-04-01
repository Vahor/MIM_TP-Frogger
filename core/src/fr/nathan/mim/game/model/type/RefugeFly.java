package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.RefugeFlyConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

import java.util.ArrayList;
import java.util.List;

public class RefugeFly extends MovingEntity {

    private final float changeSpotDelay;
    private final List<Refuge> refuges = new ArrayList<Refuge>(5);

    private static final Vector2 OUT_OF_SCREEN = new Vector2(-100, -100);

    private transient State state = State.ALIVE;
    private transient float stateTime = 0;

    public RefugeFly(RefugeFlyConfiguration flyConfiguration) {
        this.changeSpotDelay = flyConfiguration.getChangeSpotDelay();
    }

    public void addRefuge(Refuge refuge) {
        refuges.add(refuge);
    }

    public void removeRefuge(Refuge refuge) {
        refuges.remove(refuge);
    }

    public State getState() {
        return state;
    }

    public void setAlive(boolean alive) {
        stateTime = 0;
        if (alive)
            this.state = State.ALIVE;
        else
            this.state = State.DEAD;
    }

    @Override
    public void afterInitialisation() {
        super.afterInitialisation();
        position.set(getNextPosition());
    }

    @Override
    public void updateVelocity() {}

    @Override
    public boolean isVisible() {
        return state != State.HIDDEN;
    }

    @Override
    public float getWidth() {
        return .5f;
    }

    @Override
    public float getHeight() {
        return .5f;
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
    }

    @Override
    public CollideResult onCollideWith(MovingEntity entity, float delta) {
        if (entity instanceof Frogger) {
            getPosition().set(getNextPosition());
            return CollideResult.EAT;
        }
        return CollideResult.NOTHING;
    }

    @Override
    public Direction getDirection() {
        return Direction.RIGHT;
    }

    @Override
    public void update(float delta) {
        if (state != State.DEAD)
            super.update(delta);

        stateTime += delta;
        if (state == State.ALIVE && changeSpotDelay > 0 && stateTime > changeSpotDelay) {
            getPosition().set(getNextPosition());
            stateTime = 0;
        }


        if (state == State.DEAD && stateTime > 1) {
            state = State.HIDDEN;
        }
    }

    @Override
    public void onLevelRestart() {
        setAlive(true);
    }

    public Vector2 getNextPosition() {
        if (refuges.size() == 1) {
            if (World.SHARED_RANDOM.nextBoolean())
                return OUT_OF_SCREEN;
        }

        Refuge refuge = refuges.get(World.SHARED_RANDOM.nextInt(refuges.size()));
        if (refuge.isOccupied()) {
            removeRefuge(refuge);
            return getNextPosition();
        }

        return refuge.getPosition();
    }

    public enum State {
        ALIVE,
        DEAD,
        HIDDEN
    }
}
