package fr.nathan.mim.game.config;

public class FroggerConfiguration {

    private float jumpDelay;
    private float jumpDistance;

    // parser
    public FroggerConfiguration() {
    }

    public FroggerConfiguration(float jumpDelay, float jumpDistance) {
        this.jumpDelay    = jumpDelay;
        this.jumpDistance = jumpDistance;
    }

    public float getJumpDelay() {
        return jumpDelay;
    }

    public float getJumpDistance() {
        return jumpDistance;
    }
}
