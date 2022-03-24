package fr.nathan.mim.game.controller;

import com.badlogic.gdx.graphics.g2d.Batch;
import fr.nathan.mim.game.Client;
import fr.nathan.mim.game.CollideResult;
import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.Road;
import fr.nathan.mim.game.model.type.World;
import fr.nathan.mim.game.screen.GameOverScreen;
import fr.nathan.mim.game.texture.TextureFactory;
import fr.nathan.mim.game.texture.type.FroggerTexture;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorldController extends Controller {

    private final Map<Keys, Boolean> pressedKeys = new HashMap<Keys, Boolean>(4, 1);

    public WorldController(World world, Batch batch) {
        super(world, batch);

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
        Frogger frogger = world.getFrogger();
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

    private boolean handleBorders(MovingEntity element, float delta) {
        if (element.isUseDirectionForBorders()) {
            if ((element.getDirection() == Direction.LEFT && element.getX() < -element.getWidth())
                    || (element.getDirection() == Direction.RIGHT && element.getX() > world.getWidth())
                    || element.getY() > world.getHeight() - element.getHeight()
                    || element.getY() < 0
            ) {
                element.whenOutOfBorder(world, delta);
                return true;
            }
        }
        else {
            if ((element.getX() < -element.getWidth())
                    || (element.getX() > world.getWidth())
                    || element.getY() > world.getHeight() - element.getHeight()
                    || element.getY() < 0
            ) {
                element.whenOutOfBorder(world, delta);
                return true;
            }
        }

        return false;
    }

    private void handleBordersFrogger(Frogger element, float delta) {
        if (element.getX() < 0 ||
                element.getX() > world.getWidth() - element.getWidth() ||
                element.getY() > world.getHeight() - element.getHeight() ||
                element.getY() < 0
        ) {
            element.whenOutOfBorder(world, delta);
        }
    }

    private void onFroggerDie() {
        if (world.isCheat()) return;
        Frogger frogger = world.getFrogger();
        frogger.onDied();
        world.setRemainingLives(world.getRemainingLives() - 1);
        if (world.getRemainingLives() <= 0) {
            Client.getInstance().setScreen(new GameOverScreen(world, batch));
            return;
        }
        teleportFroggerToSpawn();
    }

    private void onFroggerRide(MovingEntity element) {
        Frogger frogger = world.getFrogger();
        frogger.getVelocity().set(element.getVelocity());
    }

    private void teleportFroggerToSpawn() {
        Frogger frogger = world.getFrogger();
        frogger.setDirection(Direction.UP);
        frogger.getPosition().set(frogger.getStartingPosition());
    }

    private void blockFrogger(GameElement element) {
        Frogger frogger = world.getFrogger();
        Direction direction = frogger.getDirection();
        Direction opposite = direction.opposite();
        frogger.getPosition().y += opposite.getMotY() * element.getHeight();
        frogger.getPosition().x += opposite.getMotX() * element.getWidth();
    }

    /**
     * @param collideResult Le résultat de la collision.
     * @param element L'élément avec lequel le Frogger est entré en collision.
     * @return True si l'action empêche la suite d'action
     */
    private boolean resultCollideWith(CollideResult collideResult, GameElement element) {

        if (collideResult == CollideResult.BLOCK) {
            blockFrogger(element);
            return true;
        }

        if (collideResult == CollideResult.DEAD) {
            System.out.println(":( element " + element.getClass());
            onFroggerDie();
            return true;
        }

        if (collideResult == CollideResult.RIDE && element instanceof MovingEntity) {
            onFroggerRide((MovingEntity) element);
            return true;
        }

        if (collideResult == CollideResult.WIN) {
            onFroggerWin();
            return true;
        }
        return false;
    }

    private void handleCollisions(float delta) {
        Frogger frogger = world.getFrogger();
        if (frogger.getState() != Frogger.State.IDLE) return;

        for (Road road : world.getRoads()) {
            for (GameElement element : road.getElements()) {
                CollideResult collideResult = element.handleCollision(frogger, delta);
                boolean b = resultCollideWith(collideResult, element);
                if (b) return;
            }
        }

        for (GameElement element : world.getElements()) {
            CollideResult collideResult = element.handleCollision(frogger, delta);
            boolean b = resultCollideWith(collideResult, element);
            if (b) return;
        }

        // Handle water
        for (Road road : world.getRoads()) {
            if (road.getType().isDangerous()) {
                if (road.collideWith(frogger)) {
                    System.out.println(":( water");
                    onFroggerDie();
                    break;
                }
            }
        }

    }

    private void onFroggerWin() {
        teleportFroggerToSpawn();
        world.setScore(world.getScore() + 100);
        world.setSuccessMessageTime(3);

        for (GameElement element : world.getElements()) {
            if (element instanceof MovingEntity) {
                ((MovingEntity) element).updateVelocity();
            }
        }

        for (Road road : world.getRoads()) {
            for (GameElement element : road.getElements()) {
                if (element instanceof MovingEntity) {
                    ((MovingEntity) element).updateVelocity();
                }
            }
        }

    }

    private boolean update(GameElement element, float delta) {
        element.update(delta);
        if (element instanceof MovingEntity) {
            return handleBorders((MovingEntity) element, delta);
        }
        return false;
    }

    private void updateFrogger(float delta) {
        Frogger frogger = world.getFrogger();
        frogger.update(delta);
        handleBordersFrogger(frogger, delta);
    }

    private void tryToAddElements(Road road) {

        int currentRoadSize = road.getElements().size();
        int neededRoadSize = road.getEntityCount();

        if (currentRoadSize < neededRoadSize) {
            float offsetX;
            if (road.getDirection() == Direction.RIGHT) {

                GameElement firstElement = road.getFirstElement();
                if (firstElement == null ||
                        firstElement.getX() > road.getEntityMinDistance()) {

                    // Min entre 0 et firstElement point à gauche
                    if (firstElement != null && firstElement.getX() < 0) {
                        offsetX = firstElement.getX();
                    }
                    else
                        offsetX = 0;

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
                GameElement lastElement = road.getLastElement();
                if (lastElement == null ||
                        (world.getWidth() - (lastElement.getX() + lastElement.getWidth())) > road.getEntityMinDistance()) {

                    // Max entre world.width et lastElement point à droite
                    if (lastElement != null && (lastElement.getX() + lastElement.getWidth()) > world.getWidth()) {
                        offsetX = lastElement.getX();
                    }
                    else {
                        offsetX = world.getWidth();
                    }
                    offsetX += (road.getRandomOffsetX() - road.getEntityMinDistance());


                    for (GameElement element : world.generateElement(road)) {
                        element.getPosition().x = offsetX;
                        road.addElement(element);
                        element.afterInitialisation();
                        offsetX += element.getWidth();
                    }
                }
            }
        }
    }

    private void checkAndUpdateTime(float delta) {
        if (world.getCurrentTime() <= 0) {
            Client.getInstance().setScreen(new GameOverScreen(world, batch));
            return;
        }

        world.setCurrentTime(world.getCurrentTime() - delta);
    }

    @Override
    public void update(float delta) {

        if (!world.isPause()) {
            handleInput();

            for (Road road : world.getRoads()) {
                Set<GameElement> removeElementsList = new HashSet<GameElement>(road.getEntityCount(), 1);

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

            checkAndUpdateTime(delta);

        }
    }

    enum Keys {
        LEFT, RIGHT, UP, DOWN
    }

}
