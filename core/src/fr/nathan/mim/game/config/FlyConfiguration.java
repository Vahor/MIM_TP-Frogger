package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class FlyConfiguration {

    private float changeSpotDelay;
    private float stayOnSportDelay;

    private List<Vector2> positions;

    //Parser
    public FlyConfiguration() {
    }

    public FlyConfiguration(float changeSpotDelay, float stayOnSportDelay, List<Vector2> positions) {
        this.changeSpotDelay  = changeSpotDelay;
        this.stayOnSportDelay = stayOnSportDelay;
        this.positions        = positions;
    }

    public float getChangeSpotDelay() {
        return changeSpotDelay;
    }

    public float getStayOnSportDelay() {
        return stayOnSportDelay;
    }

    public List<Vector2> getPositions() {
        return positions;
    }
}
