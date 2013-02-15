/*
 * RoomGrid
 */
package DungeonGame;

import java.util.ArrayList;
import java.util.Random;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Wirebrand
 */
public class RoomGrid {
    
    ArrayList<Room> rooms = new ArrayList<>();
    Room activeR;
    private int WIDTH = 0;
    private int gridSize = 15;
    private int totRooms = 0;
    private int maxRooms = 0;
    
    public boolean showFullMap = false;
    
    
    public RoomGrid(int n, int size, int height){
       
       maxRooms = n;
        
       WIDTH = height;
       
       gridSize = size;
       
       generateRooms();
       
       findConnectingRooms();
        
       activeR = rooms.get(0);
    }
    
public boolean checkForRoom(int x,int y){
    
    boolean b = false;
    
    for(int i = 0; i < rooms.size(); ++i){
            
        Room r1 = rooms.get(i);

        if(r1.getX() == x && r1.getY() == y){
            b = true;
        }

    }

    return b;
    
}

public boolean findRooms(int x,int y){
    
    boolean b = false;
    
    for(int i = 0; i < rooms.size(); ++i){
            
        Room r = rooms.get(i);
        
        if( ( r.getX() == x+1 && r.getY() == y ) || ( r.getX() == x-1 && r.getY() == y ) || ( r.getX() == x && r.getY() == y+1 ) || ( r.getX() == x && r.getY() == y-1 ) || ( r.getX() == x && r.getY() == y )){
            r.found = true;
        }

    }

    return b;
    
}
    
public void generateRooms(){
    
    int nrRooms = maxRooms;
    
    if( nrRooms > (gridSize-2)*(gridSize-2)/2 && gridSize > 2){
        nrRooms = (gridSize-2)*(gridSize-2)/2;
    }else if(gridSize < 2){
        nrRooms = 0;
    }

        
    Room r = new Room(WIDTH, WIDTH, gridSize/2, gridSize/2);
    rooms.add(r);
    totRooms += 1;
    
    while(totRooms < nrRooms){
        
        Random rand = new Random();
        
        boolean placingRoom = true;
        while(placingRoom){
        
            int x = rand.nextInt(gridSize); 
            int y = rand.nextInt(gridSize);

            if( !checkForRoom(x, y) ){
               int sum = 0;

               if( checkForRoom(x+1, y) ){
                    sum += 1;
               }
               if( checkForRoom(x-1, y) ){
                    sum += 1;
               }
               if( checkForRoom(x, y+1) ){
                    sum += 1;
               }
               if( checkForRoom(x, y-1) ){
                    sum += 1;
               }

               if (sum > 0 && sum < 3 && totRooms < nrRooms - 1){
                    r = new Room(WIDTH, WIDTH, x, y);
                    rooms.add(r);
                    totRooms += 1;
               }else if(sum == 1 && totRooms == nrRooms - 1){
                    r = new Room(WIDTH, WIDTH, x, y);
                    r.type = 1;
                    r.ch.coins.clear();                         
                    rooms.add(r);
                    totRooms += 1;
                    placingRoom = false;
               }
             
            }
           
        }
    }
}
    
    public void findConnectingRooms(){
        
        for(int i = 0; i < rooms.size(); ++i){
            
            Room r1 = rooms.get(i);
            
            for(int j = 0; j < rooms.size(); ++j){
        
                Room r2 = rooms.get(j);
                
                int x1 = r1.getX();
                int y1 = r1.getY();
                int x2 = r2.getX();
                int y2 = r2.getY();
                
                if( x1 == x2){
                    
                    if(y1 - 1 == y2){
                        r1.doorN = true;
                    }else if(y1 + 1 == y2){
                        r1.doorS = true;
                    }
                    
                }else if(y1 == y2){
                
                    if(x1 - 1 == x2){
                        r1.doorW = true;
                    }else if(x1 + 1 == x2){
                        r1.doorE = true;
                    }
                    
                }
            
            }
        }
        
    }
    
