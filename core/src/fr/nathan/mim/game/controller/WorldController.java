package fr.nathan.mim.game.controller;

import fr.nathan.mim.game.Direction;
import fr.nathan.mim.game.TextureFactory;
import fr.nathan.mim.game.model.GameElement;
import fr.nathan.mim.game.model.MovingEntity;
import fr.nathan.mim.game.model.type.Frogger;
import fr.nathan.mim.game.model.type.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            frogger.setFacingDirection(Direction.LEFT);
        }
        else if (isRightPressed()) {
            frogger.setFacingDirection(Direction.RIGHT);
        }
        else if (isUpPressed()) {
            frogger.setFacingDirection(Direction.UP);
        }
        else if (isDownPressed()) {
            frogger.setFacingDirection(Direction.DOWN);
        }
        else {
            return;
        }

        float animationDuration = TextureFactory.getInstance().getJumpingFrogger().getAnimationDuration();

        frogger.setState(Frogger.State.JUMPING);
        frogger.getVelocity().x += frogger.getFacingDirection().getMotX() * (frogger.getSpeed() / animationDuration);
        frogger.getVelocity().y += frogger.getFacingDirection().getMotY() * (frogger.getSpeed() / animationDuration);
    }

    private void handleBorders(MovingEntity element) {
        if (element.getFacingDirection() == Direction.LEFT && element.getX() < -element.getWidth() ||
                element.getFacingDirection() == Direction.RIGHT && element.getX() > WorldRenderer.CAMERA_WIDTH
        ) {
            element.whenOutOfBorder();
        }
    }

    private void handleCollisions() {

        int y = Math.round(world.getFrogger().getY());
        Set<GameElement> elementsInRow = world.getElementsInRow(y - 1);
        if (elementsInRow == null) return;

        for (GameElement gameElement : elementsInRow) {
            if (gameElement.collideWith(frogger)) {
                gameElement.onCollide();
            }
        }

    }

    @Override
    public void update(float delta) {
        handleInput();

        for (Set<GameElement> value : world.getElementsByRow().values()) {
            for (GameElement element : value) {
                element.update(delta);
                if (element instanceof MovingEntity) {
                    handleBorders((MovingEntity) element);
                }
            }
        }
        handleCollisions();

        frogger.update(delta);
    }
}
