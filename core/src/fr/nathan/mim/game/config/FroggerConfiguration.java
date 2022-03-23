package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

public class FroggerConfiguration {

    private float jumpDelay;
    private float jumpDistance;
    private Vector2 startingPosition;

    // parser
    public FroggerConfiguration() {
    }

    public FroggerConfiguration(float jumpDelay, float jumpDistance, Vector2 startingPosition) {
        this.jumpDelay        = jumpDelay;
        this.jumpDistance     = jumpDistance;
        this.startingPosition = startingPosition;
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

    @Override
    public String toString() {
        return "FroggerConfiguration{" +
                "jumpDelay=" + jumpDelay +
                ", jumpDistance=" + jumpDistance +
                ", startingPosition=" + startingPosition +
                '}';
    }
}