    public void drawMiniMap(int width, int height){
        
        float delta = (float)(width - height)/(gridSize+4);
        float drawPosX = height + delta*2;
        float drawPosY = delta*2;
        
        float border = 2.0f;
        glColor3f(0.9f, 0.1f, 0.1f);
        glRectd( drawPosX-border, drawPosY-border, drawPosX + delta*(gridSize) + border, drawPosY + delta*(gridSize) + border);
        glColor3f(0.1f, 0.1f, 0.1f);
        glRectd( drawPosX, drawPosY, drawPosX + delta*(gridSize), drawPosY + delta*(gridSize));
        glColor3f(0.9f, 0.9f, 0.9f);
        
        float x,y;
        Room r1;
        for(int i = 0; i < rooms.size(); ++i){
            
            r1 = rooms.get(i);
            
            x = drawPosX + r1.getX()*delta;
            y = drawPosY + r1.getY()*delta;
            
            
            if(r1.type == 1){
                glColor3f(0.2f, 0.2f, 1.0f);
            }else{
                glColor3f(0.9f, 0.9f, 0.9f);
            }
            
            if(showFullMap){
                glRectd( x, y, x + delta, y + delta);
            }else if(r1.found){
                
                if(r1.alpha < 1.0f){
                    
                    if(r1.type == 0){
                        glColor3f(0.2f + 0.7f*r1.alpha, 0.2f + 0.7f*r1.alpha, 0.2f + 0.8f*r1.alpha );
                    }else if(r1.type == 1){
                        glColor3f(0.2f, 0.2f, 0.2f + 0.7f*r1.alpha );
                    }
                    r1.alpha += 0.02;
                }
                
                glRectd( x, y, x + delta, y + delta);
                
            }
            
            
           
        }
        
        r1 = getActiveRoom();
        x = drawPosX + r1.getX()*delta;
        y = drawPosY + r1.getY()*delta;
        
        glColor3f(0.9f, 0.2f, 0.1f);       
        glRectd( x, y, x + delta, y + delta);
       /* glColor3f(0.9f, 0.9f, 0.9f);       
        glRectd( x + 1f, y + 1f, x + delta - 2f, y + delta - 2f);*/
    }
    
     public void gerenrateNewGrid(){
        
        if(rooms.size() > 0){
            rooms.clear();
        }
        
        totRooms = 0;
        
       generateRooms();
       
       findConnectingRooms();
        
       activeR = rooms.get(0);
    }
    
