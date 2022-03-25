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

    @Override
    public void afterInitialisation() {
        super.afterInitialisation();
        position.set(getNextPosition());
    }

    @Override
    public void updateVelocity() {}

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
    public CollideResult onCollideWith(MovingEntity frogger, float delta) {
        return CollideResult.BLOCK;
    }

    @Override
    public Direction getDirection() {
        return Direction.RIGHT;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;
        if (changeSpotDelay > 0 && stateTime > changeSpotDelay) {
            getPosition().set(getNextPosition());
            stateTime = 0;
        }
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
}
