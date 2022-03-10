package fr.nathan.mim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.controller.WorldRenderer;

public class TextureFactory {

    private static TextureFactory instance = null;

    public static TextureFactory getInstance() {
        if (instance == null) {
            instance = new TextureFactory();
        }
        return instance;
    }

    // Frogger
    private final TextureRegion idleFrogger;
    private final Animation<TextureRegion> jumpingFrogger;

    private final TextureAtlas vehicleAtlas;
    private final Texture background;

    public TextureFactory() {
        vehicleAtlas = new TextureAtlas(Gdx.files.internal("vehicle/data.pack"));
        TextureAtlas froggerAtlas = new TextureAtlas(Gdx.files.internal("frogger/data.pack"));
        background = new Texture(Gdx.files.internal("background.png"));

        idleFrogger    = froggerAtlas.findRegion("idle");
        jumpingFrogger = new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, froggerAtlas.findRegions("jump"), Animation.PlayMode.LOOP);
    }

    public TextureRegion getIdleFrogger() {
        return idleFrogger;
    }
    public Animation<TextureRegion> getJumpingFrogger() {
        return jumpingFrogger;
    }

    public TextureAtlas getVehicleAtlas() {
        return vehicleAtlas;
    }

    public Texture getBackground() {
        return background;
    }
}
