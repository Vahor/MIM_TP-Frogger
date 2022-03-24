package fr.nathan.mim.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.nathan.mim.game.screen.StartScreen;

public class Client extends Game {

    private static Client instance = null;

    private Batch batch;

    public static Game getInstance() {
        return instance;
    }

    @Override
    public void create() {
        batch    = new SpriteBatch();
        instance = this;

        setScreen(new StartScreen(batch));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
