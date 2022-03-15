package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Fly;
import fr.nathan.mim.game.texture.TextureHolder;

public class FlyTexture extends TextureHolder<Fly> {

    private final TextureRegion region;

    public FlyTexture(TextureRegion region) {
        this.region = region;
    }

    @Override
    public TextureRegion getTexture(Fly fly) {
        return region;
    }
}
