package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Turtle;
import fr.nathan.mim.game.texture.TextureFactory;
import fr.nathan.mim.game.texture.TextureHolder;

public class TurtleTexture extends TextureHolder<Turtle> {

    @Override
    public TextureRegion getTexture(Turtle turtle) {
        TextureRegion region;
        if (turtle.getState() == Turtle.State.SINK) {
            Animation<TextureRegion> animation = TextureFactory.getInstance().getSinkingTurtle(); // todo faire des variables avec les textures
            region = animation.getKeyFrame(turtle.getStateTime());
            if (animation.isAnimationFinished(turtle.getStateTime())) {
                turtle.onSinkEnd();
            }
        }
        else if (turtle.getState() == Turtle.State.MOVE) {
            Animation<TextureRegion> animation = TextureFactory.getInstance().getMovingTurtle();
            region = animation.getKeyFrame(turtle.getStateTime());
        }
        else if (turtle.getState() == Turtle.State.SPAWN) {
            Animation<TextureRegion> animation = TextureFactory.getInstance().getSpawningTurtle();
            region = animation.getKeyFrame(turtle.getStateTime());
            if (animation.isAnimationFinished(turtle.getStateTime())) {
                turtle.onSpawnEnd();
            }
        }
        else {
            return null;
        }

        return region;
    }
}
