package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.FlyConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Fly extends MovingEntity {

    private float stateTime;

    private final float hiddenTime;
    private final float visibleTime;

    private final Vector2 fromPoint;
    private final Vector2 toPoint;

    private final float moveSpeed;

    private boolean visible = false;

    public Fly(FlyConfiguration flyConfiguration) {
        this.hiddenTime  = flyConfiguration.getHiddenTime();
        this.visibleTime = flyConfiguration.getVisibleTime();
        this.fromPoint   = flyConfiguration.getFromPoint();
        this.toPoint     = flyConfiguration.getToPoint();
        this.moveSpeed   = flyConfiguration.getMoveSpeed();
    }

    @Override
    public void updateVelocity() {
        velocity.set(
                toPoint.cpy().sub(fromPoint).scl(moveSpeed + World.instance.getMoveSpeedBoost())
        );
        System.out.println("velocity = " + velocity);
    }

    @Override
    public void afterInitialisation() {
        super.afterInitialisation();
        updateVelocity();
        position.y = fromPoint.y;
        position.add(velocity.cpy().scl(World.SHARED_RANDOM.nextFloat() * toPoint.cpy().sub(fromPoint).len()));
    }

    @Override
    public void whenOutOfBorder(World world) {

        position.set(fromPoint);

        //System.out.println("position = " + position);
        //moveSpeed = -moveSpeed;
        //updateVelocity();
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
    public Direction getDirection() {
        return Direction.RIGHT;
    }

    @Override
    public boolean isUseDirectionForBorders() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public CollideResult onCollideWith(MovingEntity frogger, float delta) {
        if (!visible) return CollideResult.NOTHING;
        return CollideResult.DEAD;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;
        if (visible && stateTime > visibleTime) {
            visible   = false;
            stateTime = 0;
        }
        else if (!visible && stateTime > hiddenTime) {
            visible   = true;
            stateTime = 0;
        }
    }
}
