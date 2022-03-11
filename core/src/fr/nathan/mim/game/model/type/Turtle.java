package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.model.MovingEntity;

public class Turtle extends MovingEntity {

    public enum State {
        MOVE, SINK, SPAWN, DEAD
    }

    private transient State state = State.MOVE;
    private transient float stateTime = 0;

    private float respawnDelay = 3;
    private float maxRideTime = 2;

    private float currentRideTime = 0;

    public Turtle() {
        onSpawnEnd();
    }

    public State getState() {
        return state;
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

    public float getStateTime() {
        return stateTime;
    }

    @Override
    public float getWidth() {
        return 0.7f;
    }

    @Override
    public float getHeight() {
        return 0.7f;
    }

    @Override
    public boolean onCollide(Frogger frogger, float delta) {
        if (state != State.MOVE) return true;

        currentRideTime += delta;

        frogger.getVelocity().set(getVelocity());

        if (currentRideTime > maxRideTime) {
            setState(State.SINK);
            getVelocity().set(0, 0);
            frogger.getVelocity().set(0, 0);
            currentRideTime = 0;
        }

        // todo reset currentRideTime lorsqu'on n'est plus dessus

        return false;
    }

    @Override
    public void afterDeserialization() {
        super.afterDeserialization();
        updateVelocity();
    }

    public void onSinkEnd() {
        setState(State.DEAD);
        World.TIMER.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                setState(State.SPAWN);
            }
        }, respawnDelay);
    }

    public void onSpawnEnd() {
        setState(State.MOVE);
        getVelocity().set(
                getFacingDirection().getMotX() * getSpeed(),
                getFacingDirection().getMotY() * getSpeed()
        );
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("respawnDelay", respawnDelay);
        json.writeValue("maxRideTime", maxRideTime);
    }

    @Override
    public String toString() {
        return "Turtle{" +
                "state=" + state +
                ", stateTime=" + stateTime +
                ", respawnDelay=" + respawnDelay +
                ", super=" + super.toString() +
                '}';
    }
}
