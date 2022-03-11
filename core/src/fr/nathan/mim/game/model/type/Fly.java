package fr.nathan.mim.game.model.type;

import com.badlogic.gdx.utils.Json;
import fr.nathan.mim.game.model.GameElement;

public class Fly extends GameElement {

    private float[] availableSpots = new float[]{0, 2, 4, 8};
    private float changeSpotDelay = 5;

    private transient float stateTime = 0;

    public Fly() {
    }

    @Override
    public float getWidth() {
        return .5f;
    }
    @Override
    public float getHeight() {
        return .5f;
    }

    @Override
    public boolean onCollide(Frogger frogger, float delta) {
        System.out.println("Fly.onCollide");
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        stateTime += delta;
        if (changeSpotDelay > 0 && stateTime > changeSpotDelay) {
            getPosition().x = availableSpots[World.SHARED_RANDOM.nextInt(availableSpots.length)];
            stateTime       = 0;
        }
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("availableSpots", availableSpots);
        json.writeValue("changeSpotDelay", changeSpotDelay);
    }
}
