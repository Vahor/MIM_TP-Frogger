package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.FlyConfiguration;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;

import java.util.List;

public class Fly extends GameElement {

    private transient float stateTime = 0;

    private final float changeSpotDelay;
    private final List<Vector2> positions;

    public Fly(FlyConfiguration flyConfiguration) {
        this.changeSpotDelay  = flyConfiguration.getChangeSpotDelay();
        this.positions        = flyConfiguration.getPositions();
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
    public CollideResult onCollideWith(MovingEntity frogger, float delta) {
        return CollideResult.NOTHING;
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
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
        return positions.get(World.SHARED_RANDOM.nextInt(positions.size()));
    }
}
