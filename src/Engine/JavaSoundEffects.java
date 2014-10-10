/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
/**
 *
 * @author Jason Xu
 */
public class JavaSoundEffects implements Engine.SoundEffects{
    private static String bounceLocation = "";
    private static String winLocation = "";
    private static String loseLocation = "";
    @Override
    public void bounceSound(){
        URL url = JavaSoundEffects.class.getResource(bounceLocation);
        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
    }
    
}
