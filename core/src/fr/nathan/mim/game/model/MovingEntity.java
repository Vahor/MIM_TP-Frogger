package fr.nathan.mim.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.controller.WorldRenderer;

public abstract class MovingEntity extends GameElement {

    protected transient Vector2 velocity = new Vector2();

    private float speed = 1;

    private Direction facingDirection = Direction.LEFT;

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }
    public Direction getFacingDirection() {
        return facingDirection;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public final float getSpeed() {return speed;}

    @Override
    public void update(float delta) {
        super.update(delta);
        position.add(velocity.cpy().scl(delta));
    }

    public void whenOutOfBorder() {
        if (facingDirection == Direction.LEFT)
            position.x = WorldRenderer.CAMERA_WIDTH;
        else if (facingDirection == Direction.RIGHT)
            position.x = -getWidth();
    }

    protected void updateVelocity() {
        getVelocity().set(
                getFacingDirection().getMotX() * getSpeed(),
                getFacingDirection().getMotY() * getSpeed()
        );
    }

    @Override
    public String toString() {
        return "MovingEntity{" +
                "velocity=" + velocity +
                ", facingDirection=" + facingDirection +
                ", speed=" + getSpeed() +
                ", super=" + super.toString() +
                '}';
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("speed", speed);
        json.writeValue("facingDirection", facingDirection);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        setFacingDirection(Direction.valueOf(jsonData.getString("facingDirection")));
    }
}
