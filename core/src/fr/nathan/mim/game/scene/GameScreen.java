package fr.nathan.mim.game.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import fr.nathan.mim.game.WorldDao;
import fr.nathan.mim.game.controller.WorldController;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.type.World;

public class GameScreen implements Screen, InputProcessor {

    private final WorldController worldController;
    private final WorldRenderer worldRenderer;

    public GameScreen(Batch batch) {
        WorldDao worldDao = new WorldDao();
        World world;
        world = new World();
        world.demoWorld();
//        world = worldDao.get("test2.json");
        worldDao.save("test2.json", world);

        worldRenderer   = new WorldRenderer(world, batch);
        worldController = new WorldController(world);

        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.update(delta);
        worldController.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.setSize(width, height);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT)
            worldController.onLeftPressed();
        if (keycode == Input.Keys.RIGHT)
            worldController.onRightPressed();
        if (keycode == Input.Keys.UP)
            worldController.onUpPressed();
        if (keycode == Input.Keys.DOWN)
            worldController.onDownPressed();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            worldController.onLeftReleased();
        if (keycode == Input.Keys.RIGHT)
            worldController.onRightReleased();
        if (keycode == Input.Keys.UP)
            worldController.onUpReleased();
        if (keycode == Input.Keys.DOWN)
            worldController.onDownReleased();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
