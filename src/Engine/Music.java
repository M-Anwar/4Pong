/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

/**
 * An interface that is used to implement the music
 * @author Jason Xu
 */
public class Music 
{
    /*
        Hi Jason its Muhammed here. Here are a few helpful sites to get you
        started on how to make an Audio engine.
    
        Kilobolt Game Framework:
        http://www.kilobolt.com/day-5-the-android-game-framework-part-i.html
            -This website has a very good audio framework to use (numbers 6 to 8)
            I wouldn't copy it exactly but it defines some nice general functions
            which you may want to implement.
            
        A good implementation of an audio engine is found below. Again this is
        a particular implementation that you can look into but might not be the 
        most extensible.
        
            import java.io.BufferedInputStream;
            import java.util.HashMap;

            import javax.sound.sampled.AudioFormat;
            import javax.sound.sampled.AudioInputStream;
            import javax.sound.sampled.AudioSystem;
            import javax.sound.sampled.Clip;

            public class JukeBox {

                    private static HashMap<String, Clip> clips;
                    private static int gap;
                    private static boolean mute = false;

                    public static void init() {
                            clips = new HashMap<String, Clip>();
                            gap = 0;
                    }

                    public static void load(String path, String key) {
                            if(clips.get(key) != null) return;
                            Clip clip;
                            try {
                                    AudioInputStream ais =
                                            AudioSystem.getAudioInputStream(
                                                    new BufferedInputStream(
                                                            JukeBox.class.getResourceAsStream(path)
                                                    )
                                            );
                                    AudioFormat baseFormat = ais.getFormat();
                                    AudioFormat decodeFormat = new AudioFormat(
                                            AudioFormat.Encoding.PCM_SIGNED,
                                            baseFormat.getSampleRate(),
                                            16,
                                            baseFormat.getChannels(),
                                            baseFormat.getChannels() * 2,
                                            baseFormat.getSampleRate(),
                                            false
                                    );
                                    AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
                                    clip = AudioSystem.getClip();
                                    clip.open(dais);


                                    clips.put(key, clip);
                            }
                            catch(Exception e) {
                                    e.printStackTrace();
                            }
                    }

                    public static void play(String s) {
                            play(s, 0);
                    }

                    public static void play(String s, int i) {
                            if(mute) return;
                            Clip c = clips.get(s);
                            if(c == null) return;
                            if(c.isRunning()) c.stop();
                            c.setFramePosition(i);
                            while(!c.isRunning()) c.start();
                    }

                    public static void stop(String s) {
                            if(clips.get(s) == null) return;
                            if(clips.get(s).isRunning()) clips.get(s).stop();
                    }

                    public static void resume(String s) {
                            if(mute) return;
                            if(clips.get(s).isRunning()) return;
                            clips.get(s).start();
                    }

                    public static void loop(String s) {
                            loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
                    }

                    public static boolean isPlaying(String s) {
                            return clips.get(s).isRunning();
                    }

                    public static void loop(String s, int frame) {
                            loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
                    }

                    public static void loop(String s, int start, int end) {
                            loop(s, gap, start, end);
                    }

                    public static void loop(String s, int frame, int start, int end) {
                            stop(s);
                            if(mute) return;
                            clips.get(s).setLoopPoints(start, end);
                            clips.get(s).setFramePosition(frame);
                            clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
                    }

                    public static void setPosition(String s, int frame) {
                            clips.get(s).setFramePosition(frame);
                    }

                    public static int getFrames(String s) { return clips.get(s).getFrameLength(); }
                    public static int getPosition(String s) { return clips.get(s).getFramePosition(); }

                    public static void close(String s) {
                            stop(s);
                            clips.get(s).close();
                    }

            }

        The good thing about this implementation is that the usage is very simple.
        All the methods are static which means that one can just play a sound
        from anywhere by simply saying JukeBox.play("SOUND"). Our audio engine
        usage should be that simple too, static methods which we can call from 
        anywhere in our game. So when you are designing the audio take into 
        account these design considerations:
    
            - Portable: Encapsulate platform specific functionality in a localized
                        place. So that if were were to switch platforms (Android)
                        we should only have to change one part of the game.
            - Easy to Use: The audio engine should NOT require a handle to 
                           play audio. I.e no object should need to be passed
                           around in order to play sound. Static methods with a 
                           localized loading area should be created. 
            - Efficient: You should implement 2 kinds of audio (ideally). One
                         that is used for playing simple small sound effects. 
                         And another that is used for playing looping background
                         music. In this way not all the sounds have to be loaded
                         at the start of the game and we can save memory and time.
                         The sound effects should NOT be played on the main
                         thread (this should not be a real problem as most 
                         audio libraries including the standard Java one runs 
                         on a seperate thread). 
            - Multi-Sound: One should be able to play multiple sounds at once.
                           I.e one should not have to worry about stopping a 
                           previosly playing sound to start another one.
                           So we should be able to have background music playing
                           while we have small sound effects happening. 
    
        Again as usual make sure you document your API well so others can use it.
    
        Be creative ...and prosper... (some catchy tag line goes here)
    
        Regards,
        Anwar Zerrouki (Muhammed Anwar)
                        
    
    */
}
