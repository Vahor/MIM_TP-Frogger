package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.nathan.mim.game.TextureFactory;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.Vehicle;
import fr.nathan.mim.game.model.type.World;

import java.util.Set;

public class WorldRenderer extends Controller {

    public static final float CAMERA_WIDTH = 8.5f; // columns
    public static final float CAMERA_HEIGHT = 13.5f; // rows

    // todo remplacer par JUMP_FRAME_DURATION dans le Frogger
    public static final float FRAME_DURATION = .1f; // Temps d'affichage d'une frame, le jeu est à 60 fps, donc frame duration = x/60

    OrthographicCamera camera;
    Viewport viewport;

    private float pixelsPerUnitX;
    private float pixelsPerUnitY;

    public WorldRenderer(World world, Batch batch) {
        super(world, batch);
        camera   = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        viewport = new FitViewport(600, 690, camera);
        viewport.apply();
    }

    public void setSize(float width, float height) {
        viewport.update((int) width, (int) height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        pixelsPerUnitX = camera.viewportWidth / CAMERA_WIDTH;
        pixelsPerUnitY = camera.viewportHeight / CAMERA_HEIGHT;
    }

    private void draw(TextureRegion region, float x, float y, float width, float height, float rotation) {

        float realWidth = width * pixelsPerUnitX;
        float realHeight = height * pixelsPerUnitY;
        batch.draw(region,
                x * pixelsPerUnitX,
                y * pixelsPerUnitY,
                realWidth / 2f,
                realHeight / 2f,
                realWidth,
                realHeight,
                1,
                1,
                rotation
        );
    }

    public void drawBackground() {
        batch.draw(
                TextureFactory.getInstance().getBackground(),
                0,
                0,
                viewport.getWorldWidth(),
                viewport.getWorldHeight()
        );
    }

    public void drawFrogger() {
        Frogger frogger = world.getFrogger();
        TextureRegion region;
        if (frogger.getState() == Frogger.State.JUMPING) {
            Animation<TextureRegion> animation = TextureFactory.getInstance().getJumpingFrogger();
            region = animation.getKeyFrame(frogger.getStateTime());
            if (animation.isAnimationFinished(frogger.getStateTime())) {
                frogger.setState(Frogger.State.IDLE);
                frogger.getVelocity().set(0, 0);
            }
        }
        else if (frogger.getState() == Frogger.State.IDLE) {
            region = TextureFactory.getInstance().getIdleFrogger();
        }
        else {
            return;
        }

        draw(region,
                frogger.getX(),
                frogger.getY(),
                frogger.getWidth(),
                frogger.getHeight(),
                90 + frogger.getFacingDirection().getRotation()
        );

    }

    public void drawVehicle(Vehicle vehicle) {
        TextureRegion region = TextureFactory.getInstance().getVehicleAtlas().findRegion(Integer.toString(vehicle.getVehicleType().getId()));
        draw(region,
                vehicle.getX(),
                vehicle.getY() + .1f, // Offset 1.1f
                vehicle.getWidth(),
                vehicle.getHeight(),
                //0
                vehicle.getFacingDirection().getRotation()
        );
    }

    public void drawElements() {
        for (Set<GameElement> value : world.getElementsByRow().values()) {
            for (GameElement element : value) {
                if (element instanceof Vehicle) {
                    drawVehicle((Vehicle) element);
                }
            }
        }
    }

    @Override
    public void update(float delta) {
//        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawBackground();
        drawFrogger();
        drawElements();

        batch.end();
    }
}
