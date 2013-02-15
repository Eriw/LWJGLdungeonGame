/*
 * point
 */
package DungeonGame;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Wirebrand
 */
public class Point extends AbstractEntity {

        public Point(double x, double y) {
            super(x, y, 1, 1);
        }

        @Override
        public void draw() {
            glBegin(GL_POINTS);
            glVertex2d(x, y);
            glEnd();
        }

        @Override
        public void update(int delta) {
            // Do nothing
        }
    }