    public void handleRoomCollision(MoveableEntity m){
        
        findRooms(activeR.getX(),activeR.getY());
        
        if(activeR.type == 1 && remainingCoins() == 0){
        
            if( Math.abs(m.getX() - WIDTH/2) < activeR.DOOR_WIDTH + m.getWidth()/2  && Math.abs(m.getY() - WIDTH/2) < activeR.DOOR_WIDTH + m.getHeight()/2){
                m.setX(WIDTH/2);
                m.setY(WIDTH/2);
                
                gerenrateNewGrid();
            }
                        
        }
        
        if(m.getX() - m.getWidth()/2 < activeR.BORDER){
            
            if(activeR.doorW){
                if(m.getY() > activeR.HEIGHT/2 - activeR.DOOR_WIDTH + m.getHeight()/2 &&  m.getY() < activeR.HEIGHT/2 + activeR.DOOR_WIDTH - m.getHeight()/2 ){
                    
                    setActiveRoom(activeR.getX() - 1, activeR.getY());
                    if(remainingCoins() > 0 && activeR.type == 1){
                        setActiveRoom(activeR.getX() + 1, activeR.getY());
                        m.setX(activeR.BORDER + m.getWidth()/2);
                    }else{
                        m.setX(activeR.WIDTH - activeR.BORDER*2);
                        m.setY(activeR.WIDTH/2);
                    }
                    
                }else{
                    m.setX(activeR.BORDER + m.getWidth()/2);
                }
            }else{
                m.setX(activeR.BORDER + m.getWidth()/2);
            }
            
        }
        if(m.getY() - m.getHeight()/2 < activeR.BORDER){
            
            if(activeR.doorN){
                if(m.getX() > activeR.WIDTH/2 - activeR.DOOR_WIDTH + m.getWidth()/2 &&  m.getX() < activeR.WIDTH/2 + activeR.DOOR_WIDTH - m.getWidth()/2 ){
                    
                    setActiveRoom(activeR.getX(), activeR.getY() - 1);
                    if(remainingCoins() > 0 && activeR.type == 1){
                        setActiveRoom(activeR.getX(), activeR.getY() + 1);
                        m.setY(activeR.BORDER + m.getWidth()/2);
                    }else{
                        m.setX(activeR.WIDTH/2);
                        m.setY(activeR.WIDTH - activeR.BORDER*2);
                    }
                    
                }else{
                    m.setY(activeR.BORDER + m.getHeight()/2);
                }
            }else{
                m.setY(activeR.BORDER + m.getHeight()/2);
            }
            
        }
        if(m.getX() + m.getWidth()/2 > activeR.WIDTH - activeR.BORDER){
            
            if(activeR.doorE){
                if(m.getY() > activeR.HEIGHT/2 - activeR.DOOR_WIDTH + m.getHeight()/2 &&  m.getY() < activeR.HEIGHT/2 + activeR.DOOR_WIDTH - m.getHeight()/2 ){
                    
                    setActiveRoom(activeR.getX() + 1, activeR.getY());
                    if(remainingCoins() > 0 && activeR.type == 1){
                        setActiveRoom(activeR.getX() - 1, activeR.getY());
                        m.setX(activeR.WIDTH - activeR.BORDER - m.getWidth()/2);
                    }else{
                       m.setX(activeR.BORDER*2);
                       m.setY(activeR.WIDTH/2);
                    }
                                       
                }else{
                    m.setX(activeR.WIDTH - activeR.BORDER - m.getWidth()/2);
                }
            }else{
                m.setX(activeR.WIDTH - activeR.BORDER - m.getWidth()/2);
            }
            
            
        }
        if(m.getY() + m.getHeight()/2 > activeR.HEIGHT - activeR.BORDER){
            
            if(activeR.doorS){
                if(m.getX() > activeR.WIDTH/2 - activeR.DOOR_WIDTH + m.getWidth()/2 &&  m.getX() < activeR.WIDTH/2 + activeR.DOOR_WIDTH - m.getWidth()/2 ){
                    
                    setActiveRoom(activeR.getX(), activeR.getY() + 1);
                    if(remainingCoins() > 0 && activeR.type == 1){
                        setActiveRoom(activeR.getX(), activeR.getY() - 1);
                        m.setY(activeR.WIDTH - activeR.BORDER - m.getWidth()/2);
                    }else{
                        m.setX(activeR.WIDTH/2);
                        m.setY(activeR.BORDER*2);
                    }
                    
                }else{
                    m.setY(activeR.HEIGHT - activeR.BORDER - m.getHeight()/2);
                }
            }else{
                m.setY(activeR.HEIGHT - activeR.BORDER - m.getHeight()/2);
            }
            
        }
        
        activeR.ch.handleCollisions(m);
        
    }
    
    public Room getActiveRoom(){
        return activeR;
    }
    
    public void setActiveRoom(int x, int y){
        
        for(int i = 0; i < rooms.size(); ++i){
            
            Room r1 = rooms.get(i);
            
            if(r1.getX() == x && r1.getY() == y){
                activeR = r1;
            }

        }
        
    }
    
    public void drawRoom(){
        activeR.draw();
        
        if(remainingCoins() == 0){
            activeR.drawTrapDoor();
        }
    }
    
    public int remainingCoins(){
        
        int count = 0;
        for(int i = 0; i < rooms.size(); ++i){
            
            Room r1 = rooms.get(i);
            count += r1.ch.coins.size();

        }
        
        return count;
        
    }
    
   
    
}
