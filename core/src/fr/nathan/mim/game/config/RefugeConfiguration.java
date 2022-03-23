package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class RefugeConfiguration {


    private List<Vector2> positions;

    //Parser
    public RefugeConfiguration() {
    }

    public RefugeConfiguration(List<Vector2> positions) {
        this.positions = positions;
    }

    public List<Vector2> getPositions() {
        return positions;
    }
}
