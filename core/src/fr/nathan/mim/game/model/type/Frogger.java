package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.model.MovingEntity;

public class Frogger extends MovingEntity {

    public enum State {
        IDLE, JUMPING, DYING
    }

    private transient State state = State.IDLE;
    private transient float stateTime = 0;

    private transient boolean canJump = true;

    private float jumpDelay = 0.1f;

    @Override
    public boolean onCollide(Frogger frogger, float delta) {
        return false;
    }

    public State getState() {return state;}

    public boolean canJump() {
        return canJump && state == State.IDLE;
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
    public String toString() {
        return "Frogger{" +
                "state=" + state +
                ", super=" + super.toString() +
                '}';
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("jumpDelay", jumpDelay);
    }
}
