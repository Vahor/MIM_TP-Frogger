package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

public class FroggerConfiguration {

    private float jumpDelay;
    private float jumpDistance;
    private Vector2 startingPosition;
    private int totalLives;

    // parser
    public FroggerConfiguration() {
    }

    public FroggerConfiguration(float jumpDelay, float jumpDistance, Vector2 startingPosition, int totalLives) {
        this.jumpDelay        = jumpDelay;
        this.jumpDistance     = jumpDistance;
        this.startingPosition = startingPosition;
        this.totalLives       = totalLives;
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

    public int getTotalLives() {
        return totalLives;
    }

    @Override
    public String toString() {
        return "FroggerConfiguration{" +
                "jumpDelay=" + jumpDelay +
                ", jumpDistance=" + jumpDistance +
                ", startingPosition=" + startingPosition +
                ", totalLives=" + totalLives +
                '}';
    }
}
