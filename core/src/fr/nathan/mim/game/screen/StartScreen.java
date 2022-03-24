package fr.nathan.mim.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import fr.nathan.mim.game.Client;
import fr.nathan.mim.game.texture.TextureFactory;

public class StartScreen implements Screen, InputProcessor {

    private final GlyphLayout glyphLayout = new GlyphLayout();
    private final Batch batch;

    public StartScreen(Batch batch) {
        this.batch = batch;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        batch.begin();

        BitmapFont font = TextureFactory.getInstance().getFont();

        glyphLayout.setText(font, "Frogger !");
        font.draw(batch, glyphLayout,
                (Gdx.graphics.getWidth() - glyphLayout.width) / 2,
                (Gdx.graphics.getHeight() - glyphLayout.height) / 2);


        glyphLayout.setText(font, "Clique pour jouer");
        font.draw(batch, glyphLayout,
                (Gdx.graphics.getWidth() - glyphLayout.width) / 2,
                (Gdx.graphics.getHeight() - glyphLayout.height - 50) / 2);


        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Client.getInstance().setScreen(new GameScreen(batch));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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
