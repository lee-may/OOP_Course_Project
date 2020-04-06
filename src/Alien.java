import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Alien extends Sprite {

    private Bomb bomb;

    public Alien(int x, int y){
        initAlien(x,y);
    }

    private void initAlien(int x, int y){

        this.x=x;
        this.y=y;

        bomb = new Bomb(x,y);

        //input the alien image
        File imageSource = new File("src/Pictures/alien.png");
        Image alienImage = null;
        try{
            alienImage = ImageIO.read(imageSource);
        }catch(IOException e){
            e.printStackTrace();
        }
        assert alienImage != null;
        ImageIcon image = new ImageIcon(alienImage.getScaledInstance(Configs.alienWidth,Configs.alienHeight,Image.SCALE_DEFAULT));
        setImage(image.getImage());
    }

    public void act(int direction) {

        this.x += direction;
    }

    public Bomb getBomb(){
        return bomb;
    }

    public static class Bomb extends Sprite{

        public Bomb(int x, int y){
            initBomb(x,y);
        }

        private void initBomb(int x, int y){

           setDestroyed(true);

            this.x = x;
            this.y = y;

            File imageSource = new File("src/Pictures/bomb.png");
            Image explosionImage = null;
            try{
                explosionImage = ImageIO.read(imageSource);
            }catch(IOException e){
                e.printStackTrace();
            }
            assert explosionImage != null;
            ImageIcon image = new ImageIcon(explosionImage.getScaledInstance(Configs.alienWidth,Configs.alienHeight,Image.SCALE_DEFAULT));
            setImage(image.getImage());



        }
    }
}
