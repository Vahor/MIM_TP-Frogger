package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Fly;
import fr.nathan.mim.game.texture.TextureHolder;

public class FlyTexture extends TextureHolder<Fly> {

    private final TextureRegion region;
    private final TextureRegion explosion;

    public FlyTexture(TextureRegion region, TextureRegion explosion) {
        this.region    = region;
        this.explosion = explosion;
    }

    @Override
    public TextureRegion getTexture(Fly fly) {
        if(fly.getState() == Fly.State.DEAD) {
            return explosion;
        }
        return region;
    }
}
