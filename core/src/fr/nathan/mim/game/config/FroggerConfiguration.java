package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

public class FroggerConfiguration {

    private float jumpDelay;
    private float jumpDistance;
    private Vector2 startingPosition;

    private float shootDelay;
    private float tongueDistance;
    private float tongueSpeed;
    private int maxTongueCount;

    // parser
    public FroggerConfiguration() {
    }

    public FroggerConfiguration(float jumpDelay, float jumpDistance, Vector2 startingPosition, float shootDelay, float tongueDistance, float tongueSpeed, int maxTongueCount) {
        this.jumpDelay        = jumpDelay;
        this.jumpDistance     = jumpDistance;
        this.startingPosition = startingPosition;
        this.shootDelay       = shootDelay;
        this.tongueDistance   = tongueDistance;
        this.tongueSpeed      = tongueSpeed;
        this.maxTongueCount   = maxTongueCount;
    }

    public float getJumpDelay() {
        return jumpDelay;
    }

    public float getJumpDistance() {
        return jumpDistance;
    }

    public Vector2 getStartingPosition() {
        return startingPosition;
    }

    public int getMaxTongueCount() {
        return maxTongueCount;
    }

    public float getTongueDistance() {
        return tongueDistance;
    }

    public float getTongueSpeed() {
        return tongueSpeed;
    }

    public float getShootDelay() {
        return shootDelay;
    }

    @Override
    public String toString() {
        return "FroggerConfiguration{" +
                "jumpDelay=" + jumpDelay +
                ", jumpDistance=" + jumpDistance +
                ", startingPosition=" + startingPosition +
                ", shootDelay=" + shootDelay +
                ", tongueDistance=" + tongueDistance +
                ", tongueSpeed=" + tongueSpeed +
                ", maxTongueCount=" + maxTongueCount +
                '}';
    }
}
