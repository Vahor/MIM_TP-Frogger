package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class FlyConfiguration {

    private float changeSpotDelay;

    private List<Vector2> positions;

    //Parser
    public FlyConfiguration() {
    }

    public FlyConfiguration(float changeSpotDelay, List<Vector2> positions) {
        this.changeSpotDelay = changeSpotDelay;
        this.positions       = positions;
    }

    public float getChangeSpotDelay() {
        return changeSpotDelay;
    }

    public List<Vector2> getPositions() {
        return positions;
    }
}
