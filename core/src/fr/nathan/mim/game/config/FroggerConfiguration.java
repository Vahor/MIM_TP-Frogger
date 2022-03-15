package fr.nathan.mim.game.config;

public class FroggerConfiguration {

    private float jumpDelay;
    private float jumpDistance;
    private float animationDuration;

    // parser
    public FroggerConfiguration() {
    }

    public FroggerConfiguration(float jumpDelay, float jumpDistance, float animationDuration) {
        this.jumpDelay         = jumpDelay;
        this.jumpDistance      = jumpDistance;
        this.animationDuration = animationDuration;
    }

    public float getJumpDelay() {
        return jumpDelay;
    }

    public float getJumpDistance() {
        return jumpDistance;
    }

    public float getAnimationDuration() {
        return animationDuration;
    }
}
