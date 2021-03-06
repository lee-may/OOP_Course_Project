import java.awt.Image;


public class Sprite {
    private boolean visible;
    private Image image;
    private boolean dying;
    private boolean destroyed;

    int x;
    int y;
    int dx;

    public Sprite(){
        visible = true;
    }

    public void die(){
        visible = false;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y=y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public Image getImage(){
        return image;
    }

    public void setDying(boolean dying){
        this.dying = dying;
    }

    public boolean isDying(){
        return this.dying;
    }

    public void setDestroyed(boolean destroyed){
        this.destroyed = destroyed;
    }

    public boolean isDestroyed(){
        return destroyed;
    }
}
