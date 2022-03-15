package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;

public class Fly extends GameElement {

    private transient float stateTime = 0;

    private final float changeSpotDelay;
    private final float stayOnSportDelay;

    public Fly(float changeSpotDelay, float stayOnSportDelay) {
        this.changeSpotDelay  = changeSpotDelay;
        this.stayOnSportDelay = stayOnSportDelay;
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
    public boolean onCollideWith(MovingEntity frogger, float delta) {
        return false;
    }

    @Override
    public float getYWithRoad() {
        return getY();
    }

    @Override
    public Direction getDirection() {
        return Direction.DOWN;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;
        if (changeSpotDelay > 0 && stateTime > changeSpotDelay) {
            getPosition().x = World.SHARED_RANDOM.nextFloat() * 8; // todo config
            getPosition().y = World.SHARED_RANDOM.nextInt(13); // todo config
            stateTime       = -stayOnSportDelay;
        }
    }
}
