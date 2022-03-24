package fr.nathan.mim.game.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.Fly;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.RefugeFly;
import fr.nathan.mim.game.model.type.Tree;
import fr.nathan.mim.game.model.type.Turtle;
import fr.nathan.mim.game.model.type.Vehicle;
import fr.nathan.mim.game.texture.type.FlyTexture;
import fr.nathan.mim.game.texture.type.FroggerTexture;
import fr.nathan.mim.game.texture.type.RefugeFlyTexture;
import fr.nathan.mim.game.texture.type.TreeTexture;
import fr.nathan.mim.game.texture.type.TurtleTexture;
import fr.nathan.mim.game.texture.type.VehicleTexture;

import java.util.HashMap;
import java.util.Map;

public class TextureFactory {

    private static TextureFactory instance = null;

    private final Map<Class<? extends GameElement>, TextureHolder<? extends GameElement>> textureMap = new HashMap<Class<? extends GameElement>, TextureHolder<? extends GameElement>>();

    private final Texture logo;
    private final Texture background;
    private final Texture background_blur;
    private final Skin rainbowUISkin;
    private final BitmapFont font;

    public TextureFactory() {

        logo            = new Texture(Gdx.files.internal("logo.png"));

        background      = new Texture(Gdx.files.internal("background.png"));
        background_blur = new Texture(Gdx.files.internal("background_blur.png"));

        font          = new BitmapFont(Gdx.files.internal("pack/freezing/raw/font-export.fnt"));
        rainbowUISkin = new Skin(Gdx.files.internal("pack/freezing/skin/freezing-ui.json"));

        font.setUseIntegerPositions(false);

        // Frogger
        TextureAtlas froggerAtlas = new TextureAtlas(Gdx.files.internal("frogger/frogger.atlas"));
        textureMap.put(Frogger.class, new FroggerTexture(
                froggerAtlas.findRegion("idle"),
                new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, froggerAtlas.findRegions("jump"), Animation.PlayMode.LOOP)
        ));

        // Turtle
        TextureAtlas turtleAtlasMove = new TextureAtlas(Gdx.files.internal("turtle/move/moving.atlas"));
        TextureAtlas turtleAtlasSink = new TextureAtlas(Gdx.files.internal("turtle/sink.atlas")); // todo en faire un seul avec tout

        textureMap.put(Turtle.class, new TurtleTexture(
                new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, turtleAtlasMove.findRegions("move"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, turtleAtlasSink.findRegions("sink"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, turtleAtlasSink.findRegions("sink"), Animation.PlayMode.LOOP_REVERSED)
        ));

        textureMap.put(Tree.class, new TreeTexture(new TextureAtlas(Gdx.files.internal("log/log.pack"))));
        textureMap.put(Vehicle.class, new VehicleTexture(new TextureAtlas(Gdx.files.internal("vehicle/vehicle.pack"))));
        textureMap.put(Fly.class, new FlyTexture(new TextureRegion(new Texture(Gdx.files.internal("fly/idle_00.png")))));
        textureMap.put(RefugeFly.class, new RefugeFlyTexture(new TextureRegion(new Texture(Gdx.files.internal("fly/idle_00.png")))));
    }

    public static TextureFactory getInstance() {
        if (instance == null) {
            instance = new TextureFactory();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameElement> TextureRegion getTexture(T model) {
        TextureHolder<T> holder = (TextureHolder<T>) textureMap.get(model.getClass());
        if (holder == null) return null;
        return holder.getTexture(model);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameElement> TextureHolder<T> getTextureHolder(Class<T> clazz) {
        return (TextureHolder<T>) textureMap.get(clazz);
    }

    public Texture getBackground() {
        return background;
    }

    public Texture getBackgroundBlur() {
        return background_blur;
    }

    public Texture getLogo() {return logo;}

    public BitmapFont getFont() {
        return font;
    }

    public Skin getSkin() {
        return rainbowUISkin;
    }
}
