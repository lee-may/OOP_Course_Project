import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Background extends Sprite {

    public Background(int x,int y) {
        initBackground(x, y);
    }

    private void initBackground(int x, int y){

        this.x = x;
        this.y = y;

        //input player image here to display image in application
        File imageSource = new File("src/Pictures/background.jpg");
        Image backgroundImage = null;
        try{
            backgroundImage = ImageIO.read(imageSource);
        }catch(IOException e){
            e.printStackTrace();
        }
        assert backgroundImage != null;
        ImageIcon image = new ImageIcon(backgroundImage.getScaledInstance(Configs.appWidth,Configs.appHeight,Image.SCALE_DEFAULT));
        setImage(image.getImage());
    }
}
