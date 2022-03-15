package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Turtle;
import fr.nathan.mim.game.texture.TextureHolder;

public class TurtleTexture extends TextureHolder<Turtle> {

    private final Animation<TextureRegion> movingTurtle;
    private final Animation<TextureRegion> sinkingTurtle;
    private final Animation<TextureRegion> spawningTurtle;

    public TurtleTexture(Animation<TextureRegion> movingTurtle, Animation<TextureRegion> sinkingTurtle, Animation<TextureRegion> spawningTurtle) {
        this.movingTurtle   = movingTurtle;
        this.sinkingTurtle  = sinkingTurtle;
        this.spawningTurtle = spawningTurtle;
    }

    @Override
    public TextureRegion getTexture(Turtle turtle) {
        TextureRegion region;
        if (turtle.getState() == Turtle.State.SINK) {
            region = sinkingTurtle.getKeyFrame(turtle.getStateTime());
            if (sinkingTurtle.isAnimationFinished(turtle.getStateTime())) {
                turtle.onSinkEnd();
            }
        }
        else if (turtle.getState() == Turtle.State.MOVE) {
            region = movingTurtle.getKeyFrame(turtle.getStateTime());
        }
        else if (turtle.getState() == Turtle.State.SPAWN) {
            region = spawningTurtle.getKeyFrame(turtle.getStateTime());
            if (spawningTurtle.isAnimationFinished(turtle.getStateTime())) {
                turtle.onSpawnEnd();
            }
        }
        else {
            return null;
        }

        return region;
    }
}
