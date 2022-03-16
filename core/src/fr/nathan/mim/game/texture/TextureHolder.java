package fr.nathan.mim.game.texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.GameElement;

public abstract class TextureHolder<Model extends GameElement> {

    abstract public TextureRegion getTexture(Model model);
}
