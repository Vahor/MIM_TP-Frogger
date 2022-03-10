package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import fr.nathan.mim.game.model.type.World;

public abstract class Controller {

    protected final Batch batch;
    protected final World world;

    public Controller(World world, Batch batch) {
        this.batch = batch;
        this.world = world;
    }

    abstract public void update(float delta);
}
