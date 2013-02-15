/*
 * Coin
 */
package DungeonGame;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Wirebrand
 */
public class Coin extends AbstractEntity {

        float white = 0f;
        float colorVector = 0.05f;
    
        public Coin(double x, double y, float size) {
            super(x, y, size, size);
        }

        @Override
        public void draw() {
            
            white += colorVector;
            
            if(white > 0.8f){
                colorVector = -0.05f;
            }
            if(white < 0.1f){
                colorVector = 0.05f;
            }
            
            width = height*white;
            
            glColor3f(1f, 1f, white);
            glRectd(x - width/2, y - height/2, x + width/2, y + height/2);
        }
        
        public boolean checkCollision(MoveableEntity p){
            boolean col = false;
            
            if( Math.abs( p.getX() - x ) < p.getWidth()/2 + width/2 && Math.abs( p.getY() - y ) < p.getHeight()/2 + height/2){
                col = true;
            }
            
            
            return col;
        }
        

        @Override
        public void update(int delta) {
            // Do nothing
        }
    }

