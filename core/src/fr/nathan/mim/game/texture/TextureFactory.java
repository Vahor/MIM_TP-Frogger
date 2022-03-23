package fr.nathan.mim.game.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.controller.WorldRenderer;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.type.*;
import fr.nathan.mim.game.texture.type.*;

import java.util.HashMap;
import java.util.Map;

public class TextureFactory {

    private final Map<Class<? extends GameElement>, TextureHolder<? extends GameElement>> textureMap = new HashMap<Class<? extends GameElement>, TextureHolder<? extends GameElement>>();

    private static TextureFactory instance = null;

    public static TextureFactory getInstance() {
        if (instance == null) {
            instance = new TextureFactory();
        }
        return instance;
    }

    private final Texture background;
    private final BitmapFont font;

    public TextureFactory() {
        background = new Texture(Gdx.files.internal("background.png"));
        font       = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        font.getData().setScale(.45f);
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

    public BitmapFont getFont() {
        return font;
    }
}
