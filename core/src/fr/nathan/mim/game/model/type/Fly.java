package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.FlyConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Fly extends MovingEntity {

    private final float delay;
    private final float moveSpeed;

    private Vector2 direction;
    private Vector2 fromPoint;
    private Vector2 toPoint;

    private boolean leftToRight = false;

    private transient State state = State.ALIVE;
    private transient float stateTime = 0;

    public Fly(FlyConfiguration flyConfiguration) {
        this.delay     = flyConfiguration.getDelay();
        this.moveSpeed = flyConfiguration.getMoveSpeed();
    }

    public void initRandomDirection() {
        // Ajouter les cas gauche/haut et haut/droite
        if (World.SHARED_RANDOM.nextBoolean()) {
            // Début à gauche
            fromPoint = new Vector2(
                    0,
                    (World.SHARED_RANDOM.nextFloat() * World.instance.getHeight()) - (getHeight() / 2));
        }
        else {
            // Début à haut
            fromPoint = new Vector2(
                    World.SHARED_RANDOM.nextFloat() * World.instance.getWidth(),
                    World.instance.getHeight() - getHeight() / 2);
        }


        if (World.SHARED_RANDOM.nextBoolean()) {
            // Fin à droite
            toPoint = new Vector2(
                    World.instance.getWidth(),
                    World.SHARED_RANDOM.nextFloat() * World.instance.getHeight() - getHeight() / 2);
        }
        else {
            // Fin en bas
            toPoint = new Vector2(
                    World.SHARED_RANDOM.nextFloat() * World.instance.getWidth(),
                    0);
        }

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
    public void update(float delta) {
        if (state != State.DEAD)
            super.update(delta);
        stateTime += delta;
        if (state == State.DEAD && stateTime > 1) {
            state = State.HIDDEN;
        }
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
        stateTime = 0;
        if (alive)
            this.state = State.ALIVE;
        else
            this.state = State.DEAD;
    }

    public State getState() {
        return state;
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
        return state != State.HIDDEN;
    }

    @Override
    public void onLevelRestart() {
        setAlive(true);
    }

    @Override
    public CollideResult onCollideWith(MovingEntity entity, float delta) {
        if (entity instanceof Frogger) {
            return CollideResult.DEAD;
        }
        return CollideResult.NOTHING;
    }

    public enum State {
        ALIVE,
        DEAD,
        HIDDEN
    }
}
