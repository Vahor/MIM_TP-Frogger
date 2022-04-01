package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Vehicle;
import fr.nathan.mim.game.texture.TextureHolder;

public class VehicleTexture extends TextureHolder<Vehicle> {

    private final TextureAtlas atlas;
    private final TextureRegion explosion;

    public VehicleTexture(TextureAtlas atlas, TextureRegion explosion) {
        this.atlas     = atlas;
        this.explosion = explosion;
    }

    @Override
    public TextureRegion getTexture(Vehicle vehicle) {
        if (vehicle.getState() == Vehicle.State.DEAD) {
            return explosion;
        }
        return atlas.findRegion(Integer.toString(vehicle.getType().getId()));
    }
}
