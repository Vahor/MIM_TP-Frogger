package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.model.MovingEntity;

public class Frogger extends MovingEntity {

    public enum State {
        IDLE, JUMPING, DYING
    }

    private State state = State.IDLE;
    private float stateTime = 0;

    public Frogger() {
    }

    @Override
    public boolean onCollide() {
        return false;
    }

    public State getState() {return state;}
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
    public float getSpeed() {
        return 1;
    }

    @Override
    public float getWidth() {
        return .8f;
    }

    @Override
    public float getHeight() {
        return .8f;
    }

    @Override
    public String toString() {
        return "Frogger{" +
                "state=" + state +
                ", super=" + super.toString() +
                '}';
    }
}
