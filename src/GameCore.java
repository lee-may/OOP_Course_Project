import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameCore extends JPanel{

    //creating list of aliens
    private List<Alien> aliens;
    private boolean inGame = true;
    private int direction = -1;
    private int deaths = 0;
    private String message = "Game Over";

    private Timer timer;
    private Background background;
    private Player player;
    private Shoot shoot;

    public GameCore(){

        initBoard();
        gameInit();
    }

    //to initialize the application
    private void initBoard(){

        //to receive the key pressed
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);

        timer = new Timer(Configs.delay,new GameCycle());
        timer.start();

        gameInit();
    }

    private void gameInit(){

        //declare the classes
        background = new Background(0,0);
        aliens = new ArrayList<>();

        //to output 4 rows of aliens
        for(int i=0;i<4;i++){
            for(int j=0;j<6;j++){
                Alien alien = new Alien(Configs.alienX+50*j,Configs.alienY+50*i);
                //adding alien to the list
                aliens.add(alien);
            }
        }

        player = new Player(Configs.playerX,Configs.playerY);
        shoot = new Shoot();

    }

    //initialize the image
    private void drawBackground(Graphics g){

            g.drawImage(background.getImage(),background.getX(),background.getY(),this);

    }

    private void drawAliens(Graphics g){
        //transversing the list
        for(Alien alien : aliens){
            if(alien.isVisible()){
                g.drawImage(alien.getImage(),alien.getX(),alien.getY(),this);

            }

            if(alien.isDying()){
                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g){
        if(player.isVisible()){
            g.drawImage(player.getImage(),player.getX(),player.getY(),this);
        }

        if(player.isDying()){
            player.die();
            inGame = false;
        }
    }

    private void drawShoot(Graphics g){
        if(shoot.isVisible()){
            g.drawImage(shoot.getImage(),shoot.getX(),shoot.getY(),this);
        }
    }

    private void drawBomb(Graphics g){

        for(Alien a : aliens){
            Alien.Bomb b = a.getBomb();

            //because isDestroyed return true, so must put ! in front
            if(!b.isDestroyed()){
                g.drawImage(b.getImage(),b.getX(),b.getY(),this);
            }
        }
    }

    private void gameOver(Graphics g){

        //box for the word Game over
        g.setColor(new Color(0,32,48));
        g.fillRect(Configs.boardWidth/2+125,Configs.boardHeight/2+30, Configs.boardWidth-100,50);
        //the border for the box
        g.setColor(Color.white);
        g.drawRect(Configs.boardWidth/2+125,Configs.boardHeight/2+30,Configs.boardWidth-100,50);

        //set the font style
        Font small = new Font("Times New Roman",Font.BOLD,14);
        //FontMetrics fontMetrics = this.getFontMetrics(small);

        //display the text
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message,Configs.boardWidth/2+210,Configs.boardWidth/2+55);
    }


    //implement the draw function to the DoDrawing
    private void doDrawing(Graphics g){

        if(inGame){
            drawBackground(g);
            drawAliens(g);
            drawPlayer(g);
            drawShoot(g);
            drawBomb(g);

        }else{
            if(timer.isRunning()){
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    //The methods below shows how the game works
    //how the alien move, condition to shoot, condition to throw bomb etc.
    private void update(){

        if(deaths == Configs.number_of_aliens_to_destroy){

            inGame = false;
            timer.stop();
            message = "Game won !";
        }

        //player to act
        player.act();

        //shoot condition
        String explosionImage = "src/Pictures/explosion.png";
        if(shoot.isVisible()){

            int shootX = shoot.getX();
            int shootY = shoot.getY();

            for(Alien alien : aliens){

                int alienX = alien.getX();
                int alienY = alien.getY();

                //if bullet meet alien,alien disappear and replace with explosion image
                if(alien.isVisible() && shoot.isVisible()){
                    if(shootX >= (alienX) && shootX <= (alienX + Configs.alienWidth) && shootY >= (alienY) && shootY <= (alienY + Configs.alienHeight)){
                        ImageIcon image = new ImageIcon(explosionImage);
                        alien.setImage(image.getImage());
                        alien.setDying(true);
                        deaths++;
                        shoot.die();
                    }
                }
            }
            int y = shoot.getY();
            //bullet speed
            y = y-5;

            if(y<0){
                shoot.die();
            }else{
                shoot.setY(y);
            }
        }

        //to enable and control the movement of aliens
        for (Alien alien : aliens) {

            int x = alien.getX();

            if (x >= Configs.appWidth - Configs.appRight && direction != -1) {

                direction = -1;

                for (Alien a2 : aliens) {

                    a2.setY(a2.getY() + Configs.goDown);
                }
            }

            if (x <= Configs.appLeft && direction != 1) {

                direction = 1;

                for (Alien a : aliens) {

                    a.setY(a.getY() + Configs.goDown);
                }
            }
        }

        for (Alien alien : aliens) {

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > Configs.ground - Configs.alienHeight) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        //condition for alien to drop bomb
        Random generator = new Random();

        for (Alien alien : aliens) {

            int shot = generator.nextInt(200);
            Alien.Bomb bomb = alien.getBomb();

            if (shot == Configs.chance && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + Configs.playerWidth)
                        && bombY >= (playerY)
                        && bombY <= (playerY + Configs.playerHeight)) {

                    ImageIcon ii = new ImageIcon(explosionImage);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                //adjust the speed of the bomb
                bomb.setY(bomb.getY() + 2);

                if (bomb.getY() >= Configs.ground - Configs.bombHeight) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle(){
        update();
        repaint();
    }

    private class GameCycle implements ActionListener{

        public void actionPerformed(ActionEvent e){
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter{

        //This event is generated when a key is let up.
        public void keyReleased(KeyEvent e){

            player.keyReleased(e);
        }

        //This event is generated when a key is pushed down.
        public void keyPressed(KeyEvent e){

            player.keyPressed(e);

            //direction/placement of the bullet
            int x = player.getX()+15;
            int y = player.getY();

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_SPACE){
                if(inGame){
                    if(!shoot.isVisible()){
                        shoot = new Shoot(x,y);
                    }

                }
            }


        }
    }
}
