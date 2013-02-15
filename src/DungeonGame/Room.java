/*
 * Room
 */
package DungeonGame;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Wirebrand
 */
public class Room {
    
    public int WIDTH,HEIGHT,BORDER;
    
    private int x, y;
    
    public boolean doorN = false;
    public boolean doorE = false;
    public boolean doorS = false;
    public boolean doorW = false;
    
    public int type = 0;
    public float alpha = 0;
    
    public boolean found = false;
    
    public Room(int height, int width, int x, int y){
        WIDTH = width;
        HEIGHT = height;
        BORDER = (int)( (float)height*0.05f);
        this.x = x;
        this.y = y;
    }
    
    public void draw(){
        glColor3f(0.7f, 0.7f, 0.7f);
        glRectd(0,0,WIDTH,HEIGHT);
        glColor3f(0.2f, 0.2f, 0.2f);
        glRectd(BORDER,BORDER,WIDTH-BORDER,HEIGHT-BORDER);
        
        glColor3f(0.5451f, 0.2706f, 0.0745f);
        if(doorN){
            glRectd(WIDTH/2 - BORDER, 0, WIDTH/2 + BORDER, BORDER);
        }
        if(doorE){
            glRectd(WIDTH - BORDER, HEIGHT/2 -BORDER, WIDTH, HEIGHT/2 + BORDER);
        }
        if(doorS){
            glRectd(WIDTH/2 - BORDER, HEIGHT - BORDER, WIDTH/2 + BORDER, HEIGHT);
        }
        if(doorW){
            glRectd(0, HEIGHT/2 -BORDER, BORDER, HEIGHT/2 + BORDER);
        }
        
    }
    
    public void handleRoomCollision(MoveableEntity m){
        
        if(m.getX() - m.getWidth()/2 < BORDER){
            
            if(doorW){
                if(m.getY() > HEIGHT/2 - BORDER + m.getHeight()/2 &&  m.getY() < HEIGHT/2 + BORDER - m.getHeight()/2 ){
                    m.setX(WIDTH/2);
                    m.setY(WIDTH/2);
                }else{
                    m.setX(BORDER + m.getWidth()/2);
                }
            }else{
                m.setX(BORDER + m.getWidth()/2);
            }
            
        }
        if(m.getY() - m.getHeight()/2 < BORDER){
            
            if(doorN){
                if(m.getX() > WIDTH/2 - BORDER + m.getWidth()/2 &&  m.getX() < WIDTH/2 + BORDER - m.getWidth()/2 ){
                    m.setX(WIDTH/2);
                    m.setY(WIDTH/2);
                }else{
                    m.setY(BORDER + m.getHeight()/2);
                }
            }else{
                m.setY(BORDER + m.getHeight()/2);
            }
            
        }
        if(m.getX() + m.getWidth()/2 > WIDTH - BORDER){
            
            if(doorE){
                if(m.getY() > HEIGHT/2 - BORDER + m.getHeight()/2 &&  m.getY() < HEIGHT/2 + BORDER - m.getHeight()/2 ){
                    m.setX(WIDTH/2);
                    m.setY(WIDTH/2);
                }else{
                    m.setX(WIDTH - BORDER - m.getWidth()/2);
                }
            }else{
                m.setX(WIDTH - BORDER - m.getWidth()/2);
            }
            
            
        }
        if(m.getY() + m.getHeight()/2 > HEIGHT - BORDER){
            
            if(doorN){
                if(m.getX() > WIDTH/2 - BORDER + m.getWidth()/2 &&  m.getX() < WIDTH/2 + BORDER - m.getWidth()/2 ){
                    m.setX(WIDTH/2);
                    m.setY(WIDTH/2);
                }else{
                    m.setY(HEIGHT - BORDER - m.getHeight()/2);
                }
            }else{
                m.setY(HEIGHT - BORDER - m.getHeight()/2);
            }
            
        }
        
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
}
