package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Fly;
import fr.nathan.mim.game.model.type.RefugeFly;
import fr.nathan.mim.game.texture.TextureHolder;

public class RefugeFlyTexture extends TextureHolder<RefugeFly> {

    private final TextureRegion region;
    private final TextureRegion explosion;

    public RefugeFlyTexture(TextureRegion region, TextureRegion explosion) {
        this.region    = region;
        this.explosion = explosion;
    }

    @Override
    public TextureRegion getTexture(RefugeFly fly) {
        if (fly.getState() == RefugeFly.State.DEAD) {
            return explosion;
        }
        return region;
    }
}
