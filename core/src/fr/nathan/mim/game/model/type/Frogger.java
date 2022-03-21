package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.FroggerConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Frogger extends MovingEntity {

    public enum State {
        IDLE, JUMPING
    }

    private transient State state = State.IDLE;
    private transient float stateTime = 0;
    private transient Direction direction = Direction.UP;

    private transient boolean canJump = true;

    private final float jumpDelay;
    private final float jumpDistance;
    private final Vector2 startingPosition;

    private int remainingLives;

    public Frogger(FroggerConfiguration froggerConfiguration) {
        this.jumpDelay      = froggerConfiguration.getJumpDelay();
        this.jumpDistance   = froggerConfiguration.getJumpDistance();
        this.remainingLives = froggerConfiguration.getTotalLives();
        this.startingPosition = froggerConfiguration.getStartingPosition();
    }

    public State getState() {return state;}

    public boolean canJump() {
        return canJump && state == State.IDLE;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public float getJumpDistance() {
        return jumpDistance;
    }
    public Vector2 getStartingPosition() {
        return startingPosition;
    }
    public int getRemainingLives() {
        return remainingLives;
    }
    @Override
    public float getYWithRoadOffset() {
        return getY();
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
    public void whenOutOfBorder(World world) {
        position.set(
                MathUtils.clamp(position.x, 0, world.getWidth() - getWidth()),
                MathUtils.clamp(position.y, 0, world.getHeight() - getHeight())
        );
        onJumpEnd();
    }

    public void onJumpEnd() {
        setState(Frogger.State.IDLE);
        if (jumpDelay > 0) {
            World.TIMER.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    canJump = true;
                }
            }, jumpDelay);
        }
        else {
            canJump = true;
        }
        getVelocity().set(0, 0);
    }

    public void onJumpStart() {
        canJump = false;
    }

    public float getStateTime() {
        return stateTime;
    }

    /**
     *
     * @return True si la partie continue
     */
    public boolean onDied() {
        remainingLives--;
        System.out.println("remainingLives = " + remainingLives);
        return remainingLives > 0;
    }

    @Override
    public float getWidth() {
        return .55f;
    }

    @Override
    public float getHeight() {
        return .55f;
    }

    @Override
    public float getRotationOffset() {
        return 90;
    }
    @Override
    public String toString() {
        return "Frogger{" +
                "state=" + state +
                ", super=" + super.toString() +
                '}';
    }
}
