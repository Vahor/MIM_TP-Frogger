package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Refuge;
import fr.nathan.mim.game.texture.TextureHolder;

public class RefugeTexture extends TextureHolder<Refuge> {

    private final TextureRegion region;

    public RefugeTexture(TextureRegion region) {
        this.region = region;
    }

    @Override
    public TextureRegion getTexture(Refuge refuge) {
        if (refuge.isOccupied()) {
            return region;
        }
        return null;
    }
}
