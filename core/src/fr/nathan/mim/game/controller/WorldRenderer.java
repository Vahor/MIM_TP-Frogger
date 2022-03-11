package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.nathan.mim.game.TextureFactory;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.*;

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
                frogger.onJumpEnd();
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
                vehicle.getY(), // Offset 1.1f
                vehicle.getWidth(),
                vehicle.getHeight(),
                //0
                vehicle.getFacingDirection().getRotation()
        );
    }

    public void drawFly(Fly fly) {
        TextureRegion region = TextureFactory.getInstance().getIdleFly();
        draw(region,
                fly.getX(),
                fly.getY(), // Offset 1.1f
                fly.getWidth(),
                fly.getHeight(),
                0
        );
    }

    public void drawTurtle(Turtle turtle) {
        TextureRegion region;
        if (turtle.getState() == Turtle.State.SINK) {
            Animation<TextureRegion> animation = TextureFactory.getInstance().getSinkingTurtle();
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
            return;
        }

        draw(region,
                turtle.getX(),
                turtle.getY(),
                turtle.getWidth(),
                turtle.getHeight(),
                turtle.getFacingDirection().getRotation()
        );
    }

    public void drawTree(Tree tree) {
        TextureRegion region = TextureFactory.getInstance().getIdleFly();
        draw(region,
                tree.getX(),
                tree.getY(), // Offset 1.1f
                tree.getWidth(),
                tree.getHeight(),
                tree.getFacingDirection().getRotation()
        );
    }

    public void drawElements() {
        for (GameElement element : world.getElements()) {
            if (element instanceof Vehicle) {
                drawVehicle((Vehicle) element);
            }
            else if (element instanceof Fly) {
                drawFly((Fly) element);
            }
            else if (element instanceof Turtle) {
                drawTurtle((Turtle) element);
            }
            else if (element instanceof Tree) {
                drawTree((Tree) element);
            }
        }
    }

    @Override
    public void update(float delta) {
        batch.begin();

        drawBackground();
        drawFrogger();
        drawElements();

        batch.end();
    }
}
