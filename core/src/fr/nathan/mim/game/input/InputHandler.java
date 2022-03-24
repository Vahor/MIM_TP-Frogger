package fr.nathan.mim.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.nathan.mim.game.controller.WorldController;
import fr.nathan.mim.game.model.type.World;

public class InputHandler implements InputProcessor, GestureDetector.GestureListener {

    private final WorldController worldController;
    private final World world;

    private float touchDelay = 0;

    private Vector2 center;

    public InputHandler(WorldController worldController) {
        this.worldController = worldController;
        this.world           = worldController.getWorld();
    }

    public void resize(float width, float height) {
        center = new Vector2(width / 2, height / 2);
    }

    public void update(float delta) {
        touchDelay += delta;
        float MIN_TOUCH_DELAY = .25f;
        if (touchDelay > MIN_TOUCH_DELAY) {
            touchDelay = 0;
            if (Gdx.input.isTouched()) {
                Vector2 currentPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                handleMovement(
                        center,
                        currentPoint
                );
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT)
            worldController.onLeftPressed();
        if (keycode == Input.Keys.RIGHT)
            worldController.onRightPressed();
        if (keycode == Input.Keys.UP)
            worldController.onUpPressed();
        if (keycode == Input.Keys.DOWN)
            worldController.onDownPressed();

        if (keycode == Input.Keys.D)
            world.setDebug(!world.isDebug());
        if (keycode == Input.Keys.ESCAPE)
            world.setPause(!world.isPause());
        if (keycode == Input.Keys.C)
            world.setCheat(!world.isCheat());
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            worldController.onLeftReleased();
        if (keycode == Input.Keys.RIGHT)
            worldController.onRightReleased();
        if (keycode == Input.Keys.UP)
            worldController.onUpReleased();
        if (keycode == Input.Keys.DOWN)
            worldController.onDownReleased();
        return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (world.isPause()) return false;
        handleMovement(
                center,
                new Vector2(x, y)
        );
        return true;
    }

    private void handleMovement(Vector2 fromPoint, Vector2 toPoint) {
        double angle = 180 / Math.PI * Math.atan2(
                toPoint.x - fromPoint.x,
                toPoint.y - fromPoint.y
        );
        if (angle < 0) angle += 360;

        // Et selon l'angle, on exécute une action

        // On découpe les angles en 4 parties
        // Haut = de 140 à 220
        // Droite = de 40 à 140
        // Gauche = de 220 à 320
        // Bas autrement

        if (angle > 140 && angle < 220) {
            // Haut
            worldController.onUpPressed();
            runNextTick(new Runnable() {
                @Override
                public void run() {
                    worldController.onUpReleased();
                }
            });

        }
        else if (angle > 40 && angle < 140) {
            // Droite
            worldController.onRightPressed();
            runNextTick(new Runnable() {
                @Override
                public void run() {
                    worldController.onRightReleased();
                }
            });
        }
        else if (angle > 220 && angle < 320) {
            // Gauche
            worldController.onLeftPressed();
            runNextTick(new Runnable() {
                @Override
                public void run() {
                    worldController.onLeftReleased();
                }
            });
        }
        else {
            // Bas
            worldController.onDownPressed();
            runNextTick(new Runnable() {
                @Override
                public void run() {
                    worldController.onDownReleased();
                }
            });
        }

    }

    private void runNextTick(final Runnable runnable) {
        World.TIMER.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                runnable.run();
            }
        }, .1f);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(float screenX, float screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
