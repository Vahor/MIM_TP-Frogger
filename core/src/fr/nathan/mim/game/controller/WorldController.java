package fr.nathan.mim.game.controller;

import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.texture.TextureFactory;
import fr.nathan.mim.game.texture.type.FroggerTexture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldController extends Controller {

    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }

    private final Map<Keys, Boolean> pressedKeys = new HashMap<Keys, Boolean>(4, 1);

    private final Frogger frogger;

    public WorldController(World world) {
        super(world, null);

        frogger = world.getFrogger();

        for (Keys value : Keys.values()) {
            pressedKeys.put(value, false);
        }
    }

    public World getWorld() {return world;}

    public void onLeftPressed() {
        pressedKeys.put(Keys.LEFT, true);
    }
    public void onLeftReleased() {
        pressedKeys.put(Keys.LEFT, false);
    }
    public boolean isLeftPressed() {
        return pressedKeys.get(Keys.LEFT);
    }

    public void onRightPressed() {
        pressedKeys.put(Keys.RIGHT, true);
    }
    public void onRightReleased() {
        pressedKeys.put(Keys.RIGHT, false);
    }
    public boolean isRightPressed() {
        return pressedKeys.get(Keys.RIGHT);
    }

    public void onUpPressed() {
        pressedKeys.put(Keys.UP, true);
    }
    public void onUpReleased() {
        pressedKeys.put(Keys.UP, false);
    }
    public boolean isUpPressed() {
        return pressedKeys.get(Keys.UP);
    }

    public void onDownPressed() {
        pressedKeys.put(Keys.DOWN, true);
    }
    public void onDownReleased() {
        pressedKeys.put(Keys.DOWN, false);
    }
    public boolean isDownPressed() {
        return pressedKeys.get(Keys.DOWN);
    }

    private void handleInput() {
        if (frogger.getState() != Frogger.State.IDLE) return;

        if (isLeftPressed()) {
            frogger.setDirection(Direction.LEFT);
        }
        else if (isRightPressed()) {
            frogger.setDirection(Direction.RIGHT);
        }
        else if (isUpPressed()) {
            frogger.setDirection(Direction.UP);
        }
        else if (isDownPressed()) {
            frogger.setDirection(Direction.DOWN);
        }
        else {
            return;
        }

        if (frogger.canJump()) {
            float animationDuration = ((FroggerTexture) TextureFactory.getInstance().getTextureHolder(Frogger.class)).getJumpingAnimation().getAnimationDuration();

            frogger.setState(Frogger.State.JUMPING);
            frogger.getVelocity().x = frogger.getDirection().getMotX() * (frogger.getJumpDistance() / animationDuration);
            frogger.getVelocity().y = frogger.getDirection().getMotY() * (frogger.getJumpDistance() / animationDuration);
            frogger.onJumpStart();
        }
    }

    private boolean handleBorders(MovingEntity element) {
        if (element.getDirection() == Direction.LEFT && element.getX() < -element.getWidth() ||
                element.getDirection() == Direction.RIGHT && element.getX() > world.getWidth() ||
                element.getY() > world.getHeight() ||
                element.getY() < 0
        ) {
            element.whenOutOfBorder(world);
            return true;
        }

        return false;
    }

    private void handleBordersFrogger(Frogger element) {
        if (element.getX() < 0 ||
                element.getX() > world.getWidth() - element.getWidth() ||
                element.getY() > world.getHeight() - element.getHeight() ||
                element.getY() < 0
        ) {
            element.whenOutOfBorder(world);
        }
    }

    private void handleCollisions(float delta) {
        if (frogger.getState() != Frogger.State.IDLE) return;

        for (Road road : world.getRoads()) {
            for (GameElement element : road.getElements()) {
                CollideResult collideResult = element.handleCollision(frogger, delta);
                if (collideResult == CollideResult.MISS) continue;
                if (collideResult == CollideResult.RIDE) return;
                if (collideResult == CollideResult.DEAD) {
                    System.out.println(":( element");
                    return;
                }
            }
        }

        for (GameElement element : world.getElements()) {
            CollideResult collideResult = element.handleCollision(frogger, delta);
            if (collideResult == CollideResult.MISS) continue;
            if (collideResult == CollideResult.DEAD) {
                System.out.println(":( item");
                return;
            }
        }

        // Handle water

        for (Road road : world.getRoads()) {
            if (road.getType() == Road.Type.WATER) {
                if (road.collideWith(frogger)) {
                    System.out.println(":( water");
                    break;
                }
            }
        }

    }

    private boolean update(GameElement element, float delta) {
        element.update(delta);
        if (element instanceof MovingEntity) {
            return handleBorders((MovingEntity) element);
        }
        return false;
    }

    private void updateFrogger(float delta) {
        frogger.update(delta);
        handleBordersFrogger(frogger);
    }

    private void tryToAddElements(Road road) {

        int currentRoadSize = road.getElements().size();
        int neededRoadSize = road.getEntityCount();

        if (currentRoadSize < neededRoadSize) {
            if (road.getDirection() == Direction.RIGHT) {
                float offsetX;

                GameElement firstElement = road.getFirstElement();
                if (firstElement == null ||
                        firstElement.getX() > road.getEntityMinDistance()) {

                    // Min entre 0 et firstElement point à gauche
                    if (firstElement != null && firstElement.getX() < 0)
                        offsetX = firstElement.getX();
                    else
                        offsetX = 0;

                    // todo le - road.getEntityMin n'est pas bon dans tous les cas, il faudrait le faire que dans un seul if
                    //  Si l'entité est hors de l'écran, on lui donne l'offset entier
                    //  Si elle est sur l'ecran, on sait qu'il y a déjà la distance minimale grâce au if, donc on peut faire le -
                    offsetX -= (road.getRandomOffsetX() - road.getEntityMinDistance());

                    for (GameElement element : world.generateElement(road)) {
                        offsetX -= element.getWidth();
                        element.getPosition().x = offsetX;
                        road.addElement(element);
                        element.afterInitialisation();
                    }
                }
            }
            else if (road.getDirection() == Direction.LEFT) {
                float offsetX;
                GameElement lastElement = road.getLastElement();
                if (lastElement == null ||
                        (world.getWidth() - (lastElement.getX() - lastElement.getWidth())) > road.getEntityMinDistance()) {

                    // Max entre world.width et lastElement point à droite
                    if (lastElement != null && (lastElement.getX() + lastElement.getWidth()) > world.getWidth())
                        offsetX = lastElement.getX() + lastElement.getWidth();
                    else
                        offsetX = world.getWidth();

                    offsetX += (road.getRandomOffsetX() - road.getEntityMinDistance());

                    for (GameElement element : world.generateElement(road)) {
                        offsetX += element.getWidth();
                        element.getPosition().x = offsetX;
                        road.addElement(element);
                        element.afterInitialisation();
                    }
                }
            }
        }
    }

    @SuppressWarnings("SlowAbstractSetRemoveAll")
    @Override
    public void update(float delta) {
        handleInput();

        for (Road road : world.getRoads()) {
            List<GameElement> removeElementsList = new ArrayList<GameElement>(road.getEntityCount());

            for (GameElement element : road.getElements()) {
                boolean toRemove = update(element, delta);
                if (toRemove)
                    removeElementsList.add(element);
            }

            road.getElements().removeAll(removeElementsList);

            tryToAddElements(road);
        }

        for (GameElement element : world.getElements()) {
            update(element, delta);
        }

        handleCollisions(delta);

        updateFrogger(delta);
    }

}
