package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.FlyConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Fly extends MovingEntity {

    private final float delay;
    private final float moveSpeed;

    private float stateTime;

    private Vector2 direction;
    private Vector2 fromPoint;
    private Vector2 toPoint;

    private boolean leftToRight = false;
    private boolean alive = true;

    public Fly(FlyConfiguration flyConfiguration) {
        this.delay     = flyConfiguration.getDelay();
        this.moveSpeed = flyConfiguration.getMoveSpeed();
    }

    public void initRandomDirection() {
        fromPoint = new Vector2(
                0,
                World.SHARED_RANDOM.nextFloat() * World.instance.getHeight());
        toPoint   = new Vector2(
                World.instance.getWidth(),
                World.SHARED_RANDOM.nextFloat() * World.instance.getHeight());

        leftToRight = World.SHARED_RANDOM.nextBoolean();
        if (leftToRight)
            direction = toPoint.cpy().sub(fromPoint);
        else
            direction = fromPoint.cpy().sub(toPoint);

        // Normalize
        direction.nor();
    }

    @Override
    public void updateVelocity() {
        velocity.set(direction.cpy().scl(moveSpeed + (World.instance.getMoveSpeedBoost())));
    }

    @Override
    public void afterInitialisation() {
        super.afterInitialisation();

        initRandomDirection();
        updateVelocity();
        position.add(velocity.cpy().scl(World.SHARED_RANDOM.nextFloat() * toPoint.cpy().sub(fromPoint).len()));
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        stateTime += delta;
        if (stateTime > delay) {
            initRandomDirection();
            updateVelocity();
            position.set(leftToRight ? fromPoint : toPoint);
            stateTime = 0;
        }
        return false;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public float getWidth() {
        return .7f;
    }

    @Override
    public float getHeight() {
        return .7f;
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
    public boolean isUseDirectionForBorders() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return alive;
    }

    @Override
    public void onLevelRestart() {
        alive = true;
    }

    @Override
    public CollideResult onCollideWith(MovingEntity entity, float delta) {
        if (entity instanceof FroggerTongue) {
            setAlive(false);
            entity.onCollideWith(this, delta);
            return CollideResult.EAT;
        }
        return CollideResult.DEAD;
    }
}
