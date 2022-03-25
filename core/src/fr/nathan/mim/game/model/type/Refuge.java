package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;

public class Refuge extends GameElement {

    private boolean isOccupied = false;

    @Override
    public float getWidth() {
        if (isOccupied)
            return .5f;
        return .7f;
    }

    @Override
    public float getHeight() {
        if (isOccupied)
            return .5f;
        return .7f;
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
    }

    @Override
    public Direction getDirection() {
        return Direction.RIGHT;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    @Override
    public boolean isVisible() {
        return isOccupied; // On n'a rien à afficher si le refuge n'est pas occupé
    }

    @Override
    public CollideResult onCollideWith(MovingEntity frogger, float delta) {
        if (isOccupied) {
            return CollideResult.BLOCK;
        }
        isOccupied = true;
        return CollideResult.WIN;
    }
}
