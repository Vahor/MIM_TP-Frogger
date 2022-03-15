package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.texture.TextureFactory;

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

    public void drawBackground() {
        batch.draw(
                TextureFactory.getInstance().getBackground(),
                0,
                0,
                viewport.getWorldWidth(),
                viewport.getWorldHeight()
        );
    }


    private void draw(GameElement element) {
        TextureRegion region = TextureFactory.getInstance().getTexture(element);
        if (region == null) {
            System.out.println("element = " + element.getClass());
            return;
        }

        float realWidth = element.getWidth() * pixelsPerUnitX;
        float realHeight = element.getHeight() * pixelsPerUnitY;
        batch.draw(region,
                element.getX() * pixelsPerUnitX,
                element.getYWithRoad() * pixelsPerUnitY,
                realWidth / 2f,
                realHeight / 2f,
                realWidth,
                realHeight,
                1,
                1,
                element.getDirection().getRotation() + element.getRotationOffset()
        );
    }

    private void drawElement(GameElement element) {
        draw(element);
    }

    public void drawElements() {
        for (Road road : world.getRoads()) {
            for (GameElement element : road.getElements()) {
                drawElement(element);
            }

        }

        for (GameElement element : world.getElements()) {
            drawElement(element);
        }
    }

    @Override
    public void update(float delta) {
        batch.begin();

        drawBackground();
        drawElements();

        draw(world.getFrogger());

        batch.end();
    }
}
