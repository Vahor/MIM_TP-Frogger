package fr.nathan.mim.game.texture.type;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.nathan.mim.game.model.type.Vehicle;
import fr.nathan.mim.game.texture.TextureHolder;

public class VehicleTexture extends TextureHolder<Vehicle> {

    private final TextureAtlas atlas;

    public VehicleTexture(TextureAtlas atlas) {this.atlas = atlas;}

    @Override
    public TextureRegion getTexture(Vehicle vehicle) {
        return atlas.findRegion(Integer.toString(vehicle.getType().getId()));
    }
}
