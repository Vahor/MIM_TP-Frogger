package fr.nathan.mim.game.config;

public class RefugeFlyConfiguration {

    private float changeSpotDelay;

    //Parser
    public RefugeFlyConfiguration() {
    }

    public RefugeFlyConfiguration(float changeSpotDelay) {
        this.changeSpotDelay = changeSpotDelay;
    }

    public float getChangeSpotDelay() {
        return changeSpotDelay;
    }
}
