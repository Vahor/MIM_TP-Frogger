package fr.nathan.mim.game.config;

public class TurtleConfiguration {

    private float respawnDelay;
    private float maxRideTime;

    //Parser
    public TurtleConfiguration() {
    }

    public TurtleConfiguration(float respawnDelay, float maxRideTime) {
        this.respawnDelay = respawnDelay;
        this.maxRideTime  = maxRideTime;
    }

    public float getRespawnDelay() {
        return respawnDelay;
    }

    public float getMaxRideTime() {
        return maxRideTime;
    }
}
