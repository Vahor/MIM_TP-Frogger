package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.RefugeFly;
import fr.nathan.mim.game.texture.TextureHolder;

public class RefugeFlyTexture extends TextureHolder<RefugeFly> {

    private final TextureRegion region;

    public RefugeFlyTexture(TextureRegion region) {
        this.region = region;
    }

    @Override
    public TextureRegion getTexture(RefugeFly fly) {
        return region;
    }
}
