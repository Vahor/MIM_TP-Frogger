package fr.nathan.mim.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.nathan.mim.game.Client;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.texture.TextureFactory;

import java.util.Random;

public class GameOverScreen implements Screen, InputProcessor {

    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final Batch batch;
    private final World world;
    private final FitViewport viewport;

    public GameOverScreen(World world, Batch batch) {
        this.world = world;
        this.batch = batch;

        viewport = new FitViewport(600, 730);
        viewport.apply();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        batch.begin();


        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        batch.draw(
                TextureFactory.getInstance().getBackgroundBlur(),
                0,
                0,
                width,
                height
        );

        BitmapFont font = TextureFactory.getInstance().getFont();

        glyphLayout.setText(font, "Perdu !");
        font.draw(batch, glyphLayout,
                (width - glyphLayout.width) / 2f,
                (height - glyphLayout.height + 100) / 2f);


        glyphLayout.setText(font, "Score : " + world.getScore());
        font.draw(batch, glyphLayout,
                (width - glyphLayout.width) / 2f,
                (height - glyphLayout.height - 50) / 2);

        glyphLayout.setText(font,
                "Temps : " + new Float(world.getMaxTime() - world.getCurrentTime()).intValue() + "s");
        font.draw(batch, glyphLayout,
                (width - glyphLayout.width) / 2f,
                (height - glyphLayout.height - 90) / 2);


        glyphLayout.setText(font, "Clique pour recommencer");
        font.draw(batch, glyphLayout,
                (width - glyphLayout.width) / 2,
                (height - glyphLayout.height - 180) / 2);


        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // On change le random pour pas refaire la même partie
        World.SHARED_RANDOM = new Random(World.SHARED_RANDOM.nextLong());

        Client.getInstance().setScreen(new StartScreen(batch));

        return true;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
