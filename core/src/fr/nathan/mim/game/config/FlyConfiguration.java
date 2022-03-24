package fr.nathan.mim.game.config;

public class FlyConfiguration {

    private float delay;

    private float moveSpeed;

    //Parser
    public FlyConfiguration() {
    }

    public FlyConfiguration(float delay, float moveSpeed) {
        this.delay     = delay;
        this.moveSpeed = moveSpeed;
    }

    public float getDelay() {
        return delay;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }
}
