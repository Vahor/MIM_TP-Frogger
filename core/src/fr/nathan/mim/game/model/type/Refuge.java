package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;

public class Refuge extends GameElement {

    @Override
    public float getWidth() {
        return .5f;
    }

    @Override
    public float getHeight() {
        return .5f;
    }

    @Override
    public float getYWithRoadOffset() {
        return getY();
    }

    @Override
    public boolean isVisible() {
        // Rien à afficher pour les textures
        return false;
    }

    @Override
    public CollideResult onCollideWith(MovingEntity frogger, float delta) {
        return CollideResult.WIN;
    }
}
