package fr.nathan.mim.game.model;

import com.badlogic.gdx.math.Vector2;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.controller.WorldRenderer;

public abstract class MovingEntity extends GameElement {

    protected transient Vector2 velocity = new Vector2();

    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        position.add(velocity.cpy().scl(delta));
    }

    public void whenOutOfBorder() {
        if (getRoad().getDirection() == Direction.LEFT)
            position.x = WorldRenderer.CAMERA_WIDTH;
        else if (getRoad().getDirection() == Direction.RIGHT)
            position.x = -getWidth();
    }

    protected void updateVelocity() {
        getVelocity().set(
                getRoad().getDirection().getMotX() * getRoad().getMoveSpeed(),
                getRoad().getDirection().getMotY() * getRoad().getMoveSpeed()
        );
    }

    @Override
    public String toString() {
        return "MovingEntity{" +
                "velocity=" + velocity +
                ", super=" + super.toString() +
                '}';
    }
}
