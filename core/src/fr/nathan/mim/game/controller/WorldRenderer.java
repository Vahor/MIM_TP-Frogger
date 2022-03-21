package fr.nathan.mim.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.nathan.mim.game.controller.renderer.DebugRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.texture.TextureFactory;

public class WorldRenderer extends Controller {

    // todo remplacer par JUMP_FRAME_DURATION dans le Frogger
    public static final float FRAME_DURATION = .1f; // Temps d'affichage d'une frame, le jeu est à 60 fps, donc frame duration = x/60

    OrthographicCamera camera;
    Viewport viewport;

    DebugRenderer debugRenderer;
    GlyphLayout glyphLayout = new GlyphLayout();

    private float pixelsPerUnitX;
    private float pixelsPerUnitY;

    public WorldRenderer(World world, Batch batch) {
        super(world, batch);
        camera   = new OrthographicCamera(world.getWidth(), world.getHeight());
        viewport = new FitViewport(600, 690, camera);
        viewport.apply();

        debugRenderer = new DebugRenderer(this);

    }

    public float getPixelsPerUnitX() {
        return pixelsPerUnitX;
    }
    public float getPixelsPerUnitY() {
        return pixelsPerUnitY;
    }

    public void resize(float width, float height) {
        viewport.update((int) width, (int) height, true);

        pixelsPerUnitX = camera.viewportWidth / world.getWidth();
        pixelsPerUnitY = camera.viewportHeight / world.getHeight();
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

    private void drawElement(GameElement element) {
        TextureRegion region = TextureFactory.getInstance().getTexture(element);
        if (region == null) {
            System.out.println("element = " + element.getClass());
            return;
        }

        float realWidth = element.getWidth() * pixelsPerUnitX;
        float realHeight = element.getHeight() * pixelsPerUnitY;

        batch.draw(region,
                element.getX() * pixelsPerUnitX,
                element.getYWithRoadOffset() * pixelsPerUnitY,
                realWidth / 2f,
                realHeight / 2f,
                realWidth,
                realHeight,
                1,
                1,
                element.getDirection().getRotation() + element.getRotationOffset()
        );

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

    public void drawHUD() {
        BitmapFont font = TextureFactory.getInstance().getFont();
        Frogger frogger = world.getFrogger();

        font.draw(batch, "Nombre de vies : " + frogger.getRemainingLives(),
                0,
                14 * pixelsPerUnitY);


        glyphLayout.setText(font, "Temps restant : " + world.getCurrentTime().intValue() + "s");
        font.draw(batch, glyphLayout,
                (world.getWidth() * pixelsPerUnitX - glyphLayout.width),
                14 * pixelsPerUnitY);


        glyphLayout.setText(font, "FPS : " + Gdx.graphics.getFramesPerSecond());
        font.draw(batch, glyphLayout,
                (world.getWidth() * pixelsPerUnitX - glyphLayout.width) / 2,
                14 * pixelsPerUnitY);
    }

    public void drawGameOver() {
        BitmapFont font = TextureFactory.getInstance().getFont();
        glyphLayout.setText(font, "GAME OVER");
        font.draw(batch, glyphLayout,
                (world.getWidth() * pixelsPerUnitX - glyphLayout.width) / 2,
                (world.getHeight() * pixelsPerUnitY) / 2);

        glyphLayout.setText(font, "CLIQUER POUR RECOMMENCER");
        font.draw(batch, glyphLayout,
                (world.getWidth() * pixelsPerUnitX - glyphLayout.width) / 2,
                ((world.getHeight() - 3) * pixelsPerUnitY) / 2);
    }

    @Override
    public void update(float delta) {
        batch.begin();

        if (world.isGameOver()) {
            drawGameOver();
        }
        else {
            drawBackground();
            drawElements();

            drawElement(world.getFrogger());
            drawHUD();
        }

        batch.end();

        debugRenderer.update(delta);

    }
}
