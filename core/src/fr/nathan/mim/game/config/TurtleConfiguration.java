package fr.nathan.mim.game.config;

public class TurtleConfiguration {

    private float respawnDelay;
    private float maxRideTime;
    private int minGroupSize;
    private int maxGroupSize;

    //Parser
    public TurtleConfiguration() {
    }

    public TurtleConfiguration(float respawnDelay, float maxRideTime, int minGroupSize, int maxGroupSize) {
        this.respawnDelay = respawnDelay;
        this.maxRideTime  = maxRideTime;
        this.minGroupSize = minGroupSize;
        this.maxGroupSize = maxGroupSize;
    }

    public float getRespawnDelay() {
        return respawnDelay;
    }

    public float getMaxRideTime() {
        return maxRideTime;
    }

    public int getMinGroupSize() {
        return minGroupSize;
    }
    public int getMaxGroupSize() {
        return maxGroupSize;
    }
}
