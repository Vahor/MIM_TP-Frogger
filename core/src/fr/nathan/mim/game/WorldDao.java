package fr.nathan.mim.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import fr.nathan.mim.game.config.Configurable;
import fr.nathan.mim.game.model.type.Fly;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.Tree;
import fr.nathan.mim.game.model.type.Turtle;
import fr.nathan.mim.game.model.type.Vehicle;
import fr.nathan.mim.game.model.type.World;

public class WorldDao {

    private final Json parser;

    public WorldDao() {
        parser = new Json();

        parser.setOutputType(JsonWriter.OutputType.json);

        parser.addClassTag("Fly", Fly.class);
        parser.addClassTag("Vehicle", Vehicle.class);
        parser.addClassTag("Frogger", Frogger.class);
        parser.addClassTag("Turtle", Turtle.class);
        parser.addClassTag("Tree", Tree.class);
    }

    public World get(String path) {

        FileHandle fileHandle = Gdx.files.external(path);
        String rawJson = fileHandle.readString();

        World world = parser.fromJson(World.class, rawJson);
        world.afterInitialisation();
        world.getFrogger().afterInitialisation();
        for (Configurable element : world.getRoads()) {
            element.afterInitialisation();
        }

        return world;
    }

    public void save(String path, World world) {

        FileHandle fileHandle = Gdx.files.external(path);
        fileHandle.writeString(parser.prettyPrint(world), false); // todo toJson à la place de prettyPrint
    }
}
