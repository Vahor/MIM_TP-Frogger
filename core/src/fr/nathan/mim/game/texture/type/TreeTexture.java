package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Tree;
import fr.nathan.mim.game.texture.TextureHolder;

public class TreeTexture extends TextureHolder<Tree> {

    private final TextureAtlas atlas;

    public TreeTexture(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    public TextureRegion getTexture(Tree tree) {
        return atlas.findRegion(Integer.toString(tree.getType().getId()));
    }
}
