package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.config.FroggerConfiguration;
import fr.nathan.mim.game.model.MovingEntity;

public class Frogger extends MovingEntity {

    private final float jumpDelay;
    private final float jumpDistance;
    private final Vector2 startingPosition;

    private final float shootDelay;
    private final float tongueDistance;
    private final float tongueSpeed;
    private int tongueCount;
    private final int maxTongueCount;

    private transient State state = State.IDLE;
    private transient float stateTime = 0;
    private transient Direction direction = Direction.UP;

    private transient boolean canJump = true;

    private float currentJumpTime = 0;
    private float currentShootTime = 0;

    public Frogger(FroggerConfiguration froggerConfiguration) {
        this.jumpDelay        = froggerConfiguration.getJumpDelay();
        this.jumpDistance     = froggerConfiguration.getJumpDistance();
        this.startingPosition = froggerConfiguration.getStartingPosition();
        this.shootDelay       = froggerConfiguration.getShootDelay();
        this.tongueDistance   = froggerConfiguration.getTongueDistance();
        this.tongueCount      = froggerConfiguration.getMaxTongueCount();
        this.maxTongueCount   = froggerConfiguration.getMaxTongueCount();
        this.tongueSpeed      = froggerConfiguration.getTongueSpeed();
    }

    public State getState() {return state;}

    public void setState(State state) {
        this.state = state;
        stateTime  = 0;
    }

    public boolean canShoot() {
        return tongueCount > 0 && state != State.SHOOTING;
    }

    public int getTongueCount() {
        return tongueCount;
    }

    public float getTongueSpeed() {
        return tongueSpeed;
    }

    public void setTongueCount(int tongueCount) {
        if (tongueCount > maxTongueCount) return;
        this.tongueCount = tongueCount;
    }

    public float getTongueDistance() {
        return tongueDistance;
    }

    public boolean canJump() {
        return canJump && state == State.IDLE;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getJumpDistance() {
        return jumpDistance;
    }

    public Vector2 getStartingPosition() {
        return startingPosition;
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
    }

    @Override
    public boolean isUseDirectionForBorders() {
        return false;
    }

    @Override
    public void onLevelRestart() {
        setTongueCount(maxTongueCount);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;

        // À chaque update, si on a un delay de saut, on le décrémente
        if (currentJumpTime > 0) {
            currentJumpTime -= delta;
            // Si il est terminé, on peut re-sauter
            if (currentJumpTime <= 0) {
                canJump = true;
            }
        }

        if (currentShootTime > 0) {
            currentShootTime -= delta;
            // Si il est terminé, on peut re-tirer
            if (currentShootTime <= 0) {
                onShootEnd();
            }
        }
    }

    @Override
    public boolean whenOutOfBorder(World world, float delta) {
        position.set(
                MathUtils.clamp(position.x, 0, world.getWidth() - getWidth()),
                MathUtils.clamp(position.y, 0, world.getHeight() - getHeight())
        );
        onJumpEnd();
        return false;
    }

    public void onJumpEnd() {
        setState(Frogger.State.IDLE);
        getVelocity().set(0, 0);

        // Si il y a un delay de saut, on l'applique, sinon on peut re-sauter directement
        if (jumpDelay > 0)
            currentJumpTime = jumpDelay;
        else
            canJump = true;
    }

    public void onJumpStart() {
        canJump = false;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void onDied() {
    }

    public void onShootStart() {
        if (state == State.IDLE) {
            setState(State.SHOOTING);
            tongueCount--;
            currentShootTime = shootDelay;
        }
    }

    public void onShootEnd() {
        setState(State.IDLE);
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

    public enum State {
        IDLE, JUMPING, SHOOTING
    }
}
