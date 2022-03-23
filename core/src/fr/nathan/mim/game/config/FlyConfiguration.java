package fr.nathan.mim.game.config;

import com.badlogic.gdx.math.Vector2;

public class FlyConfiguration {

    private float hiddenTime;
    private float visibleTime;

    private Vector2 fromPoint;
    private Vector2 toPoint;

    private float moveSpeed;

    //Parser
    public FlyConfiguration() {
    }

    public FlyConfiguration(float hiddenTime, float visibleTime, Vector2 fromPoint, Vector2 toPoint, float moveSpeed) {
        this.hiddenTime  = hiddenTime;
        this.visibleTime = visibleTime;
        this.fromPoint   = fromPoint;
        this.toPoint     = toPoint;
        this.moveSpeed   = moveSpeed;
    }

    public float getHiddenTime() {
        return hiddenTime;
    }
    public float getVisibleTime() {
        return visibleTime;
    }
    public Vector2 getFromPoint() {
        return fromPoint;
    }
    public Vector2 getToPoint() {
        return toPoint;
    }
    public float getMoveSpeed() {
        return moveSpeed;
    }
}
