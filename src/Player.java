import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Sprite {

    public Player(int x,int y) {
        initPlayer(x, y);
    }

    private void initPlayer(int x, int y){

        this.x = x;
        this.y = y;

        //input player image here to display image in application
        File imageSource = new File("src/Pictures/player.png");
        Image playerImage = null;
        try{
            playerImage = ImageIO.read(imageSource);
        }catch(IOException e){
            e.printStackTrace();
        }
        assert playerImage != null;
        ImageIcon image = new ImageIcon(playerImage.getScaledInstance(Configs.playerWidth,Configs.playerHeight,Image.SCALE_DEFAULT));
        setImage(image.getImage());
    }

    //direction to let the player move
    public void act(){

        x = x+dx;

        //it will stop when player move left to the maximum
        if (x <= 2){
            x=2;
        }

        //it will stop when player move right to the maximum
        if(x >= Configs.appWidth - 65 ){

            x = Configs.appWidth - 65 ;
        }
    }

    //methods to receive input from keyboard
    //This event is generated when a key is pushed down.
    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT){
            dx = -2;
        }

        if(key == KeyEvent.VK_RIGHT){
            dx=2;
        }
    }

    //This event is generated when a key is let up.
    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT){
            dx = 0;
        }

        if(key == KeyEvent.VK_RIGHT){
            dx=0;
        }

    }
}
