package fr.nathan.mim.game.model;

public interface Collidable {

    boolean collideWith(MovingEntity element);
    boolean onCollideWith(MovingEntity element, float delta);
}
