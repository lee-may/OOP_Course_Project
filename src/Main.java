import java.awt.EventQueue;
import javax.swing.*;

public class Main extends JFrame {

    public Main() {

        gui();
    }

    public void gui() {

        add(new GameCore());
        setTitle("Alien Shooter");
        setSize(Configs.appWidth, Configs.appHeight);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main game = new Main();
            Music BGM = new Music();
            game.setVisible(true);
            BGM.playSound();

        });

    }

}









