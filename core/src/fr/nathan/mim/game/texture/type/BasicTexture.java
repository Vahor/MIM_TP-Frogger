package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Fly;
import fr.nathan.mim.game.texture.TextureHolder;

public class BasicTexture extends TextureHolder<GameElement> {

    private final TextureRegion region;

    public BasicTexture(TextureRegion region) {
        this.region = region;
    }

    @Override
    public TextureRegion getTexture(GameElement fly) {
        return region;
    }
}
