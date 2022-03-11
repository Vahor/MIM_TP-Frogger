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

        public int getId() {
            return id;
        }
    }

    private Type type = Type.SMALL;

    public Type getType() {
        return type;
    }

    @Override
    public float getWidth() {
        return type.width;
    }

    @Override
    public float getHeight() {
        return .8f;
    }

    @Override
    public void afterDeserialization() {
        super.afterDeserialization();
        updateVelocity();
    }

    @Override
    public boolean onCollide(Frogger frogger, float delta) {

        frogger.getVelocity().set(getVelocity());
        return false;
    }

}
