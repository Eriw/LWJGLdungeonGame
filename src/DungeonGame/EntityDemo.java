/*
 * Main
 */

package DungeonGame;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

/**
 * Shows the use of an entity driven game engine
 *
 * @author Oskar
 */
public class EntityDemo {

    private static long lastFrame;
    public static int WIDTH = 640;
    public static int HEIGHT = 480;

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private static int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }  
    
    private static void checkCollision(Entity e, MoveableEntity m){
         if (m.intersects(e)) {
                
            float ratio = 2;

            if(m.getX() < WIDTH/2 && m.getY() < HEIGHT/2) {
                 m.setDX(0.3*ratio);
             }
            if(m.getX() > WIDTH/2 && m.getY() < HEIGHT/2) {
                 m.setDY(0.4*ratio);
             }
            if(m.getX() > WIDTH/2 && m.getY() > HEIGHT/2) {
                 m.setDX(-0.3*ratio);
             }
            if(m.getX() < WIDTH/2 && m.getY() > HEIGHT/2) {
                 m.setDY(-0.1*ratio);
             }
        }
         
        //float limit = 12;
        if(m.getX() > WIDTH-m.getWidth()/2) {
            m.setDX(0);
            m.setX(WIDTH-m.getWidth()/2);
        }
        if(m.getY() > HEIGHT-m.getHeight()/2) {
            m.setDY(0);
            m.setY(HEIGHT-m.getHeight()/2);
        }
        if(m.getX() < m.getWidth()/2) {
            m.setDX(0);
            m.setX(m.getWidth()/2);
        }
        if(m.getY() < m.getHeight()/2) {
            m.setDY(0);
            m.setY(m.getHeight()/2);
        }
    }
    
    private static void input(MoveableEntity e, RoomGrid rg) {
        
        e.setDX(0);
        e.setDY(0);
        
        float speed = 0.4f;
        
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            e.setDY(-speed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            e.setDY(speed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            e.setDX(-speed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            e.setDX(speed);
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            rg.gerenrateNewGrid(15);
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_M)) {
            rg.showFullMap = true;
        }else{
            rg.showFullMap = false;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
            Display.destroy();
            System.exit(0);
        }
        
    }

    public static void main(String[] args) {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setTitle("Entity Demo");
            Display.create();
        } catch (LWJGLException e) {
            Display.destroy();
            System.exit(1);
        }

        // Initialization code Entities
        MoveableEntity player = new Player(100, 100, 20, 20);
        Entity point = new Point(10, 10);
        
        RoomGrid rg = new RoomGrid(11, 9, HEIGHT);
        Room room;// = rg.getActiveRoom();
        //Room room = new Room(HEIGHT, HEIGHT, 0, 0);

        // Initialization code OpenGL
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        lastFrame = getTime();

        while (!Display.isCloseRequested()) {
            // Render

            point.setLocation(Mouse.getX(), HEIGHT - Mouse.getY() - 1);

            glClear(GL_COLOR_BUFFER_BIT);

            int delta = getDelta();
            
            // Update
            player.update(delta);
            point.update(delta);

            // Check keys
            input(player, rg);
            
            // Check collisions
            checkCollision(point,player);
            rg.handleRoomCollision(player);
            room = rg.getActiveRoom();

            // Render
            room.draw();
            point.draw();
            player.draw();
            rg.drawMiniMap(WIDTH, HEIGHT);

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
        System.exit(0);
    }
}
