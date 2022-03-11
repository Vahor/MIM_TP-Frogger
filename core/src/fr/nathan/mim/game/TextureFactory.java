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

    // Vehicle
    private final TextureAtlas vehicleAtlas;

    // Turtle
    private final Animation<TextureRegion> movingTurtle;
    private final Animation<TextureRegion> sinkingTurtle;
    private final Animation<TextureRegion> spawningTurtle;

    // TreeLog
    private final TextureAtlas treeAtlas;

    // Fly
    private final TextureRegion idleFly;

    private final Texture background;

    public TextureFactory() {
        vehicleAtlas = new TextureAtlas(Gdx.files.internal("vehicle/vehicle.pack"));
        background   = new Texture(Gdx.files.internal("background.png"));

        // Frogger
        TextureAtlas froggerAtlas = new TextureAtlas(Gdx.files.internal("frogger/frogger.pack"));
        idleFrogger    = froggerAtlas.findRegion("idle");
        jumpingFrogger = new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, froggerAtlas.findRegions("jump"), Animation.PlayMode.LOOP);

        // Turtle
        TextureAtlas turtleAtlasMove = new TextureAtlas(Gdx.files.internal("turtle/move/moving.atlas"));
        TextureAtlas turtleAtlasSink = new TextureAtlas(Gdx.files.internal("turtle/sink.atlas")); // todo en faire un seul avec tout
        movingTurtle  = new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, turtleAtlasMove.findRegions("move"), Animation.PlayMode.LOOP);
        sinkingTurtle = new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, turtleAtlasSink.findRegions("sink"), Animation.PlayMode.LOOP);
        spawningTurtle = new Animation<TextureRegion>(WorldRenderer.FRAME_DURATION, turtleAtlasSink.findRegions("sink"), Animation.PlayMode.LOOP_REVERSED);

        // TreeLog
        treeAtlas =  new TextureAtlas(Gdx.files.internal("log/log.pack"));

        // Fly
        TextureAtlas flyAtlas = new TextureAtlas(Gdx.files.internal("fly/fly.pack"));
        idleFly = flyAtlas.findRegion("idle");
    }

    public TextureRegion getIdleFrogger() {
        return idleFrogger;
    }
    public Animation<TextureRegion> getJumpingFrogger() {
        return jumpingFrogger;
    }

    public Animation<TextureRegion> getMovingTurtle() {
        return movingTurtle;
    }
    public Animation<TextureRegion> getSinkingTurtle() {
        return sinkingTurtle;
    }
    public Animation<TextureRegion> getSpawningTurtle() {
        return spawningTurtle;
    }

    public TextureAtlas getTreeAtlas() {
        return treesAtlas;
    }

    public TextureRegion getIdleFly() {
        return idleFly;
    }

    public TextureAtlas getVehicleAtlas() {
        return vehicleAtlas;
    }

    public Texture getBackground() {
        return background;
    }
}
