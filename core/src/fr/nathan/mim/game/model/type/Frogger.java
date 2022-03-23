package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

    private float currentJumpTime = 0;

    private final float jumpDelay;
    private final float jumpDistance;
    private final Vector2 startingPosition;

    private int remainingLives;

    public Frogger(FroggerConfiguration froggerConfiguration) {
        this.jumpDelay        = froggerConfiguration.getJumpDelay();
        this.jumpDistance     = froggerConfiguration.getJumpDistance();
        this.remainingLives   = froggerConfiguration.getTotalLives();
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

        // À chaque update, si on a un delay de saut, on le décrémente
        if (currentJumpTime > 0) {
            currentJumpTime -= delta;
            // Si il est terminé, on peut re-sauter
            if (currentJumpTime <= 0) {
                canJump = true;
            }
        }
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
