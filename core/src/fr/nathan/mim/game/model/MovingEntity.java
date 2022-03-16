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
        if (getDirection() == Direction.LEFT) {
            // On récupère l'élement le plus à droite et on y ajoute l'écart requis
            GameElement lastElement = getRoad().getLastElement();

            // On ne veut pas tp au centre de l'écran, donc on ne prend que ce qui est après la bordure
            float x = Math.max(lastElement.getX() + lastElement.getWidth(), WorldRenderer.WORLD_WIDTH);

            position.x = x + getOffsetXToNextEntity();
        }
        else if (getDirection() == Direction.RIGHT) {
            GameElement firstElement = getRoad().getFirstElement();

            float x = Math.min(firstElement.getX(), 0);

            position.x = x - getOffsetXToNextEntity();
        }

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
