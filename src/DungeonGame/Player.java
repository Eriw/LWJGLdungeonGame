/*
 * Player
 */
package DungeonGame;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Wirebrand
 */
public class Player extends AbstractMovableEntity{
    
    public Player(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void draw() {
        glColor3f(1f, 0.5f, 0.3f);
        glRectd(x - width/2, y - height/2, x + width/2, y + height/2);
    }
}



