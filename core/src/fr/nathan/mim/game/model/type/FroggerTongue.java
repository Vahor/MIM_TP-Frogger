package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.MovingEntity;

public class FroggerTongue extends MovingEntity {

    private transient float distance = 0;
    private final transient float maxDistance;
    private final transient float speed;
    private final Frogger frogger;

    private Direction direction;

    public FroggerTongue(Frogger frogger) {
        direction    = frogger.getDirection();
        maxDistance  = frogger.getTongueDistance();
        speed        = frogger.getTongueSpeed();
        this.frogger = frogger;
        position.set(
                frogger.getPosition().x + frogger.getWidth() / 2 - getWidth() / 2,
                frogger.getPosition().y + frogger.getHeight() / 2 - getHeight() / 2
        );
        getVelocity().x = getDirection().getMotX() * speed;
        getVelocity().y = getDirection().getMotY() * speed;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (direction != null)
            if (distance > maxDistance) {
                direction = null;
                frogger.setTongueCount(frogger.getTongueCount() + 1);
            }
            else {
                distance += speed * delta;
            }
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        if (direction != null) {
            frogger.setTongueCount(frogger.getTongueCount() + 1);
        }
        return true;
    }

    @Override
    public void updateVelocity() {}

    @Override
    public CollideResult onCollideWith(MovingEntity element, float delta) {
        if (element instanceof Frogger) return CollideResult.NOTHING;
        if (element instanceof FroggerTongue) return CollideResult.NOTHING;
        direction = null;
        return CollideResult.NOTHING;
    }

    @Override
    public boolean watchCollide() {
        return isVisible();
    }

    @Override
    public boolean isVisible() {
        return direction != null;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
    }

    @Override
    public float getRotationOffset() {
        return frogger.getRotationOffset();
    }

    @Override
    public float getWidth() {
        return .1f;
    }

    @Override
    public float getHeight() {
        return .3f;
    }

    @Override
    public String toString() {
        return "FroggerTongue{" +
                "distance=" + distance +
                ", maxDistance=" + maxDistance +
                ", speed=" + speed +
                ", frogger=" + frogger +
                ", direction=" + direction +
                '}';
    }
}
