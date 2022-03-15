package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.MovingEntity;

public class Frogger extends MovingEntity {

    public enum State {
        IDLE, JUMPING, DYING
    }

    private transient State state = State.IDLE;
    private transient float stateTime = 0;
    private transient Direction direction = Direction.UP;

    private transient boolean canJump = true;

    private final float jumpDelay;
    private final float jumpDistance;
    private final float animationDuration;

    public Frogger(float jumpDelay, float jumpDistance, float animationDuration) {
        this.jumpDelay         = jumpDelay;
        this.jumpDistance      = jumpDistance;
        this.animationDuration = animationDuration;
    }

    @Override
    public boolean onCollideWith(MovingEntity frogger, float delta) {
        return false;
    }

    public float getAnimationDuration() {
        return animationDuration;
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

    @Override
    public float getYWithRoad() {
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
    public void whenOutOfBorder() {
        super.whenOutOfBorder();
        // todo gerer les bordures pour pas que la grenouille sorte du cadre
        //  Avec les flèches + lorsqu'on est sur une tortur/arbre
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

    @Override
    public float getWidth() {
        return .7f;
    }

    @Override
    public float getHeight() {
        return .7f;
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
