/*
 * Coin Handler
 */
package DungeonGame;

import java.util.ArrayList;
import java.util.Random;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Wirebrand
 */
public class CoinHandler {
    
    ArrayList<Coin> coins = new ArrayList<>();
    
    public CoinHandler(int n, float min, float max){
        
        Random rand = new Random();
        
        float size = 10f;
        
        for(int i = 0; i < n; ++i){
            Coin c = new Coin(rand.nextFloat()*(max-size*2-min) + min, rand.nextFloat()*(max-size*2-min) + min, size);
            coins.add(c);
        }   
        
    }
    
    public void drawCoins(){
        
        for(int i = 0; i < coins.size(); ++i){
            Coin c = coins.get(i);
            c.draw();
        }
        
    }
    
    public void handleCollisions(MoveableEntity p){
        
        for(int i = 0; i < coins.size(); ++i){
            Coin c = coins.get(i);
            if(c.checkCollision(p)){
                coins.remove(c);
            }
        }
        
    }
    
}
