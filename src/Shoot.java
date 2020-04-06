import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Shoot extends Sprite {

    public Shoot(){
    }

    public Shoot(int x, int y){
        initShoot(x,y);
    }

    private void initShoot(int x, int y) {

        //insert image file
        File imageSource = new File("src/Pictures/shoot.png");
        Image shootImage = null;
        try {
            shootImage = ImageIO.read(imageSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert shootImage != null;
        //resize the image
        ImageIcon image = new ImageIcon(shootImage.getScaledInstance(Configs.shootWidth, Configs.shootHeight, Image.SCALE_DEFAULT));
        setImage(image.getImage());

        setX(x);

        setY(y);

    }
}
