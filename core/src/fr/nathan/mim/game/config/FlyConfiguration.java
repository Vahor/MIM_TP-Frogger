package fr.nathan.mim.game.config;

public class FlyConfiguration {

    private float changeSpotDelay;
    private float stayOnSportDelay;

    //Parser
    public FlyConfiguration() {
    }

    public FlyConfiguration(float changeSpotDelay, float stayOnSportDelay) {
        this.changeSpotDelay  = changeSpotDelay;
        this.stayOnSportDelay = stayOnSportDelay;
    }

    public float getChangeSpotDelay() {
        return changeSpotDelay;
    }

    public float getStayOnSportDelay() {
        return stayOnSportDelay;
    }
}
