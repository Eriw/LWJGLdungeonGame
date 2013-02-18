/*
 * Movable Entity
 */

package DungeonGame;

public abstract class AbstractMovableEntity extends AbstractEntity implements
        MoveableEntity {

    protected float dx, dy;

    public AbstractMovableEntity(float x, float y, float width,float height) {
        super(x, y, width, height);
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public void update(int delta) {
        this.x += delta * dx;
        this.y += delta * dy;
    }

    public float getDX() {
        return dx;
    }

    public float getDY() {
        return dy;
    }

    public void setDX(float dx) {
        this.dx = dx;
    }

    public void setDY(float dy) {
        this.dy = dy;
    }

    @Override
    public abstract void draw();
}
