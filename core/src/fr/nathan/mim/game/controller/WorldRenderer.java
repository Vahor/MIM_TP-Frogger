package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.nathan.mim.game.controller.renderer.DebugRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.texture.TextureFactory;

public class WorldRenderer extends Controller {

    // todo remplacer par JUMP_FRAME_DURATION dans le Frogger
    public static final float FRAME_DURATION = .08f; // Temps d'affichage d'une frame, le jeu est à 60 fps, donc frame duration = x/60

    OrthographicCamera camera;
    Viewport viewport;

    DebugRenderer debugRenderer;
    GlyphLayout glyphLayout = new GlyphLayout();

    private final float pixelsPerUnitX;
    private final float pixelsPerUnitY;

    public WorldRenderer(World world, Batch batch) {
        super(world, batch);
        debugRenderer = new DebugRenderer(this);

        camera   = new OrthographicCamera(world.getWidth(), world.getHeight());
        viewport = new FitViewport(600, 690, camera);
        viewport.apply();


        pixelsPerUnitX = camera.viewportWidth / world.getWidth();
        pixelsPerUnitY = camera.viewportHeight / world.getHeight();

    }

    public float getPixelsPerUnitX() {
        return pixelsPerUnitX;
    }

    public float getPixelsPerUnitY() {
        return pixelsPerUnitY;
    }

    public void resize(float width, float height) {
        viewport.update((int) width, (int) height, true);
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
        if (!element.isVisible()) return;
        TextureRegion region = TextureFactory.getInstance().getTexture(element);
        if (region == null) {
//            System.out.println("element = " + element.getClass());
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

    public void drawHUD(float delta) {
        BitmapFont font = TextureFactory.getInstance().getFont();

        font.draw(batch, "Nombre de vies : " + world.getRemainingLives(),
                0,
                14 * pixelsPerUnitY);

        font.draw(batch, "Tirs : " + world.getFrogger().getTongueCount(),
                0,
                14.3f * pixelsPerUnitY);


        glyphLayout.setText(font, "Temps restant : " + world.getCurrentTime().intValue() + "s");
        font.draw(batch, glyphLayout,
                (world.getWidth() * pixelsPerUnitX - glyphLayout.width),
                14 * pixelsPerUnitY);


        glyphLayout.setText(font, "Score : " + world.getScore());
        font.draw(batch, glyphLayout,
                (world.getWidth() * pixelsPerUnitX - glyphLayout.width) / 2,
                14 * pixelsPerUnitY);

        if (world.getSuccessMessageTime() > 0) {
            world.setSuccessMessageTime(world.getSuccessMessageTime() - delta);

            glyphLayout.setText(font, "Bravo");
            font.draw(batch, glyphLayout,
                    (world.getWidth() * pixelsPerUnitX - glyphLayout.width) / 2,
                    14.3f * pixelsPerUnitY);

        }
    }

    @Override
    public void update(float delta) {
        batch.begin();

        drawBackground();
        drawElements();

        drawElement(world.getFrogger());
        drawHUD(delta);

        batch.end();

        debugRenderer.update(delta);

    }
}
