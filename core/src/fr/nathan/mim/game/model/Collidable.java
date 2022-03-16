package fr.nathan.mim.game.model;

import fr.nathan.mim.game.CollideResult;

public interface Collidable {

    boolean collideWith(MovingEntity element);
    CollideResult onCollideWith(MovingEntity element, float delta);
}
