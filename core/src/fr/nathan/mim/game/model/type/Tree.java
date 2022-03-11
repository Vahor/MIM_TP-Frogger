package fr.nathan.mim.game.model.type;

import fr.nathan.mim.game.model.MovingEntity;

public class Tree extends MovingEntity {

    public enum Type {
        SMALL(0, 2),
        MEDIUM(1, 2),
        LARGE(1, 2),

        ;

        private final int id;
        private final float width;

        private static final Type[] values = values();

        Type(int id, float width) {
            this.id   = id;
            this.width = width;
        }
    }

    private Type type;

    @Override
    public float getWidth() {
        return type.width;
    }

    @Override
    public float getHeight() {
        return .8f;
    }

    @Override
    public boolean onCollide(Frogger frogger, float delta) {
        System.out.println("Tree.onCollide");
        return false;
    }

}
