package fr.nathan.mim.game.model;

import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.controller.WorldRenderer;

public abstract class MovingEntity extends GameElement {

    protected Vector2 velocity = new Vector2();

    private Direction facingDirection = Direction.UP;

    protected MovingEntity() {
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }
    public Direction getFacingDirection() {
        return facingDirection;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

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

    @Override
    public String toString() {
        return "MovingEntity{" +
                "velocity=" + velocity +
                ", facingDirection=" + facingDirection +
                ", super=" + super.toString() +
                '}';
    }
}
