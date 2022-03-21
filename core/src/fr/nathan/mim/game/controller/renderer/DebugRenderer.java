package fr.nathan.mim.game.controller.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fr.nathan.mim.game.controller.Controller;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.texture.TextureFactory;

public class DebugRenderer extends Controller {

    private final WorldRenderer worldRenderer;
    ShapeRenderer shapeRenderer;

    public DebugRenderer(WorldRenderer worldRenderer) {
        super(worldRenderer.getWorld(), worldRenderer.getBatch());
        this.worldRenderer = worldRenderer;
        shapeRenderer      = new ShapeRenderer();
    }

    public void update(float delta) {

        if (!world.isDebug()) return;

        if (world.isGameOver()) {

        }
        else {
            BitmapFont font = TextureFactory.getInstance().getFont();

            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            // Ajout des lignes verticales
            for (int col = 0; col < world.getWidth(); col++) {
                shapeRenderer.line(
                        col * worldRenderer.getPixelsPerUnitX(),
                        0,
                        col * worldRenderer.getPixelsPerUnitX(),
                        world.getHeight() * worldRenderer.getPixelsPerUnitY()
                );
            }

            // Ajout des lignes horizontales
            for (int row = 0; row < world.getHeight(); row++) {
                shapeRenderer.line(
                        0,
                        row * worldRenderer.getPixelsPerUnitY(),
                        world.getWidth() * worldRenderer.getPixelsPerUnitX(),
                        row * worldRenderer.getPixelsPerUnitY()
                );
            }

            // Zones de click
            shapeRenderer.setColor(Color.ORANGE);
            // Diagonale Haut Gauche -> Bas Droit
            shapeRenderer.line(
                    0,
                    world.getHeight() * worldRenderer.getPixelsPerUnitY(),
                    world.getWidth() * worldRenderer.getPixelsPerUnitX(),
                    0
            );

            // Diagonale Haut Droit -> Bas Gauche
            shapeRenderer.line(
                    world.getWidth() * worldRenderer.getPixelsPerUnitX(),
                    world.getHeight() * worldRenderer.getPixelsPerUnitY(),
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
                        (road.getOffsetY() + .2f) * worldRenderer.getPixelsPerUnitY());
                font.draw(batch,
                        road.getOffsetY() +
                                " - [" + road.getEntityMinDistance() + ", " + road.getEntityMaxDistance() + "]"
                                + " - " + road.getElements().size() + "/" + road.getEntityCount(),
                        10,
                        (road.getOffsetY() + .5f) * worldRenderer.getPixelsPerUnitY());

                for (GameElement element : road.getElements()) {

                    shapeRenderer.setColor(Color.PURPLE);
                    shapeRenderer.rect(
                            element.getX() * worldRenderer.getPixelsPerUnitX(),
                            element.getYWithRoadOffset() * worldRenderer.getPixelsPerUnitY(),
                            .1f * worldRenderer.getPixelsPerUnitX(),
                            element.getHeight() * worldRenderer.getPixelsPerUnitY());

                    shapeRenderer.setColor(Color.CYAN);
                    shapeRenderer.rect(
                            (element.getX() + element.getWidth()) * worldRenderer.getPixelsPerUnitX(),
                            element.getYWithRoadOffset() * worldRenderer.getPixelsPerUnitY(),
                            .1f * worldRenderer.getPixelsPerUnitX(),
                            element.getHeight() * worldRenderer.getPixelsPerUnitY());
                }
            }

            batch.end();
            shapeRenderer.end();
        }

    }
}
