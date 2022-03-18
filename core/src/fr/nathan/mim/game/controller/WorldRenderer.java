package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.texture.TextureFactory;

public class WorldRenderer extends Controller {

    // todo remplacer par JUMP_FRAME_DURATION dans le Frogger
    public static final float FRAME_DURATION = .1f; // Temps d'affichage d'une frame, le jeu est à 60 fps, donc frame duration = x/60

    OrthographicCamera camera;
    Viewport viewport;
    ShapeRenderer shapeRenderer;
    BitmapFont font = new BitmapFont();

    private float pixelsPerUnitX;
    private float pixelsPerUnitY;

    public WorldRenderer(World world, Batch batch) {
        super(world, batch);
        camera   = new OrthographicCamera(world.getWidth(), world.getHeight());
        viewport = new FitViewport(600, 690, camera);
        viewport.apply();
        shapeRenderer = new ShapeRenderer();
    }

    public void setSize(float width, float height) {
        viewport.update((int) width, (int) height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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

    @Override
    public void update(float delta) {
        batch.begin();

        drawBackground();
        drawElements();

        drawElement(world.getFrogger());

        batch.end();

        if (world.isDebug()) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            // Ajout des lignes verticales
            for (int col = 0; col < world.getWidth(); col++) {
                shapeRenderer.line(
                        col * pixelsPerUnitX,
                        0,
                        col * pixelsPerUnitX,
                        world.getHeight() * pixelsPerUnitY
                );
            }

            // Ajout des lignes horizontales
            for (int row = 0; row < world.getHeight(); row++) {
                shapeRenderer.line(
                        0,
                        row * pixelsPerUnitY,
                        world.getWidth() * pixelsPerUnitX,
                        row * pixelsPerUnitY
                );
            }

            // Zones de click
            shapeRenderer.setColor(Color.ORANGE);
            // Diagonale Haut Gauche -> Bas Droit
            shapeRenderer.line(
                    0,
                    world.getHeight() * pixelsPerUnitY,
                    world.getWidth() * pixelsPerUnitX,
                    0
            );

            // Diagonale Haut Droit -> Bas Gauche
            shapeRenderer.line(
                    world.getWidth() * pixelsPerUnitX,
                    world.getHeight() * pixelsPerUnitY,
                    0,
                    0
            );


            shapeRenderer.end();


            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            batch.begin();
            // Informations sur les routes
            for (Road road : world.getRoads()) {
                font.draw(batch,
                        road.getType().name() + " - " + road.getDirection().name(),
                        10,
                        (road.getOffsetY() + .2f) * pixelsPerUnitY);
                font.draw(batch,
                        road.getOffsetY() +
                                " - [" + road.getEntityMinDistance() + ", " + road.getEntityMaxDistance() + "]"
                                + " - " + road.getElements().size() + "/" + road.getEntityCount(),
                        10,
                        (road.getOffsetY() + .5f) * pixelsPerUnitY);

                for (GameElement element : road.getElements()) {

                    shapeRenderer.setColor(Color.PURPLE);
                    shapeRenderer.rect(
                            element.getX() * pixelsPerUnitX,
                            element.getYWithRoadOffset() * pixelsPerUnitY,
                            .1f * pixelsPerUnitX,
                            element.getHeight() * pixelsPerUnitY);

                    shapeRenderer.setColor(Color.CYAN);
                    shapeRenderer.rect(
                            (element.getX() + element.getWidth()) * pixelsPerUnitX,
                            element.getYWithRoadOffset() * pixelsPerUnitY,
                            .1f * pixelsPerUnitX,
                            element.getHeight() * pixelsPerUnitY);
                }
            }

            batch.end();
            shapeRenderer.end();

        }

    }
}
