package fr.nathan.mim.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import fr.nathan.mim.game.WorldDao;
import fr.nathan.mim.game.controller.WorldController;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.input.InputHandler;
import fr.nathan.mim.game.model.type.World;

public class GameScreen implements Screen {

    private final WorldController worldController;
    private final WorldRenderer worldRenderer;
    private final InputHandler inputHandler;

    public GameScreen(Batch batch) {
        WorldDao worldDao = new WorldDao();
        World world;
       world = new World();
        world.demoWorld();
//        world = worldDao.get("config.json");
              worldDao.save("config.json", world);

        worldRenderer   = new WorldRenderer(world, batch);
        worldController = new WorldController(world, batch);
        inputHandler    = new InputHandler(worldController);

        Gdx.input.setInputProcessor(inputHandler);

    }

    @Override
    public void show() {
        System.out.println("GameScreen.show");
    }

    @Override
    public void render(float delta) {
        worldRenderer.update(delta);
        worldController.update(delta);
        inputHandler.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
        inputHandler.resize(width, height);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}

}
