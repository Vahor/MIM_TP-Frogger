package fr.nathan.mim.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.nathan.mim.game.screen.GameScreen;

public class Client extends Game {

    private Batch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();

        setScreen(new GameScreen(batch));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
