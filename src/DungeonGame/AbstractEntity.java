/*
 * Entity
 */

package DungeonGame;

import java.awt.*;

public abstract class AbstractEntity implements Entity {

    protected float x, y, width, height;
    protected Rectangle hitbox = new Rectangle();

    public AbstractEntity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public boolean intersects(Entity other) {
        hitbox.setBounds((int)(x - width/2), (int) (y-height/2), (int) width, (int) height);
        return hitbox.intersects(other.getX(), other.getY(), other.getWidth(), other.getHeight());
    }

}
