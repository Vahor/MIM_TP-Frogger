package fr.nathan.mim.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.nathan.mim.game.Client;
import fr.nathan.mim.game.texture.TextureFactory;

public class StartScreen implements Screen {

    private final Batch batch;
    private final FitViewport viewport;

    private Stage stage;

    public StartScreen(final Batch batch) {
        this.batch = batch;
        viewport   = new FitViewport(600, 730);
        viewport.apply();

        stage = new Stage(viewport);


        TextButton button = new TextButton(
                " Cliques pour jouer ",
                TextureFactory.getInstance().getSkin());
        button.pad(30);

        button.setPosition(
                (viewport.getWorldWidth() - button.getWidth()) / 2,
                (viewport.getWorldHeight() - button.getHeight() - 150) / 2
        );

        button.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Client.getInstance().setScreen(new GameScreen(batch));
                return true;
            }
        });

        stage.addActor(button);

        ////
        TextButton quitter = new TextButton(
                " Quitter ",
                TextureFactory.getInstance().getSkin());
        quitter.pad(30);

        quitter.setPosition(
                (viewport.getWorldWidth() - quitter.getWidth()) / 2,
                button.getY() - 80
        );

        quitter.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        stage.addActor(quitter);

        Gdx.input.setInputProcessor(stage);
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

        TextureRegion logo = new TextureRegion(TextureFactory.getInstance().getLogo());
        float logoWidth = width / 2f;
        float logoHeight = logoWidth * 1.6f;
        batch.draw(
                logo,
                width / 2f - logoWidth / 2f,
                height / 3,
                logoWidth / 2f,
                logoHeight / 2f,
                logoWidth,
                logoHeight,
                1,
                1,
                -45
        );

        batch.end();


        stage.act(delta);
        stage.draw();
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
        stage.dispose();
    }

    @Override
    public void dispose() {

    }
}
