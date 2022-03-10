package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.World;

public class FroggerController extends Controller {

//    private final Animation<Sprite> movementAnimation;

    public FroggerController(World world, Batch batch) {
        super(world, batch);

//        Array<Sprite> sprites = TextureFactory.getInstance().getFroggerAtlas().createSprites();
//        this.movementAnimation = new Animation<Sprite>(sprites) {
//            @Override
//            public void onFrameEnd(boolean finished) {
//                Frogger frogger = getFrogger();
////                frogger.setX(frogger.getX() + frogger.getDirection().getMotX() * Constants.ROW_HEIGHT / getFrameCount());
////                frogger.setY(frogger.getY() + frogger.getDirection().getMotY() * Constants.ROW_HEIGHT / getFrameCount());
////
////                Sprite currentFrame = movementAnimation.getCurrentFrame();
////
////                frogger.setHeight(currentFrame.getHeight());
////                frogger.setWidth(currentFrame.getWidth());
//            }
//        };
//        movementAnimation.setTotalDurationInSeconds(.4f);

//        Frogger frogger = getFrogger();
//        Sprite currentFrame = movementAnimation.getCurrentFrame();

//        frogger.setHeight(currentFrame.getHeight());
//        frogger.setWidth(currentFrame.getWidth());
//        frogger.setX((Constants.GAME_WIDTH + frogger.getWidth()) / 2);
    }

    private Frogger getFrogger() {
//        return worldRenderer.getWorld().getFrogger();
        return null;
    }

    public Sprite getFroggerSprite() {
        Frogger frogger = getFrogger();

//        Sprite currentFrame = movementAnimation.getCurrentFrame();
//        currentFrame.setX(frogger.getX());
//        currentFrame.setY(frogger.getY() + (Constants.ROW_HEIGHT - frogger.getHeight()) / 2); // offsetY
//        currentFrame.setRotation(frogger.getDirection().getRotation());

        return null;
    }

    @Override
    public void update(float delta) {
//        handleInput();
//        movementAnimation.update(delta);
    }
}
