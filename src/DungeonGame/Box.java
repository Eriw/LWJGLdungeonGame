/*
 * Box
 */
package DungeonGame;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Wirebrand
 */
public class Box extends AbstractMovableEntity {
    
    public Box(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void draw() {
        glColor3f(1f, 1f, 1f);
        glRectd(x - width/2, y - height/2, x + width/2, y + height/2);
    }

}

