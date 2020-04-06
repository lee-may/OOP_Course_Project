import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Music  {

    public static void playSound(){

        try{
            File musicPath = new File("src/Music/backgroundMusic.wav");

            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else{
                System.out.println("Can't find file");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}


