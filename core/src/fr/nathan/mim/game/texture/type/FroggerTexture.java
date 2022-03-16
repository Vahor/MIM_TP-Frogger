package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.texture.TextureHolder;

public class FroggerTexture extends TextureHolder<Frogger> {

    private final TextureRegion idle;
    private final Animation<TextureRegion> jumpingAnimation;

    public FroggerTexture(TextureRegion idle, Animation<TextureRegion> jumpingAnimation) {
        this.idle             = idle;
        this.jumpingAnimation = jumpingAnimation;
    }

    public Animation<TextureRegion> getJumpingAnimation() {
        return jumpingAnimation;
    }

    @Override
    public TextureRegion getTexture(Frogger frogger) {

        TextureRegion region;
        if (frogger.getState() == Frogger.State.JUMPING) {
            region = jumpingAnimation.getKeyFrame(frogger.getStateTime());
            if (jumpingAnimation.isAnimationFinished(frogger.getStateTime())) {
                frogger.onJumpEnd();
            }
        }
        else if (frogger.getState() == Frogger.State.IDLE) {
            region = idle;
        }
        else {
            return null;
        }

        return region;
    }

}
